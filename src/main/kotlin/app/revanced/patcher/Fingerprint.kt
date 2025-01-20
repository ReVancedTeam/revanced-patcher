@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package app.revanced.patcher

import app.revanced.patcher.FieldAccessFilter.Companion.parseJvmFieldAccess
import app.revanced.patcher.InstructionFilter.Companion.METHOD_MAX_INSTRUCTIONS
import app.revanced.patcher.Match.PatternMatch
import app.revanced.patcher.MethodCallFilter.Companion.parseJvmMethodCall
import app.revanced.patcher.extensions.InstructionExtensions.instructions
import app.revanced.patcher.extensions.InstructionExtensions.instructionsOrNull
import app.revanced.patcher.patch.*
import app.revanced.patcher.util.proxy.ClassProxy
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.ClassDef
import com.android.tools.smali.dexlib2.iface.Method
import com.android.tools.smali.dexlib2.iface.instruction.Instruction
import com.android.tools.smali.dexlib2.iface.instruction.ReferenceInstruction
import com.android.tools.smali.dexlib2.iface.instruction.WideLiteralInstruction
import com.android.tools.smali.dexlib2.iface.reference.FieldReference
import com.android.tools.smali.dexlib2.iface.reference.MethodReference
import com.android.tools.smali.dexlib2.iface.reference.StringReference
import com.android.tools.smali.dexlib2.iface.reference.TypeReference
import com.android.tools.smali.dexlib2.util.MethodUtil
import java.util.EnumSet
import kotlin.collections.forEach
import kotlin.lazy
import kotlin.reflect.KProperty

/**
 * A fingerprint for a method. A fingerprint is a partial description of a method.
 * It is used to uniquely match a method by its characteristics.
 *
 * An example fingerprint for a public method that takes a single string parameter and returns void:
 * ```
 * fingerprint {
 *    accessFlags(AccessFlags.PUBLIC)
 *    returns("V")
 *    parameters("Ljava/lang/String;")
 * }
 * ```
 *
 * @param name Human readable fingerprint name used for [toString] and error stack traces.
 * @param accessFlags The exact access flags using values of [AccessFlags].
 * @param returnType The return type. Compared using [String.startsWith].
 * @param parameters The parameters. Partial matches allowed and follow the same rules as [returnType].
 * @param filters A list of filters to match, declared in the same order the instructions appear in the method.
 * @param strings A list of the strings that appear anywhere in the method. Compared using [String.contains].
 * @param custom A custom condition for this fingerprint.
 */
