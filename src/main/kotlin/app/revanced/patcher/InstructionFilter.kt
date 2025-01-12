@file:Suppress("unused")

package app.revanced.patcher

import app.revanced.patcher.FieldAccessFilter.Companion.parseJvmFieldAccess
import app.revanced.patcher.InstructionFilter.Companion.METHOD_MAX_INSTRUCTIONS
import app.revanced.patcher.MethodCallFilter.Companion.parseJvmMethodCall
import app.revanced.patcher.extensions.InstructionExtensions.instructions
import app.revanced.patcher.patch.BytecodePatchContext
import app.revanced.patcher.util.parametersStartsWith
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.Method
import com.android.tools.smali.dexlib2.iface.instruction.Instruction
import com.android.tools.smali.dexlib2.iface.instruction.ReferenceInstruction
import com.android.tools.smali.dexlib2.iface.instruction.WideLiteralInstruction
import com.android.tools.smali.dexlib2.iface.reference.FieldReference
import com.android.tools.smali.dexlib2.iface.reference.MethodReference
import com.android.tools.smali.dexlib2.iface.reference.StringReference
import com.android.tools.smali.dexlib2.iface.reference.TypeReference
import java.util.EnumSet
import kotlin.collections.forEach

/**
 * Matches method [Instruction] objects, similar to how [Fingerprint] matches entire fingerprints.
 *
 * The most basic filters match only opcodes and nothing more,
 * and more precise filters can match:
 * - Field references (get/put opcodes) by name/type.
 * - Method calls (invoke_* opcodes) by name/parameter/return type.
 * - Object instantiation for specific class types.
 * - Literal const values.
 *
 * Variable space is allowed between each filter.
 *
 * All filters use a default [maxInstructionsBefore] of [METHOD_MAX_INSTRUCTIONS]
 * meaning they can match anywhere after the previous filter.
 */
abstract class InstructionFilter(
    /**
     * Maximum number of non matching method instructions that can appear before this filter.
     * A value of zero means this filter must match immediately after the prior filter,
     * or if this is the first filter then this may only match the first instruction of a method.
     */
    val maxInstructionsBefore: Int = METHOD_MAX_INSTRUCTIONS
) {

    init {
        if (maxInstructionsBefore < 0) {
            throw IllegalArgumentException("maxInstructionsBefore cannot be negative")
        }
    }

    /**
     * If this filter matches the method instruction.
     *
     * @param method The enclosing method of [instruction].
     * @param instruction The instruction to check for a match.
     * @param methodIndex The index of [instruction] in the enclosing [method].
     *                    The index can be ignored unless a filter has an unusual reason,
     *                    such as matching only the last index of a method.
     */
    abstract fun matches(
        context: BytecodePatchContext,
        method: Method,
        instruction: Instruction,
        methodIndex: Int
    ): Boolean

    companion object {
        /**
         * Maximum number of instructions allowed in a Java method.
         * Indicates to allow a match anywhere after the previous filter.
         */
        const val METHOD_MAX_INSTRUCTIONS = 65535
    }
}



class AnyInstruction internal constructor(
    private val filters: List<InstructionFilter>,
    maxInstructionsBefore: Int,
) : InstructionFilter(maxInstructionsBefore) {

    override fun matches(
        context: BytecodePatchContext,
        method: Method,
        instruction: Instruction,
        methodIndex: Int
    ) : Boolean {
        return filters.any { filter ->
            filter.matches(context, method, instruction, methodIndex)
        }
    }
}

/**
 * Logical OR operator where the first filter that matches satisfies this filter.
 */
fun anyInstruction(
    vararg filters: InstructionFilter,
    maxInstructionsBefore: Int = METHOD_MAX_INSTRUCTIONS,
) = AnyInstruction(filters.asList(), maxInstructionsBefore)



open class OpcodeFilter(
    val opcode: Opcode,
    maxInstructionsBefore: Int,
) : InstructionFilter(maxInstructionsBefore) {

    override fun matches(
        context: BytecodePatchContext,
        method: Method,
        instruction: Instruction,
        methodIndex: Int
    ): Boolean {
        return instruction.opcode == opcode
    }
}

/**
 * Single opcode.
 */
