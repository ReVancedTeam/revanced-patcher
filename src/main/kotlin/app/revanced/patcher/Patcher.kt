package app.revanced.patcher

import app.revanced.patcher.patch.*
import kotlinx.coroutines.flow.flow
import java.io.Closeable
import java.util.logging.Logger

/**
 * A Patcher.
 *
 * @param config The configuration to use for the patcher.
 */
class Patcher(private val config: PatcherConfig) : Closeable {
    private val logger = Logger.getLogger(this::class.java.name)

    /**
     * The context containing the current state of the patcher.
     */
    val context = PatcherContext(config)

    init {
        context.resourceContext.decodeResources(ResourcePatchContext.ResourceMode.NONE)
    }

    /**
     * Add patches.
     *
     * @param patches The patches to add.
     */
    operator fun plusAssign(patches: Set<Patch<*>>) {
        // Add all patches to the executablePatches set.
        context.executablePatches += patches

        // Add all patches and their dependencies to the allPatches set.
        patches.forEach { patch ->
            fun Patch<*>.addRecursively() =
                also(context.allPatches::add).dependencies.forEach(Patch<*>::addRecursively)

            patch.addRecursively()
        }

        fun Patch<*>.anyRecursively(predicate: (Patch<*>) -> Boolean): Boolean =
            predicate(this) || dependencies.any { dependency -> dependency.anyRecursively(predicate) }

        context.allPatches.let { patches ->
            // Check, if what kind of resource mode is required.
            config.resourceMode = if (patches.any { patch -> patch.anyRecursively { it is ResourcePatch } }) {
                ResourcePatchContext.ResourceMode.FULL
            } else if (patches.any { patch -> patch.anyRecursively { it is RawResourcePatch } }) {
                ResourcePatchContext.ResourceMode.RAW_ONLY
            } else {
                ResourcePatchContext.ResourceMode.NONE
            }
        }
    }

    /**
     * Execute added patches.
     *
     * @return A flow of [PatchResult]s.
     */
    operator fun invoke() = flow {
        fun Patch<*>.execute(
            executedPatches: LinkedHashMap<Patch<*>, PatchResult>,
        ): PatchResult {
            // If the patch was executed before or failed, return it's the result.
            executedPatches[this]?.let { patchResult ->
                patchResult.exception ?: return patchResult

                return PatchResult(this, PatchException("The patch '$this' failed previously"))
            }

            // Recursively execute all dependency patches.
            dependencies.forEach { dependency ->
                dependency.execute(executedPatches).exception?.let {
                    return PatchResult(
                        this,
                        PatchException(
                            "The patch \"$this\" depends on \"$dependency\" which raised an exception:\n${it.stackTraceToString()}",
                        ),
                    )
                }
            }

            // Execute the patch.
            return try {
                execute(context)

                PatchResult(this)
            } catch (exception: PatchException) {
                PatchResult(this, exception)
            } catch (exception: Exception) {
                PatchResult(this, PatchException(exception))
            }.also { executedPatches[this] = it }
        }

        // Prevent from decoding the app manifest twice if it is not needed.
        if (config.resourceMode != ResourcePatchContext.ResourceMode.NONE) {
            context.resourceContext.decodeResources(config.resourceMode)
        }

        logger.info("Executing patches")

        val executedPatches = LinkedHashMap<Patch<*>, PatchResult>()

        context.executablePatches.sortedBy { it.name }.forEach { patch ->
            val patchResult = patch.execute(executedPatches)

            // TODO: Only emit, if the patch has no finalizerBlock.
            //  if (patchResult.exception == null && patch.finalizerBlock != null) return@forEach

            emit(patchResult)
        }

        executedPatches.values.filter { it.exception == null }.asReversed().forEach { executionResult ->
            val patch = executionResult.patch

            val result =
                try {
                    patch.finalize(context)

                    executionResult
                } catch (exception: PatchException) {
                    PatchResult(patch, exception)
                } catch (exception: Exception) {
                    PatchResult(patch, PatchException(exception))
                }

            if (result.exception != null) {
                emit(
                    PatchResult(
                        patch,
                        PatchException(
                            "The patch \"$patch\" raised an exception: ${result.exception.stackTraceToString()}",
                            result.exception,
                        ),
                    ),
                )
            } else if (patch.name != null) {
                emit(result)
            }
        }
    }

    override fun close() = context.bytecodeContext.lookupMaps.close()

    /**
     * Compile and save patched APK files.
     *
     * @return The [PatcherResult] containing the patched APK files.
     */
    @OptIn(InternalApi::class)
    fun get() = PatcherResult(context.bytecodeContext.get(), context.resourceContext.get())
}