class Fingerprint internal constructor(
    internal val name: String,
    internal val accessFlags: Int?,
    internal val returnType: String?,
    internal val parameters: List<String>?,
    internal val filters: List<InstructionFilter>?,
    @Deprecated("Instead use instruction filters")
    internal val strings: List<String>?,
    internal val custom: ((method: Method, classDef: ClassDef) -> Boolean)?,
) {
    @Suppress("ktlint:standard:backing-property-naming")
    // Backing field needed for lazy initialization.
    private var _matchOrNull: Match? = null

    /**
     * The match for this [Fingerprint], or `null` if no matches exist.
     */
    context(BytecodePatchContext)
    fun matchOrNull(): Match? {
        if (_matchOrNull != null) return _matchOrNull

        // Use string instruction literals to resolve faster.
        var stringLiterals =
            if (strings != null) {
                // Old deprecated string declaration.
                strings
            } else {
                filters?.filterIsInstance<StringFilter>()
                    ?.map { it.string(this@BytecodePatchContext) }
            }

        stringLiterals?.mapNotNull {
            lookupMaps.methodsByStrings[it]
        }?.minByOrNull { it.size }?.let { methodClasses ->
            methodClasses.forEach { (method, classDef) ->
                val match = matchOrNull(classDef, method)
                if (match != null) {
                    _matchOrNull = match
                    return match
                }
            }
        }

        classes.forEach { classDef ->
            val match = matchOrNull(classDef)
            if (match != null) {
                _matchOrNull = match
                return match
            }
        }

        return null
    }

    /**
     * Match using a [ClassDef].
     *
     * @param classDef The class to match against.
     * @return The [Match] if a match was found or if the
     * fingerprint is already matched to a method, null otherwise.
     */
    context(BytecodePatchContext)
    fun matchOrNull(
        classDef: ClassDef,
    ): Match? {
        if (_matchOrNull != null) return _matchOrNull

        for (method in classDef.methods) {
            val match = matchOrNull(classDef, method)
            if (match != null) {
                _matchOrNull = match
                return match
            }
        }

        return null
    }

    /**
     * Match using a [Method].
     * The class is retrieved from the method.
     *
     * @param method The method to match against.
     * @return The [Match] if a match was found or if the fingerprint is previously matched to a method,
     * otherwise `null`.
     */
    context(BytecodePatchContext)
    fun matchOrNull(
        method: Method,
    ): Match? {
        if (_matchOrNull != null) return _matchOrNull

        return matchOrNull(classBy { method.definingClass == it.type }!!.immutableClass, method)
    }

    /**
     * Match using a [Method].
     *
     * @param method The method to match against.
     * @param classDef The class the method is a member of.
     * @return The [Match] if a match was found or if the fingerprint is previously matched to a method,
     * otherwise `null`.
     */
    context(BytecodePatchContext)
    fun matchOrNull(
        classDef: ClassDef,
        method: Method,
    ): Match? {
        if (_matchOrNull != null) return _matchOrNull

        if (returnType != null && !method.returnType.startsWith(returnType)) {
            return null
        }

        if (accessFlags != null && accessFlags != method.accessFlags) {
            return null
        }

        // TODO: parseParameters()
        if (parameters != null && !parametersStartsWith(method.parameterTypes, parameters)) {
            return null
        }

        if (custom != null && !custom.invoke(method, classDef)) {
            return null
        }

        // Legacy string declarations.
        val stringMatches: List<Match.StringMatch>? = if (strings == null) {
            null
        } else {
            buildList {
                val instructions = method.instructionsOrNull ?: return null

                var stringsList : MutableList<String>? = null

                instructions.forEachIndexed { instructionIndex, instruction ->
                    if (
                        instruction.opcode != Opcode.CONST_STRING &&
                        instruction.opcode != Opcode.CONST_STRING_JUMBO
                    ) {
                        return@forEachIndexed
                    }

                    val string = ((instruction as ReferenceInstruction).reference as StringReference).string
                    if (stringsList == null) {
                        stringsList = strings.toMutableList()
                    }
                    val index = stringsList.indexOfFirst(string::contains)
                    if (index == -1) return@forEachIndexed

                    add(Match.StringMatch(string, instructionIndex))
                    stringsList.removeAt(index)
                }

                if (stringsList == null || stringsList.isNotEmpty()) return null
            }
        }

        val instructionMatches = if (filters == null) {
            null
        } else {
            val instructions = method.instructionsOrNull?.toList() ?: return null

            fun matchFilters() : List<Match.InstructionMatch>? {
                val lastMethodIndex = instructions.lastIndex
                var instructionMatches : MutableList<Match.InstructionMatch>? = null

                var firstInstructionIndex = 0
                firstFilterLoop@ while (true) {
                    // Matched index of the first filter.
                    var firstFilterIndex = -1
                    var subIndex = firstInstructionIndex

                    for (filterIndex in filters.indices) {
                        val filter = filters[filterIndex]
                        val maxIndex = (subIndex + filter.maxInstructionsBefore)
                            .coerceAtMost(lastMethodIndex)
                        var instructionsMatched = false

                        while (subIndex <= maxIndex) {
                            val instruction = instructions[subIndex]
                            if (filter.matches(this@BytecodePatchContext, method, instruction, subIndex)) {
                                if (filterIndex == 0) {
                                    firstFilterIndex = subIndex
                                }
                                if (instructionMatches == null) {
                                    instructionMatches = ArrayList<Match.InstructionMatch>(filters.size)
                                }
                                instructionMatches += Match.InstructionMatch(filter, subIndex, instruction)
                                instructionsMatched = true
                                subIndex++
                                break
                            }
                            subIndex++
                        }

                        if (!instructionsMatched) {
                            if (filterIndex == 0) {
                                return null // First filter has no more matches to start from.
                            }

                            // Try again with the first filter, starting from
                            // the next possible first filter index.
                            firstInstructionIndex = firstFilterIndex + 1
                            instructionMatches?.clear()
                            continue@firstFilterLoop
                        }
                    }

                    // All instruction filters matches.
                    return instructionMatches
                }
            }

            matchFilters() ?: return null
        }

        _matchOrNull = Match(
            classDef,
            method,
            instructionMatches,
            stringMatches,
        )

        return _matchOrNull
    }

    fun patchException() = PatchException("Failed to match the fingerprint: $this")

    override fun toString() = name


    /**
     * The match for this [Fingerprint].
     *
     * @return The [Match] of this fingerprint.
     * @throws PatchException If the [Fingerprint] failed to match.
     */
    context(BytecodePatchContext)
    fun match() = matchOrNull() ?: throw patchException()

    /**
     * Match using a [ClassDef].
     *
     * @param classDef The class to match against.
     * @return The [Match] of this fingerprint.
     * @throws PatchException If the fingerprint failed to match.
     */
    context(BytecodePatchContext)
    fun match(
        classDef: ClassDef,
    ) = matchOrNull(classDef) ?: throw patchException()

    /**
     * Match using a [Method].
     * The class is retrieved from the method.
     *
     * @param method The method to match against.
     * @return The [Match] of this fingerprint.
     * @throws PatchException If the fingerprint failed to match.
     */
    context(BytecodePatchContext)
    fun match(
        method: Method,
    ) = matchOrNull(method) ?: throw patchException()

    /**
     * Match using a [Method].
     *
     * @param method The method to match against.
     * @param classDef The class the method is a member of.
     * @return The [Match] of this fingerprint.
     * @throws PatchException If the fingerprint failed to match.
     */
    context(BytecodePatchContext)
    fun match(
        method: Method,
        classDef: ClassDef,
    ) = matchOrNull(classDef, method) ?: throw patchException()

    /**
     * The class the matching method is a member of, or null if this fingerprint did not match.
     */
    context(BytecodePatchContext)
    val originalClassDefOrNull
        get() = matchOrNull()?.originalClassDef

    /**
     * The matching method, or null of this fingerprint did not match.
     */
    context(BytecodePatchContext)
    val originalMethodOrNull
        get() = matchOrNull()?.originalMethod

    /**
     * The mutable version of [originalClassDefOrNull].
     *
     * Accessing this property allocates a [ClassProxy].
     * Use [originalClassDefOrNull] if mutable access is not required.
     */
    context(BytecodePatchContext)
    val classDefOrNull
        get() = matchOrNull()?.classDef

    /**
     * The mutable version of [originalMethodOrNull].
     *
     * Accessing this property allocates a [ClassProxy].
     * Use [originalMethodOrNull] if mutable access is not required.
     */
    context(BytecodePatchContext)
    val methodOrNull
        get() = matchOrNull()?.method

    /**
     * The match for the opcode pattern, or null if this fingerprint did not match.
     */
    context(BytecodePatchContext)
    @Deprecated("instead use instructionMatchesOrNull")
    val patternMatchOrNull : PatternMatch?
        get() {
            val match = this.matchOrNull()
            if (match == null || match.instructionMatchesOrNull == null) {
                return null
            }
            return match.patternMatch
        }

    /**
     * The match for the instruction filters, or null if this fingerprint did not match.
     */
    context(BytecodePatchContext)
    val instructionMatchesOrNull
        get() = matchOrNull()?.instructionMatchesOrNull

    /**
     * The matches for the strings, or null if this fingerprint did not match.
     *
     * This does not give matches for strings declared using [string] instruction filters.
     */
    context(BytecodePatchContext)
    @Deprecated("Instead use string instructions and `instructionMatchesOrNull()`")
    val stringMatchesOrNull
        get() = matchOrNull()?.stringMatchesOrNull

    /**
     * The class the matching method is a member of.
     *
     * @throws PatchException If the fingerprint has not been matched.
     */
    context(BytecodePatchContext)
    val originalClassDef
        get() = match().originalClassDef

    /**
     * The matching method.
     *
     * @throws PatchException If the fingerprint has not been matched.
     */
    context(BytecodePatchContext)
    val originalMethod
        get() = match().originalMethod

    /**
     * The mutable version of [originalClassDef].
     *
     * Accessing this property allocates a [ClassProxy].
     * Use [originalClassDef] if mutable access is not required.
     *
     * @throws PatchException If the fingerprint has not been matched.
     */
    context(BytecodePatchContext)
    val classDef
        get() = match().classDef

    /**
     * The mutable version of [originalMethod].
     *
     * Accessing this property allocates a [ClassProxy].
     * Use [originalMethod] if mutable access is not required.
     *
     * @throws PatchException If the fingerprint has not been matched.
     */
    context(BytecodePatchContext)
    val method
        get() = match().method

    /**
     * The match for the opcode pattern.
     *
     * @throws PatchException If the fingerprint has not been matched.
     */
    context(BytecodePatchContext)
    @Deprecated("Instead use instructionMatch")
    val patternMatch
        get() = match().patternMatch

    /**
     * Instruction filter matches.
     *
     * @throws PatchException If the fingerprint has not been matched.
     */
    context(BytecodePatchContext)
    val instructionMatches
        get() = match().instructionMatches

    /**
     * The matches for the strings declared using `strings()`.
     * This does not give matches for strings declared using [string] instruction filters.
     *
     * @throws PatchException If the fingerprint has not been matched.
     */
    context(BytecodePatchContext)
    @Deprecated("Instead use string instructions and `instructionMatches()`")
    val stringMatches
        get() = match().stringMatches
}

