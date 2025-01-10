@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package app.revanced.patcher

import app.revanced.patcher.extensions.InstructionExtensions.instructionsOrNull
import app.revanced.patcher.patch.BytecodePatchContext
import app.revanced.patcher.patch.PatchException
import app.revanced.patcher.util.proxy.ClassProxy
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.ClassDef
import com.android.tools.smali.dexlib2.iface.Method
import com.android.tools.smali.dexlib2.iface.instruction.ReferenceInstruction
import com.android.tools.smali.dexlib2.iface.reference.StringReference
import com.android.tools.smali.dexlib2.util.MethodUtil
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.withContext

/*
suspend inline fun <T, R> Iterable<T>.concurrentFirstNotNullOfOrNull(crossinline transform: (T) -> R?) =
    asFlow().flatMapMerge { value ->
        flow { transform(value)?.let { emit(it) } }
    }.firstOrNull()
*/
/*
suspend inline fun <T> Iterable<T>.concurrentFind(crossinline predicate: (T) -> Boolean) =
    asFlow().flatMapMerge { value -> flow { if (predicate(value)) emit(value) } }.firstOrNull()
*/
internal fun List<*>.chunks(count: Int): List<Pair<Int, Int>> {
    if (size <= count) return listOf(0 to lastIndex)

    val chunkSize = size / count
    val indices = MutableList(count) { (it*chunkSize) + 1 to (it + 1)*chunkSize }
    indices[0] = 0 to indices[0].second
    indices[indices.lastIndex] = indices[indices.lastIndex].first to lastIndex
    return indices
}
internal suspend inline fun <T> List<T>.concurrentFind(crossinline predicate: (T) -> Boolean): T? = coroutineScope {
    val cpus = Runtime.getRuntime().availableProcessors()
    val completableDeferred = CompletableDeferred<T?>(parent = coroutineContext.job)
    val jobs = chunks(cpus).map { (start, end) ->
        launch(Dispatchers.Default) {
            var i = start
            while (i <= end) {
                val element = this@concurrentFind[i]
                if (predicate(element)) {
                    completableDeferred.complete(element)
                    return@launch
                }
                i++
            }
        }
    }
    val notFoundJob = launch {
        jobs.joinAll()
        completableDeferred.complete(null)
    }

    val result = completableDeferred.await()
    jobs.forEach(Job::cancel)
    notFoundJob.cancel()
    result
}

internal suspend inline fun <T, R> List<T>.concurrentFirstNotNullOfOrNull(crossinline transform: (T) -> R?): R? = coroutineScope {
    val cpus = Runtime.getRuntime().availableProcessors()
    val completableDeferred = CompletableDeferred<R?>(parent = coroutineContext.job)
    val jobs = chunks(cpus).map { (start, end) ->
        launch(Dispatchers.Default) {
            var i = start
            while (i <= end) {
                val element = this@concurrentFirstNotNullOfOrNull[i]
                transform(element)?.let { value ->
                    completableDeferred.complete(value)
                    return@launch
                }
                i++
            }
        }
    }
    val notFoundJob = launch {
        jobs.joinAll()
        completableDeferred.complete(null)
    }

    val result = completableDeferred.await()
    jobs.forEach(Job::cancel)
    notFoundJob.cancel()
    result
}

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
 * @param accessFlags The exact access flags using values of [AccessFlags].
 * @param returnType The return type. Compared using [String.startsWith].
 * @param parameters The parameters. Partial matches allowed and follow the same rules as [returnType].
 * @param opcodes A pattern of instruction opcodes. `null` can be used as a wildcard.
 * @param strings A list of the strings. Compared using [String.contains].
 * @param custom A custom condition for this fingerprint.
 * @param fuzzyPatternScanThreshold The threshold for fuzzy scanning the [opcodes] pattern.
 */
