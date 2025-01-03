@file:Suppress("unused")

package app.revanced.patcher

import app.revanced.patcher.extensions.InstructionExtensions.instructions
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.Method
import com.android.tools.smali.dexlib2.iface.instruction.Instruction
import com.android.tools.smali.dexlib2.iface.instruction.ReferenceInstruction
import com.android.tools.smali.dexlib2.iface.instruction.WideLiteralInstruction
import com.android.tools.smali.dexlib2.iface.reference.FieldReference
import com.android.tools.smali.dexlib2.iface.reference.MethodReference
import java.util.EnumSet
import kotlin.collections.forEach

abstract class InstructionFilter(
    /**
     * Maximum number of non matching method instructions that can appear before this filter.
     * A value of zero means this filter must match immediately after the prior filter,
     * or if this is the first filter then this may only match the first instruction of a method.
     */
    val maxInstructionsBefore: Int = METHOD_MAX_INSTRUCTIONS
) {

    abstract fun matches(
        method: Method,
        instruction: Instruction,
        methodIndex: Int
    ): Boolean

    companion object {
        /**
         * Maximum number of instructions allowed in a Java method.
         */
        const val METHOD_MAX_INSTRUCTIONS = 65535
    }
}

/**
 * Logical or operator, where the first filter that matches is the match result.
 */
class AnyFilter(
    private val filters: List<InstructionFilter>,
    maxInstructionsBefore: Int = METHOD_MAX_INSTRUCTIONS,
) : InstructionFilter(maxInstructionsBefore) {

    override fun matches(
        method: Method,
        instruction: Instruction,
        methodIndex: Int
    ): Boolean {
        return filters.any { matches(method, instruction, methodIndex) }
    }
}

/**
 * Single opcode.
 */