/**
 * A match of a [Fingerprint].
 *
 * @param originalClassDef The class the matching method is a member of.
 * @param originalMethod The matching method.
 * @param _instructionMatches The match for the instruction filters.
 * @param _stringMatches The matches for the strings declared using `strings()`.
 */
context(BytecodePatchContext)
class Match internal constructor(
    val originalClassDef: ClassDef,
    val originalMethod: Method,
    private val _instructionMatches: List<InstructionMatch>?,
    private val _stringMatches: List<StringMatch>?,
) {
    /**
     * The mutable version of [originalClassDef].
     *
     * Accessing this property allocates a [ClassProxy].
     * Use [originalClassDef] if mutable access is not required.
     */
    val classDef by lazy { proxy(originalClassDef).mutableClass }

    /**
     * The mutable version of [originalMethod].
     *
     * Accessing this property allocates a [ClassProxy].
     * Use [originalMethod] if mutable access is not required.
     */
    val method by lazy { classDef.methods.first { MethodUtil.methodSignaturesMatch(it, originalMethod) } }

    @Deprecated("Instead use instructionMatches", ReplaceWith("instructionMatches"))
    val patternMatch by lazy {
        if (_instructionMatches == null) throw PatchException("Did not match $this")
        @SuppressWarnings("deprecation")
        PatternMatch(_instructionMatches.first().index, _instructionMatches.last().index)
    }

    val instructionMatches
        get() = _instructionMatches ?: throw PatchException("Fingerprint declared no instruction filters")
    val instructionMatchesOrNull = _instructionMatches

    @Deprecated("Instead use string instructions and `instructionMatches()`")
    val stringMatches
        get() = _stringMatches ?: throw PatchException("Fingerprint declared no strings")
    @Deprecated("Instead use string instructions and `instructionMatchesOrNull()`")
    val stringMatchesOrNull = _stringMatches

    /**
     * A match for an opcode pattern.
     * @param startIndex The index of the first opcode of the pattern in the method.
     * @param endIndex The index of the last opcode of the pattern in the method.
     */
    @Deprecated("Instead use InstructionMatch")
    class PatternMatch internal constructor(
        val startIndex: Int,
        val endIndex: Int,
    )

    /**
     * A match for a string.
     *
     * @param string The string that matched.
     * @param index The index of the instruction in the method.
     */
    @Deprecated("Instead use string instructions and `InstructionMatch`")
    class StringMatch internal constructor(val string: String, val index: Int)

    /**
     * A match for a [InstructionFilter].
     * @param filter The filter that matched
     * @param index The instruction index it matched with.
     * @param instruction The instruction that matched.
     */
    class InstructionMatch internal constructor(
        val filter : InstructionFilter,
        val index: Int,
        val instruction: Instruction
    ) {
        @Suppress("UNCHECKED_CAST")
        fun <T> getInstruction(): T = instruction as T
    }
}