class Fingerprint internal constructor(
    internal val accessFlags: Int?,
    internal val returnType: String?,
    internal val parameters: List<String>?,
    internal val opcodes: List<Opcode?>?,
    internal val strings: List<String>?,
    internal val custom: ((method: Method, classDef: ClassDef) -> Boolean)?,
    private val fuzzyPatternScanThreshold: Int,
) {
    @Suppress("ktlint:standard:backing-property-naming")
    // Backing field needed for lazy initialization.
    private var _matchOrNull: Match? = null

    /**
     * The match for this [Fingerprint]. Null if unmatched.
     */
    /*
    context(BytecodePatchContext)
    private val matchOrNull: Match?
        get() = matchOrNull()
    */

    /**
     * Match using [BytecodePatchContext.lookupMaps].
     *
     * Generally faster than the other [matchOrNull] overloads when there are many methods to check for a match.
     *
     * Fingerprints can be optimized for performance:
     * - Slowest: Specify [custom] or [opcodes] and nothing else.
     * - Fast: Specify [accessFlags], [returnType].
     * - Faster: Specify [accessFlags], [returnType] and [parameters].
     * - Fastest: Specify [strings], with at least one string being an exact (non-partial) match.
     *
     * @return The [Match] if a match was found or if the fingerprint is already matched to a method, null otherwise.
     */
    context(BytecodePatchContext)
    internal suspend fun matchOrNull(): Match? {
        if (_matchOrNull != null) return _matchOrNull

        val match = strings?.mapNotNull {
            lookupMaps.methodsByStrings[it]
        }?.minByOrNull { it.size }?.let { methodClasses ->
            methodClasses.forEach { (classDef, method) ->
                val match = matchOrNull(classDef, method)
                if (match != null) return@let match
            }

            null
        }
        if (match != null) return match

        return withContext(Dispatchers.Default) {
            classes.concurrentFirstNotNullOfOrNull<ClassDef, Match> { matchOrNull(it) }
        }
    }

    /**
     * Match using a [ClassDef].
     *
     * @param classDef The class to match against.
     * @return The [Match] if a match was found or if the fingerprint is already matched to a method, null otherwise.
     */
    context(BytecodePatchContext)
    fun matchOrNull(
        classDef: ClassDef,
    ): Match? {
        if (_matchOrNull != null) return _matchOrNull

        for (method in classDef.methods) {
            val match = matchOrNull(method, classDef)
            if (match != null) return match
        }

        return null
    }

    /**
     * Match using a [Method].
     * The class is retrieved from the method.
     *
     * @param method The method to match against.
     * @return The [Match] if a match was found or if the fingerprint is already matched to a method, null otherwise.
     */
    context(BytecodePatchContext)
    suspend fun matchOrNull(
        method: Method,
    ) = matchOrNull(method, classBy { method.definingClass == it.type }!!.immutableClass)

    /**
     * Match using a [Method].
     *
     * @param method The method to match against.
     * @param classDef The class the method is a member of.
     * @return The [Match] if a match was found or if the fingerprint is already matched to a method, null otherwise.
     */
    context(BytecodePatchContext)
    fun matchOrNull(
        method: Method,
        classDef: ClassDef,
    ): Match? {
        if (_matchOrNull != null) return _matchOrNull

        if (returnType != null && !method.returnType.startsWith(returnType)) {
            return null
        }

        if (accessFlags != null && accessFlags != method.accessFlags) {
            return null
        }

        fun parametersEqual(
            parameters1: Iterable<CharSequence>,
            parameters2: Iterable<CharSequence>,
        ): Boolean {
            if (parameters1.count() != parameters2.count()) return false
            val iterator1 = parameters1.iterator()
            parameters2.forEach {
                if (!it.startsWith(iterator1.next())) return false
            }
            return true
        }

        // TODO: parseParameters()
        if (parameters != null && !parametersEqual(parameters, method.parameterTypes)) {
            return null
        }

        if (custom != null && !custom.invoke(method, classDef)) {
            return null
        }

        val stringMatches: List<Match.StringMatch>? =
            if (strings != null) {
                buildList {
                    val instructions = method.instructionsOrNull ?: return null

                    val stringsList = strings.toMutableList()

                    instructions.forEachIndexed { instructionIndex, instruction ->
                        if (
                            instruction.opcode != Opcode.CONST_STRING &&
                            instruction.opcode != Opcode.CONST_STRING_JUMBO
                        ) {
                            return@forEachIndexed
                        }

                        val string =
                            ((instruction as ReferenceInstruction).reference as StringReference).string
                        val index = stringsList.indexOfFirst(string::contains)
                        if (index == -1) return@forEachIndexed

                        add(Match.StringMatch(string, instructionIndex))
                        stringsList.removeAt(index)
                    }

                    if (stringsList.isNotEmpty()) return null
                }
            } else {
                null
            }

        val patternMatch = if (opcodes != null) {
            val instructions = method.instructionsOrNull ?: return null

            fun patternScan(): Match.PatternMatch? {
                val fingerprintFuzzyPatternScanThreshold = fuzzyPatternScanThreshold

                val instructionLength = instructions.count()
                val patternLength = opcodes.size

                for (index in 0 until instructionLength) {
                    var patternIndex = 0
                    var threshold = fingerprintFuzzyPatternScanThreshold

                    while (index + patternIndex < instructionLength) {
                        val originalOpcode = instructions.elementAt(index + patternIndex).opcode
                        val patternOpcode = opcodes.elementAt(patternIndex)

                        if (patternOpcode != null && patternOpcode.ordinal != originalOpcode.ordinal) {
                            // Reaching maximum threshold (0) means,
                            // the pattern does not match to the current instructions.
                            if (threshold-- == 0) break
                        }

                        if (patternIndex < patternLength - 1) {
                            // If the entire pattern has not been scanned yet, continue the scan.
                            patternIndex++
                            continue
                        }

                        // The entire pattern has been scanned.
                        return Match.PatternMatch(
                            index,
                            index + patternIndex,
                        )
                    }
                }

                return null
            }

            patternScan() ?: return null
        } else {
            null
        }

        _matchOrNull = Match(
            method,
            patternMatch,
            stringMatches,
            classDef,
        )

        return _matchOrNull
    }

    private val exception get() = PatchException("Failed to match the fingerprint: $this")

    /**
     * The match for this [Fingerprint].
     *
     * @throws PatchException If the [Fingerprint] has not been matched.
     */
    context(BytecodePatchContext)
    private suspend fun match() = matchOrNull() ?: throw exception

    /**
     * Match using a [ClassDef].
     *
     * @param classDef The class to match against.
     * @return The [Match] if a match was found or if the fingerprint is already matched to a method, null otherwise.
     * @throws PatchException If the fingerprint has not been matched.
     */
    context(BytecodePatchContext)
    fun match(
        classDef: ClassDef,
    ) = matchOrNull(classDef) ?: throw exception

    /**
     * Match using a [Method].
     * The class is retrieved from the method.
     *
     * @param method The method to match against.
     * @return The [Match] if a match was found or if the fingerprint is already matched to a method, null otherwise.
     * @throws PatchException If the fingerprint has not been matched.
     */
    context(BytecodePatchContext)
    suspend fun match(
        method: Method,
    ) = matchOrNull(method) ?: throw exception

    /**
     * Match using a [Method].
     *
     * @param method The method to match against.
     * @param classDef The class the method is a member of.
     * @return The [Match] if a match was found or if the fingerprint is already matched to a method, null otherwise.
     * @throws PatchException If the fingerprint has not been matched.
     */
    context(BytecodePatchContext)
    fun match(
        method: Method,
        classDef: ClassDef,
    ) = matchOrNull(method, classDef) ?: throw exception

    /**
     * The class the matching method is a member of.
     */
    context(BytecodePatchContext)
    suspend fun originalClassDefOrNull() = matchOrNull()?.originalClassDef

    /**
     * The matching method.
     */
    context(BytecodePatchContext)
    suspend fun originalMethodOrNull() = matchOrNull()?.originalMethod

    /**
     * The mutable version of [originalClassDefOrNull].
     *
     * Accessing this property allocates a [ClassProxy].
     * Use [originalClassDefOrNull] if mutable access is not required.
     */
    context(BytecodePatchContext)
    suspend fun classDefOrNull() = matchOrNull()?.classDef

    /**
     * The mutable version of [originalMethodOrNull].
     *
     * Accessing this property allocates a [ClassProxy].
     * Use [originalMethodOrNull] if mutable access is not required.
     */
    context(BytecodePatchContext)
    suspend fun methodOrNull() = matchOrNull()?.method

    /**
     * The match for the opcode pattern.
     */
    context(BytecodePatchContext)
    suspend fun patternMatchOrNull() = matchOrNull()?.patternMatch

    /**
     * The matches for the strings.
     */
    context(BytecodePatchContext)
    suspend fun stringMatchesOrNull() = matchOrNull()?.stringMatches

    /**
     * The class the matching method is a member of.
     *
     * @throws PatchException If the fingerprint has not been matched.
     */
    context(BytecodePatchContext)
    suspend fun originalClassDef() = match().originalClassDef

    /**
     * The matching method.
     *
     * @throws PatchException If the fingerprint has not been matched.
     */
    context(BytecodePatchContext)
    suspend fun originalMethod() = match().originalMethod

    /**
     * The mutable version of [originalClassDef].
     *
     * Accessing this property allocates a [ClassProxy].
     * Use [originalClassDef] if mutable access is not required.
     *
     * @throws PatchException If the fingerprint has not been matched.
     */
    context(BytecodePatchContext)
    suspend fun classDef() = match().classDef

    /**
     * The mutable version of [originalMethod].
     *
     * Accessing this property allocates a [ClassProxy].
     * Use [originalMethod] if mutable access is not required.
     *
     * @throws PatchException If the fingerprint has not been matched.
     */
    context(BytecodePatchContext)
    suspend fun method() = match().method

    /**
     * The match for the opcode pattern.
     *
     * @throws PatchException If the fingerprint has not been matched.
     */
    context(BytecodePatchContext)
    suspend fun patternMatch() = match().patternMatch

    /**
     * The matches for the strings.
     *
     * @throws PatchException If the fingerprint has not been matched.
     */
    context(BytecodePatchContext)
    suspend fun stringMatches() = match().stringMatches
}