fun opcode(opcode: Opcode, maxInstructionsBefore: Int = METHOD_MAX_INSTRUCTIONS) =
    OpcodeFilter(opcode, maxInstructionsBefore)



/**
 * Matches a single instruction from many kinds of opcodes.
 * If matching only a single opcode instead use [OpcodeFilter].
 */
open class OpcodesFilter private constructor(
    val opcodes: EnumSet<Opcode>?,
    maxInstructionsBefore: Int,
) : InstructionFilter(maxInstructionsBefore) {

    protected constructor(
        /**
         * Value of `null` will match any opcode.
         */
        opcodes: List<Opcode>?,
        maxInstructionsBefore: Int = METHOD_MAX_INSTRUCTIONS
    ) : this(if (opcodes == null) null else EnumSet.copyOf(opcodes), maxInstructionsBefore)

    override fun matches(
        context: BytecodePatchContext,
        method: Method,
        instruction: Instruction,
        methodIndex: Int
    ): Boolean {
        if (opcodes == null) {
            return true // Match anything.
        }
        return opcodes.contains(instruction.opcode)
    }

    companion object {
        /**
         * First opcode can match anywhere in a method, but all
         * subsequent opcodes must match after the previous opcode.
         *
         * A value of `null` indicates to match any opcode.
         */
        internal fun listOfOpcodes(opcodes: Collection<Opcode?>): List<InstructionFilter> {
            var list = ArrayList<InstructionFilter>(opcodes.size)

            // First opcode can match anywhere.
            var instructionsBefore = METHOD_MAX_INSTRUCTIONS
            opcodes.forEach { opcode ->
                list += if (opcode == null) {
                    // Null opcode matches anything.
                    OpcodesFilter(null as List<Opcode>?, instructionsBefore)
                } else {
                    OpcodeFilter(opcode, instructionsBefore)
                }
                instructionsBefore = 0
            }

            return list
        }
    }
}



class LiteralFilter internal constructor(
    var literal: (BytecodePatchContext) -> Long,
    opcodes: List<Opcode>? = null,
    maxInstructionsBefore: Int,
) : OpcodesFilter(opcodes, maxInstructionsBefore) {

    private var literalValue: Long? = null

    override fun matches(
        context: BytecodePatchContext,
        method: Method,
        instruction: Instruction,
        methodIndex: Int
    ): Boolean {
        if (!super.matches(context, method, instruction, methodIndex)) {
            return false
        }

        if (instruction !is WideLiteralInstruction) return false

        if (literalValue == null) {
            literalValue = literal(context)
        }

        return instruction.wideLiteral == literalValue
    }
}

/**
 * Literal value, such as:
 * `const v1, 0x7f080318`
 *
 * that can be matched using:
 * `LiteralFilter(0x7f080318)`
 * or
 * `LiteralFilter(2131231512)`
 */
fun literal(
    literal: (BytecodePatchContext) -> Long,
    opcodes: List<Opcode>? = null,
    maxInstructionsBefore: Int = METHOD_MAX_INSTRUCTIONS,
) = LiteralFilter(literal, opcodes, maxInstructionsBefore)

/**
 * Literal value, such as:
 * `const v1, 0x7f080318`
 *
 * that can be matched using:
 * `LiteralFilter(0x7f080318)`
 * or
 * `LiteralFilter(2131231512L)`
 */
fun literal(
    literal: Long,
    opcodes: List<Opcode>? = null,
    maxInstructionsBefore: Int = METHOD_MAX_INSTRUCTIONS,
) = LiteralFilter({ literal }, opcodes, maxInstructionsBefore)

/**
 * Floating point literal.
 */
fun literal(
    literal: Double,
    opcodes: List<Opcode>? = null,
    maxInstructionsBefore: Int = METHOD_MAX_INSTRUCTIONS,
) = LiteralFilter({ literal.toRawBits() }, opcodes, maxInstructionsBefore)