/**
 * A builder for [Fingerprint].
 *
 * @property name Name of the fingerprint, and usually identical to the variable name.
 * @property accessFlags The exact access flags using values of [AccessFlags].
 * @property returnType The return type compared using [String.startsWith].
 * @property parameters The parameters of the method. Partial matches allowed and follow the same rules as [returnType].
 * @property instructionFilters Filters to match the method instructions.
 * @property strings A list of the strings compared each using [String.contains].
 * @property customBlock A custom condition for this fingerprint.
 *
 * @constructor Create a new [FingerprintBuilder].
 */
class FingerprintBuilder(val name: String) {
    private var accessFlags: Int? = null
    private var returnType: String? = null
    private var parameters: List<String>? = null
    private var instructionFilters: List<InstructionFilter>? = null
    private var strings: List<String>? = null
    private var customBlock: ((method: Method, classDef: ClassDef) -> Boolean)? = null

    /**
     * Set the access flags.
     *
     * @param accessFlags The exact access flags using values of [AccessFlags].
     */
    fun accessFlags(accessFlags: Int) {
        this.accessFlags = accessFlags
    }

    /**
     * Set the access flags.
     *
     * @param accessFlags The exact access flags using values of [AccessFlags].
     */
    fun accessFlags(vararg accessFlags: AccessFlags) {
        this.accessFlags = accessFlags.fold(0) { acc, it -> acc or it.value }
    }