class OpcodeFilter(
    val opcode: Opcode,
    maxInstructionsBefore: Int = METHOD_MAX_INSTRUCTIONS,
) : InstructionFilter(maxInstructionsBefore) {

    override fun matches(
        method: Method,
        instruction: Instruction,
        methodIndex: Int
    ): Boolean {
        return instruction.opcode == opcode
    }

    companion object {
        fun listOfOpcodes(opcodes: Collection<Opcode?>): List<InstructionFilter> {
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

/**
 * Matches multiple opcodes.
 * If using only a single opcode instead use [OpcodeFilter].
 */
open class OpcodesFilter(
    /**
     * Value of null will match any opcode.
     */
    val opcodes: EnumSet<Opcode>?,
    maxInstructionsBefore: Int = METHOD_MAX_INSTRUCTIONS,
) : InstructionFilter(maxInstructionsBefore) {

    constructor(
        /**
         * Value of null will match any opcode.
         */
        opcodes: List<Opcode>?,
        maxInstructionsBefore: Int = METHOD_MAX_INSTRUCTIONS
    ) : this(if (opcodes == null) null else EnumSet.copyOf(opcodes), maxInstructionsBefore)

    override fun matches(
        method: Method,
        instruction: Instruction,
        methodIndex: Int
    ): Boolean {
        if (opcodes == null) {
            return true // Match anything.
        }
        return opcodes.contains(instruction.opcode) == true
    }
}

class LiteralFilter(
    var literal: () -> Long,
    opcodes: List<Opcode>? = null,
    maxInstructionsBefore: Int = METHOD_MAX_INSTRUCTIONS,
) : OpcodesFilter(opcodes, maxInstructionsBefore) {

    /**
     * Constant long literal.
     */
    constructor(
        literal : Long,
        opcodes: List<Opcode>? = null,
        maxInstructionsBefore: Int = METHOD_MAX_INSTRUCTIONS,
    ) : this({ literal }, opcodes, maxInstructionsBefore)

    /**
     * Floating point literal.
     */
    constructor(
        literal : Double,
        opcodes: List<Opcode>? = null,
        maxInstructionsBefore: Int = METHOD_MAX_INSTRUCTIONS,
    ) : this({ literal.toRawBits() }, opcodes, maxInstructionsBefore)

    override fun matches(
        method: Method,
        instruction: Instruction,
        methodIndex: Int
    ): Boolean {
        if (!super.matches(method, instruction, methodIndex)) {
            return false
        }

        return (instruction as? WideLiteralInstruction)?.wideLiteral == literal()
    }
}

class MethodFilter(
    /**
     * Defining class of the method call. Matches using endsWith().
     *
     * For calls to a method in the same class, use 'this' as the defining class.
     * Note: 'this' does not work for methods declared only in a superclass.
     */
    val definingClass: (() -> String)? = null,
    /**
     * Method name. Must be exact match of the method name.
     */
    val methodName: (() -> String)? = null,
    /**
     * Parameters of the method call. Each parameter matches
     * using startsWith() and semantics are the same as [Fingerprint].
     */
    val parameters: (() -> List<String>)? = null,
    /**
     * Return type.  Matches using startsWith()
     */
    val returnType: (() -> String)? = null,
    /**
     * Opcode types to match. By default this matches any method call opcode:
     * <code>Opcode.INVOKE_*</code>.
     *
     * If this filter must match specific types of method call, then specify the desired opcodes
     * such as [Opcode.INVOKE_STATIC], [Opcode.INVOKE_STATIC_RANGE] to only match static calls.
     */
    opcodes: List<Opcode>? = null,
    maxInstructionsBefore: Int = METHOD_MAX_INSTRUCTIONS,
) : OpcodesFilter(opcodes, maxInstructionsBefore) {

    // Define both providers and literal strings.
    // Providers are used when the parameters are not known at declaration,
    // such as using another Fingerprint to find define a method or field type.
    constructor(
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
        methodName: String? = null,
        /**
         * Parameters of the method call. Each parameter matches
         * using startsWith() and semantics are the same as [Fingerprint].
         */
        parameters: List<String>? = null,
        /**
         * Return type.  Matches using startsWith()
         */
        returnType: String? = null,

        opcodes: List<Opcode>? = null,
        maxInstructionsBefore: Int = METHOD_MAX_INSTRUCTIONS,
    ) : this(
        @Suppress("USELESS_CAST")
        if (definingClass != null) {
            { definingClass } as (() -> String)
        } else null, if (methodName != null) {
            { methodName }
        } else null, if (parameters != null) {
            { parameters }
        } else null, if (returnType != null) {
            { returnType }
        } else null,
        opcodes,
        maxInstructionsBefore
    )

    override fun matches(
        method: Method,
        instruction: Instruction,
        methodIndex: Int
    ): Boolean {
        if (!super.matches(method, instruction, methodIndex)) {
            return false
        }

        val reference = (instruction as? ReferenceInstruction)?.reference as? MethodReference
        if (reference == null) return false

        if (definingClass != null) {
            val referenceClass = reference.definingClass
            val definingClass = definingClass()
            if (!referenceClass.endsWith(definingClass)) {
                // Check if 'this' defining class is used.
                // Would be nice if this also checked all super classes,
                // but doing so requires iteratively checking all superclasses
                // up to the root Object class since class defs are mere Strings.
                if (definingClass != "this" || referenceClass != method.definingClass) {
                    return false
                } // else, the method call is for 'this' class.
            }
        }
        if (methodName != null && reference.name != methodName()) {
            return false
        }
        if (returnType != null && !reference.returnType.startsWith(returnType())) {
            return false
        }
        if (parameters != null && !parametersStartsWith(reference.parameterTypes, parameters())) {
            return false
        }

        return true
    }
}

class FieldFilter(
    /**
     * Defining class of the field call. Matches using endsWith().
     *
     * For calls to a method in the same class, use 'this' as the defining class.
     * Note: 'this' does not work for fields found in superclasses.
     */

    val definingClass: (() -> String)? = null,
    /**
     * Name of the field.  Must be a full match of the field name.
     */
    val name: (() -> String)? = null,
    /**
     * Class type of field. Partial matches using startsWith() is allowed.
     */
    val type: (() -> String)? = null,
    opcodes: List<Opcode>? = null,
    maxInstructionsBefore: Int = METHOD_MAX_INSTRUCTIONS,
) : OpcodesFilter(opcodes, maxInstructionsBefore) {

    constructor(
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
        opcodes: List<Opcode>? = null,
        maxInstructionsBefore: Int = METHOD_MAX_INSTRUCTIONS,
    ) : this(
        @Suppress("USELESS_CAST")
        if (definingClass != null) {
            { definingClass } as (() -> String)
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

    override fun matches(
        method: Method,
        instruction: Instruction,
        methodIndex: Int
    ): Boolean {
        if (!super.matches(method, instruction, methodIndex)) {
            return false
        }

        val reference = (instruction as? ReferenceInstruction)?.reference as? FieldReference
        if (reference == null) return false

        if (definingClass != null) {
            val referenceClass = reference.definingClass
            val definingClass = definingClass()

            if (!referenceClass.endsWith(definingClass)) {
                if (definingClass != "this" || referenceClass != method.definingClass) {
                    return false
                } // else, the method call is for 'this' class.
            }
        }
        if (name != null && reference.name != name()) {
            return false
        }
        if (type != null && !reference.type.startsWith(type())) {
            return false
        }

        return true
    }
}

/**
 * Filter wrapper that only matches the last instruction of a method.
 */
class LastInstructionFilter(
    var filter : InstructionFilter,
    maxInstructionsBefore: Int = METHOD_MAX_INSTRUCTIONS,
) : InstructionFilter(maxInstructionsBefore) {

    override fun matches(
        method: Method,
        instruction: Instruction,
        methodIndex: Int
    ): Boolean {
        return methodIndex == method.instructions.count() - 1 && filter.matches(
            method, instruction, methodIndex
        )
    }
}