class StringFilter internal constructor(
    var string: (BytecodePatchContext) -> String,
    var partialMatch: Boolean,
    maxInstructionsBefore: Int,
) : OpcodesFilter(listOf(Opcode.CONST_STRING, Opcode.CONST_STRING_JUMBO), maxInstructionsBefore) {

    override fun matches(
        context: BytecodePatchContext,
        method: Method,
        instruction: Instruction,
        methodIndex: Int
    ): Boolean {
        if (!super.matches(context, method, instruction, methodIndex)) {
            return false
        }

        val instructionString = ((instruction as ReferenceInstruction).reference as StringReference).string
        val filterString = string(context)

        return if (partialMatch) {
            instructionString.contains(filterString)
        } else {
            instructionString == filterString
        }
    }
}

/**
 * Literal String instruction.
 */
fun string(
    string: (BytecodePatchContext) -> String,
    /**
     * If [string] is a partial match, where the target string contains this string.
     * For more precise matching, consider using [any] with multiple exact string declarations.
     */
    partialMatch: Boolean = false,
    maxInstructionsBefore: Int = METHOD_MAX_INSTRUCTIONS,
) = StringFilter(string, partialMatch, maxInstructionsBefore)

/**
 * Literal String instruction.
 */
fun string(
    string: String,
    /**
     * If [string] is a partial match, where the target string contains this string.
     * For more precise matching, consider using [any] with multiple exact string declarations.
     */
    partialMatch: Boolean = false,
    maxInstructionsBefore: Int = METHOD_MAX_INSTRUCTIONS,
) = StringFilter({ string }, partialMatch, maxInstructionsBefore)



class MethodCallFilter internal constructor(
    val definingClass: ((BytecodePatchContext) -> String)? = null,
    val name: ((BytecodePatchContext) -> String)? = null,
    val parameters: ((BytecodePatchContext) -> List<String>)? = null,
    val returnType: ((BytecodePatchContext) -> String)? = null,
    opcodes: List<Opcode>? = null,
    maxInstructionsBefore: Int,
) : OpcodesFilter(opcodes, maxInstructionsBefore) {

    override fun matches(
        context: BytecodePatchContext,
        method: Method,
        instruction: Instruction,
        methodIndex: Int
    ): Boolean {
        if (!super.matches(context, method, instruction, methodIndex)) {
            return false
        }

        val reference = (instruction as? ReferenceInstruction)?.reference as? MethodReference
        if (reference == null) return false

        if (definingClass != null) {
            val referenceClass = reference.definingClass
            val definingClass = definingClass(context)

            if (!referenceClass.endsWith(definingClass)) {
                // Check if 'this' defining class is used.
                // Would be nice if this also checked all super classes,
                // but doing so requires iteratively checking all superclasses
                // up to the root Object class since class defs are mere Strings.
                if (!(definingClass == "this" && referenceClass == method.definingClass)) {
                    return false
                } // else, the method call is for 'this' class.
            }
        }
        if (name != null && reference.name != name(context)) {
            return false
        }
        if (returnType != null && !reference.returnType.startsWith(returnType(context))) {
            return false
        }
        if (parameters != null && !parametersStartsWith(reference.parameterTypes, parameters(context))) {
            return false
        }

        return true
    }

    companion object {
        private val regex = Regex("""^(L[^;]+;)->([^(\s]+)\(([^)]*)\)(\[?L[^;]+;|\[?[BCSIJFDZV])${'$'}""")

        internal fun parseJvmMethodCall(
            methodSignature: String,
            opcodes: List<Opcode>? = null,
            maxInstructionsBefore: Int = METHOD_MAX_INSTRUCTIONS
        ): MethodCallFilter {
            val matchResult = regex.matchEntire(methodSignature)
                ?: throw IllegalArgumentException("Invalid method signature: $methodSignature")

            val classDescriptor = matchResult.groupValues[1]
            val methodName = matchResult.groupValues[2]
            val paramDescriptorString = matchResult.groupValues[3]
            val returnDescriptor = matchResult.groupValues[4]

            val paramDescriptors = parseParameterDescriptors(paramDescriptorString)

            return MethodCallFilter(
                { classDescriptor },
                { methodName },
                { paramDescriptors },
                { returnDescriptor },
                opcodes,
                maxInstructionsBefore
            )
        }

        /**
         * Parses a single JVM type descriptor or an array descriptor at the current position.
         * For example: Lcom/example/SomeClass; or I or [I or [Lcom/example/SomeClass; etc.
         */
        private fun parseSingleType(params: String, startIndex: Int): Pair<String, Int> {
            var i = startIndex

            // Keep track of array dimensions '['
            while (i < params.length && params[i] == '[') {
                i++
            }

            return if (i < params.length && params[i] == 'L') {
                // It's an object type starting with 'L', read until ';'
                val semicolonPos = params.indexOf(';', i)
                if (semicolonPos == -1) {
                    throw IllegalArgumentException("Malformed object descriptor (missing semicolon) in: $params")
                }
                // Substring from startIndex up to and including the semicolon.
                val typeDescriptor = params.substring(startIndex, semicolonPos + 1)
                typeDescriptor to (semicolonPos + 1)
            } else {
                // It's either a primitive or we've already consumed the array part
                // So just take one character (e.g. 'I', 'Z', 'B', etc.)
                val typeDescriptor = params.substring(startIndex, i + 1)
                typeDescriptor to (i + 1)
            }
        }

        /**
         * Parses the parameters (the part inside parentheses) into a list of JVM type descriptors.
         */
        private fun parseParameterDescriptors(paramString: String): List<String> {
            val result = mutableListOf<String>()
            var currentIndex = 0
            while (currentIndex < paramString.length) {
                val (type, nextIndex) = parseSingleType(paramString, currentIndex)
                result.add(type)
                currentIndex = nextIndex
            }
            return result
        }
    }
}