    /**
     * Set the return type.
     *
     * If [accessFlags] includes [AccessFlags.CONSTRUCTOR], then there is no need to
     * set a return type set since constructors are always void return type.
     *
     * @param returnType The return type compared using [String.startsWith].
     */
    fun returns(returnType: String) {
        this.returnType = returnType
    }

    /**
     * Set the parameters.
     *
     * @param parameters The parameters of the method.
     *                   Partial matches allowed and follow the same rules as [returnType].
     */
    fun parameters(vararg parameters: String) {
        this.parameters = parameters.toList()
    }

    private fun verifyNoFiltersSet() {
        if (this.instructionFilters != null) {
            throw PatchException("Instruction filters already set")
        }
    }

    /**
     * A pattern of opcodes, where each opcode must appear immediately after the previous.
     *
     * To use opcodes with other [InstructionFilter] objects,
     * instead use [instructions] with individual opcodes declared using [opcode].
     *
     * This method is identical to declaring individual opcode filters
     * with [InstructionFilter.maxInstructionsBefore] set to zero.
     *
     * Unless absolutely necessary, it is recommended to instead use [instructions]
     * with more fine grained filters.
     *
     * ```
     * opcodes(
     *    Opcode.INVOKE_VIRTUAL, // First opcode matches anywhere in the method.
     *    Opcode.MOVE_RESULT_OBJECT, // Must match exactly after INVOKE_VIRTUAL.
     *    Opcode.IPUT_OBJECT // Must match exactly after MOVE_RESULT_OBJECT.
     * )
     * ```
     * is identical to:
     * ```
     * instructions(
     *    opcode(Opcode.INVOKE_VIRTUAL), // First opcode matches anywhere in the method.
     *    opcode(Opcode.MOVE_RESULT_OBJECT, maxInstructionsBefore = 0), // Must match exactly after INVOKE_VIRTUAL.
     *    opcode(Opcode.IPUT_OBJECT, maxInstructionsBefore = 0) // Must match exactly after MOVE_RESULT_OBJECT.
     * )
     * ```
     *
     * @param opcodes An opcode pattern of instructions.
     *                Wildcard or unknown opcodes can be specified by `null`.
     */
    @Deprecated("Instead use the more precise `instructions()` declarations")
    fun opcodes(vararg opcodes: Opcode?) {
        verifyNoFiltersSet()
        this.instructionFilters = OpcodesFilter.listOfOpcodes(opcodes.toList())
    }

    /**
     * A pattern of opcodes from SMALI formatted text,
     * where each opcode must appear immediately after the previous opcode.
     *
     * Unless absolutely necessary, it is recommended to instead use [instructions].
     *
     * @param instructions A list of instructions or opcode names in SMALI format.
     * - Wildcard or unknown opcodes can be specified by `null`.
     * - Empty lines are ignored.
     * - Each instruction must be on a new line.
     * - The opcode name is enough, no need to specify the operands.
     *
     * @throws Exception If an unknown opcode is used.
     */
    fun opcodes(instructions: String) {
        verifyNoFiltersSet()
        this.instructionFilters = OpcodesFilter.listOfOpcodes(
            instructions.trimIndent().split("\n").filter {
                it.isNotBlank()
            }.map {
                // Remove any operands.
                val name = it.split(" ", limit = 1).first().trim()
                if (name == "null") return@map null

                opcodesByName[name] ?: throw Exception("Unknown opcode: $name")
            }
        )
    }

    /**
     * A list of instruction filters to match.
     */
    fun instructions(vararg instructionFilters: InstructionFilter) {
        verifyNoFiltersSet()
        this.instructionFilters = instructionFilters.toList()
    }

    /**
     * Set the strings.
     *
     * @param strings A list of strings compared each using [String.contains].
     */
    @Deprecated("Instead use `instruction()` filters and `string()` instruction declarations")
    fun strings(vararg strings: String) {
        this.strings = strings.toList()
    }

    /**
     * Set a custom condition for this fingerprint.
     *
     * @param customBlock A custom condition for this fingerprint.
     */
    fun custom(customBlock: (method: Method, classDef: ClassDef) -> Boolean) {
        this.customBlock = customBlock
    }