/**
 * A match of a [Fingerprint].
 *
 * @param originalClassDef The class the matching method is a member of.
 * @param originalMethod The matching method.
 * @param patternMatch The match for the opcode pattern.
 * @param stringMatches The matches for the strings.
 */
context(BytecodePatchContext)
class Match internal constructor(
    val originalMethod: Method,
    val patternMatch: PatternMatch?,
    val stringMatches: List<StringMatch>?,
    val originalClassDef: ClassDef,
) {
    /**
     * The mutable version of [originalClassDef].
     *
     * Accessing this property allocates a [ClassProxy].
     * Use [originalClassDef] if mutable access is not required.
     */
    val classDef by lazy { syncProxy(originalClassDef).mutableClass }

    /**
     * The mutable version of [originalMethod].
     *
     * Accessing this property allocates a [ClassProxy].
     * Use [originalMethod] if mutable access is not required.
     */
    val method by lazy {
        classDef.methods.first {
            MethodUtil.methodSignaturesMatch(
                it,
                originalMethod
            )
        }
    }

    /**
     * A match for an opcode pattern.
     * @param startIndex The index of the first opcode of the pattern in the method.
     * @param endIndex The index of the last opcode of the pattern in the method.
     */
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
    class StringMatch internal constructor(val string: String, val index: Int)
}