/**
 * Identifies method calls.
 *
 * `Null` parameters matches anything.
 *
 * By default any type of method call matches.
 * Specify opcodes if a specific type of method call is desired (such as only static calls).
 */
fun methodCall(
    /**
     * Defining class of the method call. Matches using endsWith().
     *
     * For calls to a method in the same class, use 'this' as the defining class.
     * Note: 'this' does not work for methods declared only in a superclass.
     */
    definingClass: ((BytecodePatchContext) -> String)? = null,
    /**
     * Method name. Must be exact match of the method name.
     */
    name: ((BytecodePatchContext) -> String)? = null,
    /**
     * Parameters of the method call. Each parameter matches
     * using startsWith() and semantics are the same as [Fingerprint].
     */
    parameters: ((BytecodePatchContext) -> List<String>)? = null,
    /**
     * Return type. Matches using startsWith()
     */
    returnType: ((BytecodePatchContext) -> String)? = null,
    /**
     * Opcode types to match. By default this matches any method call opcode:
     * `Opcode.INVOKE_*`.
     *
     * If this filter must match specific types of method call, then specify the desired opcodes
     * such as [Opcode.INVOKE_STATIC], [Opcode.INVOKE_STATIC_RANGE] to match only static calls.
     */
    opcodes: List<Opcode>? = null,
    maxInstructionsBefore: Int = METHOD_MAX_INSTRUCTIONS,
) = MethodCallFilter(
    definingClass,
    name,
    parameters,
    returnType,
    opcodes,
    maxInstructionsBefore
)

fun methodCall(
    /**
     * Defining class of the method call. Matches using endsWith().
     *
     * For calls to a method in the same class, use 'this' as the defining class.
     * Note: 'this' does not work for methods declared only in a superclass.
     */
    definingClass: String? = null,
    /**
     * Method name. Must be exact match of the method name.
     */
    name: String? = null,
    /**
     * Parameters of the method call. Each parameter matches
     * using startsWith() and semantics are the same as [Fingerprint].
     */
    parameters: List<String>? = null,
    /**
     * Return type.  Matches using startsWith()
     */
    returnType: String? = null,
    /**
     * Opcode types to match. By default this matches any method call opcode:
     * `Opcode.INVOKE_*`.
     *
     * If this filter must match specific types of method call, then specify the desired opcodes
     * such as [Opcode.INVOKE_STATIC], [Opcode.INVOKE_STATIC_RANGE] to match only static calls.
     */
    opcodes: List<Opcode>? = null,
    maxInstructionsBefore: Int = METHOD_MAX_INSTRUCTIONS,
) = MethodCallFilter(
    if (definingClass != null) {
        { definingClass }
    } else null,
    if (name != null) {
        { name }
    } else null,
    if (parameters != null) {
        { parameters }
    } else null,
    if (returnType != null) {
        { returnType }
    } else null,
    opcodes,
    maxInstructionsBefore
)