    fun build() : Fingerprint {
        // If access flags include constructor then
        // skip the return type check since it's always void.
        if (returnType?.equals("V") == true && accessFlags != null
            && AccessFlags.CONSTRUCTOR.isSet(accessFlags!!)
        ) {
            returnType = null
        }

        return Fingerprint(
            name,
            accessFlags,
            returnType,
            parameters,
            instructionFilters,
            strings,
            customBlock,
        )
    }


    private companion object {
        val opcodesByName = Opcode.entries.associateBy { it.name }
    }
}

class FingerprintDelegate(
    private val block: FingerprintBuilder.() -> Unit
) {
    // Must cache the fingerprint, otherwise on every usage
    // a new fingerprint is built and resolved.
    private var fingerprint: Fingerprint? = null

    // Called when you read the property, e.g. `val x by fingerprint { ... }`
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Fingerprint {
        if (fingerprint == null) {
            val builder = FingerprintBuilder(property.name)
            builder.block()
            fingerprint = builder.build()
        }

        return fingerprint!!
    }
}

/**
 * Create a [Fingerprint].
 *
 * @param block The block to build the [Fingerprint].
 *
 * @return The created [Fingerprint].
 */
fun fingerprint(block: FingerprintBuilder.() -> Unit): FingerprintDelegate {
    return FingerprintDelegate(block)
}


/**
 * Matches two lists of parameters, where the first parameter list
 * starts with the values of the second list.
 */
internal fun parametersStartsWith(
    targetMethodParameters: Iterable<CharSequence>,
    fingerprintParameters: Iterable<CharSequence>,
): Boolean {
    if (fingerprintParameters.count() != targetMethodParameters.count()) return false
    val fingerprintIterator = fingerprintParameters.iterator()

    targetMethodParameters.forEach {
        if (!it.startsWith(fingerprintIterator.next())) return false
    }

    return true
}



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
     * @param enclosingMethod The method of that contains [instruction].
     * @param instruction The instruction to check for a match.
     * @param methodIndex The index of [instruction] in the enclosing [enclosingMethod].
     *                    The index can be ignored unless a filter has an unusual reason,
     *                    such as matching only the last index of a method.
     */
    abstract fun matches(
        context: BytecodePatchContext,
        enclosingMethod: Method,
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
        enclosingMethod: Method,
        instruction: Instruction,
        methodIndex: Int
    ) : Boolean {
        return filters.any { filter ->
            filter.matches(context, enclosingMethod, instruction, methodIndex)
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
        enclosingMethod: Method,
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
        enclosingMethod: Method,
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
        enclosingMethod: Method,
        instruction: Instruction,
        methodIndex: Int
    ): Boolean {
        if (!super.matches(context, enclosingMethod, instruction, methodIndex)) {
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
        enclosingMethod: Method,
        instruction: Instruction,
        methodIndex: Int
    ): Boolean {
        if (!super.matches(context, enclosingMethod, instruction, methodIndex)) {
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
        enclosingMethod: Method,
        instruction: Instruction,
        methodIndex: Int
    ): Boolean {
        if (!super.matches(context, enclosingMethod, instruction, methodIndex)) {
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
                if (!(definingClass == "this" && referenceClass == enclosingMethod.definingClass)) {
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
        enclosingMethod: Method,
        instruction: Instruction,
        methodIndex: Int
    ): Boolean {
        if (!super.matches(context, enclosingMethod, instruction, methodIndex)) {
            return false
        }

        val reference = (instruction as? ReferenceInstruction)?.reference as? FieldReference
        if (reference == null) return false

        if (definingClass != null) {
            val referenceClass = reference.definingClass
            val definingClass = definingClass(context)

            if (!referenceClass.endsWith(definingClass)) {
                if (!(definingClass == "this" && referenceClass == enclosingMethod.definingClass)) {
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
        enclosingMethod: Method,
        instruction: Instruction,
        methodIndex: Int
    ): Boolean {
        if (!super.matches(context, enclosingMethod, instruction, methodIndex)) {
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
        enclosingMethod: Method,
        instruction: Instruction,
        methodIndex: Int
    ): Boolean {
        if (!super.matches(context, enclosingMethod, instruction, methodIndex)) {
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
        enclosingMethod: Method,
        instruction: Instruction,
        methodIndex: Int
    ): Boolean {
        return methodIndex == enclosingMethod.instructions.count() - 1 && filter.matches(
            context, enclosingMethod, instruction, methodIndex
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