/**
 * A builder for [Fingerprint].
 *
 * @property accessFlags The exact access flags using values of [AccessFlags].
 * @property returnType The return type compared using [String.startsWith].
 * @property parameters The parameters of the method. Partial matches allowed and follow the same rules as [returnType].
 * @property opcodes An opcode pattern of the instructions. Wildcard or unknown opcodes can be specified by `null`.
 * @property strings A list of the strings compared each using [String.contains].
 * @property customBlock A custom condition for this fingerprint.
 * @property fuzzyPatternScanThreshold The threshold for fuzzy pattern scanning.
 *
 * @constructor Create a new [FingerprintBuilder].
 */
class FingerprintBuilder internal constructor(
    private val fuzzyPatternScanThreshold: Int = 0,
) {
    private var accessFlags: Int? = null
    private var returnType: String? = null
    private var parameters: List<String>? = null
    private var opcodes: List<Opcode?>? = null
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
     * @param returnType The return type compared using [String.startsWith].
     */
    fun returns(returnType: String) {
        this.returnType = returnType
    }

    /**
     * Set the parameters.
     *
     * @param parameters The parameters of the method. Partial matches allowed and follow the same rules as [returnType].
     */
    fun parameters(vararg parameters: String) {
        this.parameters = parameters.toList()
    }

    /**
     * Set the opcodes.
     *
     * @param opcodes An opcode pattern of instructions.
     * Wildcard or unknown opcodes can be specified by `null`.
     */
    fun opcodes(vararg opcodes: Opcode?) {
        this.opcodes = opcodes.toList()
    }

    /**
     * Set the opcodes.
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
        this.opcodes = instructions.trimIndent().split("\n").filter {
            it.isNotBlank()
        }.map {
            // Remove any operands.
            val name = it.split(" ", limit = 1).first().trim()
            if (name == "null") return@map null

            opcodesByName[name] ?: throw Exception("Unknown opcode: $name")
        }
    }

    /**
     * Set the strings.
     *
     * @param strings A list of strings compared each using [String.contains].
     */
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

    internal fun build() = Fingerprint(
        accessFlags,
        returnType,
        parameters,
        opcodes,
        strings,
        customBlock,
        fuzzyPatternScanThreshold,
    )

    private companion object {
        val opcodesByName = Opcode.entries.associateBy { it.name }
    }
}

/**
 * Create a [Fingerprint].
 *
 * @param fuzzyPatternScanThreshold The threshold for fuzzy pattern scanning. Default is 0.
 * @param block The block to build the [Fingerprint].
 *
 * @return The created [Fingerprint].
 */
fun fingerprint(
    fuzzyPatternScanThreshold: Int = 0,
    block: FingerprintBuilder.() -> Unit,
) = FingerprintBuilder(fuzzyPatternScanThreshold).apply(block).build()