fun methodCall(
    /**
     * Defining class of the method call. Matches using endsWith().
     *
     * For calls to a method in the same class, use 'this' as the defining class.
     * Note: 'this' does not work for methods declared only in a superclass.
     */
    definingClass: String? = null,
    /**
     * Method name. Must be exact match of the method name.
     */
    name: String? = null,
    /**
     * Parameters of the method call. Each parameter matches
     * using startsWith() and semantics are the same as [Fingerprint].
     */
    parameters: List<String>? = null,
    /**
     * Return type.  Matches using startsWith()
     */
    returnType: String? = null,
    /**
     * Opcode types to match. By default this matches any method call opcode:
     * `Opcode.INVOKE_*`.
     *
     * If this filter must match specific types of method call, then specify the desired opcodes
     * such as [Opcode.INVOKE_STATIC], [Opcode.INVOKE_STATIC_RANGE] to match only static calls.
     */
    opcode: Opcode,
    maxInstructionsBefore: Int,
) = MethodCallFilter(
    if (definingClass != null) {
        { definingClass }
    } else null,
    if (name != null) {
        { name }
    } else null,
    if (parameters != null) {
        { parameters }
    } else null,
    if (returnType != null) {
        { returnType }
    } else null,
    listOf(opcode),
    maxInstructionsBefore
)

/**
 * Method call for a copy pasted SMALI style method signature. e.g.:
 * `Landroid/view/View;->inflate(Landroid/content/Context;ILandroid/view/ViewGroup;)Landroid/view/View;`
 *
 * Does not support obfuscated method names or parameter/return types.
 */
fun methodCall(
    smali: String,
    opcodes: List<Opcode>? = null,
    maxInstructionsBefore: Int = METHOD_MAX_INSTRUCTIONS
) = parseJvmMethodCall(smali, opcodes, maxInstructionsBefore)

/**
 * Method call for a copy pasted SMALI style method signature. e.g.:
 * `Landroid/view/View;->inflate(Landroid/content/Context;ILandroid/view/ViewGroup;)Landroid/view/View;`
 *
 * Does not support obfuscated method names or parameter/return types.
 */
fun methodCall(
    smali: String,
    opcode: Opcode,
    maxInstructionsBefore: Int = METHOD_MAX_INSTRUCTIONS
) = parseJvmMethodCall(smali, listOf(opcode), maxInstructionsBefore)



class FieldAccessFilter internal constructor(
    val definingClass: ((BytecodePatchContext) -> String)? = null,
    val name: ((BytecodePatchContext) -> String)? = null,
    val type: ((BytecodePatchContext) -> String)? = null,
    opcodes: List<Opcode>? = null,
    maxInstructionsBefore: Int,
) : OpcodesFilter(opcodes, maxInstructionsBefore) {

    override fun matches(
        context: BytecodePatchContext,
        method: Method,
        instruction: Instruction,
        methodIndex: Int
    ): Boolean {
        if (!super.matches(context, method, instruction, methodIndex)) {
            return false
        }

        val reference = (instruction as? ReferenceInstruction)?.reference as? FieldReference
        if (reference == null) return false

        if (definingClass != null) {
            val referenceClass = reference.definingClass
            val definingClass = definingClass(context)

            if (!referenceClass.endsWith(definingClass)) {
                if (!(definingClass == "this" && referenceClass == method.definingClass)) {
                    return false
                } // else, the method call is for 'this' class.
            }
        }
        if (name != null && reference.name != name(context)) {
            return false
        }
        if (type != null && !reference.type.startsWith(type(context))) {
            return false
        }

        return true
    }

    internal companion object {
        private val regex = Regex("""^(L[^;]+;)->([^:]+):(\[?L[^;]+;|\[?[BCSIJFDZV])${'$'}""")

        internal fun parseJvmFieldAccess(
            fieldSignature: String,
            opcodes: List<Opcode>? = null,
            maxInstructionsBefore: Int = METHOD_MAX_INSTRUCTIONS
        ): FieldAccessFilter {
            val matchResult = regex.matchEntire(fieldSignature)
                ?: throw IllegalArgumentException("Invalid field access smali: $fieldSignature")

            return fieldAccess(
                definingClass = matchResult.groupValues[1],
                name = matchResult.groupValues[2],
                type = matchResult.groupValues[3],
                opcodes = opcodes,
                maxInstructionsBefore = maxInstructionsBefore
            )
        }
    }
}

/**
 * Matches a field call, such as:
 * `iget-object v0, p0, Lahhh;->g:Landroid/view/View;`
 */
fun fieldAccess(
    /**
     * Defining class of the field call. Matches using endsWith().
     *
     * For calls to a method in the same class, use 'this' as the defining class.
     * Note: 'this' does not work for fields found in superclasses.
     */
    definingClass: ((BytecodePatchContext) -> String)? = null,
    /**
     * Name of the field. Must be a full match of the field name.
     */
    name: ((BytecodePatchContext) -> String)? = null,
    /**
     * Class type of field. Partial matches using startsWith() is allowed.
     */
    type: ((BytecodePatchContext) -> String)? = null,
    /**
     * Valid opcodes matches for this instruction.
     * By default this matches any kind of field access
     * (`Opcode.IGET`, `Opcode.SGET`, `Opcode.IPUT`, `Opcode.SPUT`, etc).
     */
    opcodes: List<Opcode>? = null,
    maxInstructionsBefore: Int = METHOD_MAX_INSTRUCTIONS,
)  = FieldAccessFilter(definingClass, name, type, opcodes, maxInstructionsBefore)

/**
 * Matches a field call, such as:
 * `iget-object v0, p0, Lahhh;->g:Landroid/view/View;`
 */
fun fieldAccess(
    /**
     * Defining class of the field call. Matches using endsWith().
     *
     * For calls to a method in the same class, use 'this' as the defining class.
     * Note: 'this' does not work for fields found in superclasses.
     */
    definingClass: String? = null,
    /**
     * Name of the field.  Must be a full match of the field name.
     */
    name: String? = null,
    /**
     * Class type of field. Partial matches using startsWith() is allowed.
     */
    type: String? = null,
    /**
     * Valid opcodes matches for this instruction.
     * By default this matches any kind of field access
     * (`Opcode.IGET`, `Opcode.SGET`, `Opcode.IPUT`, `Opcode.SPUT`, etc).
     */
    opcodes: List<Opcode>? = null,
    maxInstructionsBefore: Int = METHOD_MAX_INSTRUCTIONS,
) = FieldAccessFilter(
    if (definingClass != null) {
        { definingClass }
    } else null,
    if (name != null) {
        { name }
    } else null,
    if (type != null) {
        { type }
    } else null,
    opcodes,
    maxInstructionsBefore
)

/**
 * Matches a field call, such as:
 * `iget-object v0, p0, Lahhh;->g:Landroid/view/View;`
 */
fun fieldAccess(
    /**
     * Defining class of the field call. Matches using endsWith().
     *
     * For calls to a method in the same class, use 'this' as the defining class.
     * Note: 'this' does not work for fields found in superclasses.
     */
    definingClass: String? = null,
    /**
     * Name of the field.  Must be a full match of the field name.
     */
    name: String? = null,
    /**
     * Class type of field. Partial matches using startsWith() is allowed.
     */
    type: String? = null,
    opcode: Opcode,
    maxInstructionsBefore: Int = METHOD_MAX_INSTRUCTIONS,
) = fieldAccess(
    definingClass,
    name,
    type,
    listOf(opcode),
    maxInstructionsBefore
)

/**
 * Field access for a copy pasted SMALI style field access call. e.g.:
 * `Ljava/lang/Boolean;->TRUE:Ljava/lang/Boolean;`
 *
 * Does not support obfuscated field names or obfuscated field types.
 */
fun fieldAccess(
    smali: String,
    opcodes: List<Opcode>? = null,
    maxInstructionsBefore: Int = METHOD_MAX_INSTRUCTIONS
) = parseJvmFieldAccess(smali, opcodes, maxInstructionsBefore)

/**
 * Field access for a copy pasted SMALI style field access call. e.g.:
 * `Ljava/lang/Boolean;->TRUE:Ljava/lang/Boolean;`
 *
 * Does not support obfuscated field names or obfuscated field types.
 */
fun fieldAccess(
    smali: String,
    opcode: Opcode,
    maxInstructionsBefore: Int = METHOD_MAX_INSTRUCTIONS
) = parseJvmFieldAccess(smali, listOf(opcode), maxInstructionsBefore)



class NewInstanceFilter internal constructor (
    var type: (BytecodePatchContext) -> String,
    maxInstructionsBefore : Int,
) : OpcodesFilter(listOf(Opcode.NEW_INSTANCE, Opcode.NEW_ARRAY), maxInstructionsBefore) {

    override fun matches(
        context: BytecodePatchContext,
        method: Method,
        instruction: Instruction,
        methodIndex: Int
    ): Boolean {
        if (!super.matches(context, method, instruction, methodIndex)) {
            return false
        }

        val reference = (instruction as? ReferenceInstruction)?.reference as? TypeReference
        if (reference == null) return false

        return reference.type.endsWith(type(context))
    }
}


/**
 * Opcode type [Opcode.NEW_INSTANCE] or [Opcode.NEW_ARRAY] with a non obfuscated class type.
 *
 * @param type Class type that matches the target instruction using [String.endsWith].
 */
fun newInstancetype(type: (BytecodePatchContext) -> String, maxInstructionsBefore: Int = METHOD_MAX_INSTRUCTIONS) =
    NewInstanceFilter(type, maxInstructionsBefore)

/**
 * Opcode type [Opcode.NEW_INSTANCE] or [Opcode.NEW_ARRAY] with a non obfuscated class type.
 *
 * @param type Class type that matches the target instruction using [String.endsWith].
 */
fun newInstance(type: String, maxInstructionsBefore: Int = METHOD_MAX_INSTRUCTIONS) : NewInstanceFilter {
    if (!type.endsWith(";")) {
        throw IllegalArgumentException("Class type does not end with a semicolon: $type")
    }
    return NewInstanceFilter({ type }, maxInstructionsBefore)
}



class CheckCastFilter internal constructor (
    var type: (BytecodePatchContext) -> String,
    maxInstructionsBefore : Int,
) : OpcodeFilter(Opcode.CHECK_CAST, maxInstructionsBefore) {

    override fun matches(
        context: BytecodePatchContext,
        method: Method,
        instruction: Instruction,
        methodIndex: Int
    ): Boolean {
        if (!super.matches(context, method, instruction, methodIndex)) {
            return false
        }

        val reference = (instruction as? ReferenceInstruction)?.reference as? TypeReference
        if (reference == null) return false

        return reference.type.endsWith(type(context))
    }
}

/**
 * Opcode type [Opcode.CHECK_CAST] with a non obfuscated class type.
 *
 * @param type Class type that matches the target instruction using [String.endsWith].
 */
fun checkCast(type: (BytecodePatchContext) -> String, maxInstructionsBefore: Int = METHOD_MAX_INSTRUCTIONS) =
    CheckCastFilter(type, maxInstructionsBefore)

/**
 * Opcode type [Opcode.CHECK_CAST] with a non obfuscated class type.
 *
 * @param type Class type that matches the target instruction using [String.endsWith].
 */
fun checkCast(type: String, maxInstructionsBefore: Int = METHOD_MAX_INSTRUCTIONS) : CheckCastFilter {
    if (!type.endsWith(";")) {
        throw IllegalArgumentException("Class type does not end with a semicolon: $type")
    }

    return CheckCastFilter({ type }, maxInstructionsBefore)
}



class LastInstructionFilter internal constructor(
    var filter : InstructionFilter,
    maxInstructionsBefore: Int,
) : InstructionFilter(maxInstructionsBefore) {

    override fun matches(
        context: BytecodePatchContext,
        method: Method,
        instruction: Instruction,
        methodIndex: Int
    ): Boolean {
        return methodIndex == method.instructions.count() - 1 && filter.matches(
            context, method, instruction, methodIndex
        )
    }
}

/**
 * Filter wrapper that matches the last instruction of a method.
 */
fun lastInstruction(
    filter : InstructionFilter,
    maxInstructionsBefore: Int = METHOD_MAX_INSTRUCTIONS,
) = LastInstructionFilter(filter, maxInstructionsBefore)
