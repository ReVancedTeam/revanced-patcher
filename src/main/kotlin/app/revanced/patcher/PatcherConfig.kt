package app.revanced.patcher

import app.revanced.patcher.patch.ResourcePatchContext
import brut.androlib.Config
import java.io.File
import java.util.logging.Logger

/**
 * The configuration for the patcher.
 *
 * @param apkFile The apk file to patch.
 * @param temporaryFilesPath A path to a folder to store temporary files in.
 * @param aaptBinaryPath A path to a custom aapt binary.
 * @param frameworkFileDirectory A path to the directory to cache the framework file in.
 */
class PatcherConfig(
    internal val apkFile: File,
    private val temporaryFilesPath: File = File("revanced-temporary-files"),
    aaptBinaryPath: File? = null,
    frameworkFileDirectory: String? = null,
) {
    /**
     * The configuration for the patcher.
     *
     * @param apkFile The apk file to patch.
     * @param temporaryFilesPath A path to a folder to store temporary files in.
     * @param aaptBinaryPath A path to a custom aapt binary.
     * @param frameworkFileDirectory A path to the directory to cache the framework file in.
     */
    @Deprecated(
        "Use the constructor with a File for aaptBinaryPath instead.",
        ReplaceWith("PatcherConfig(apkFile, temporaryFilesPath, aaptBinaryPath?.let { File(it) }, frameworkFileDirectory)"),
    )
    constructor(
        apkFile: File,
        temporaryFilesPath: File = File("revanced-temporary-files"),
        aaptBinaryPath: String? = null,
        frameworkFileDirectory: String? = null,
    ) : this(apkFile, temporaryFilesPath, aaptBinaryPath?.let { File(it) }, frameworkFileDirectory)

    private val logger = Logger.getLogger(PatcherConfig::class.java.name)

    /**
     * The mode to use for resource decoding and compiling.
     *
     * @see ResourcePatchContext.ResourceMode
     */
    internal var resourceMode = ResourcePatchContext.ResourceMode.NONE

    /**
     * The configuration for decoding and compiling resources.
     */
    internal val resourceConfig =
        Config.getDefaultConfig().apply {
            aaptBinary = aaptBinaryPath
            frameworkDirectory = frameworkFileDirectory
        }

    /**
     * The path to the temporary apk files directory.
     */
    internal val apkFiles = temporaryFilesPath.resolve("apk")

    /**
     * The path to the temporary patched files directory.
     */
    internal val patchedFiles = temporaryFilesPath.resolve("patched")

    /**
     * Initialize the temporary files' directories.
     * This will delete the existing temporary files directory if it exists.
     */
    internal fun initializeTemporaryFilesDirectories() {
        temporaryFilesPath.apply {
            if (exists()) {
                logger.info("Deleting existing temporary files directory")

                if (!deleteRecursively()) {
                    logger.severe("Failed to delete existing temporary files directory")
                }
            }
        }

        apkFiles.mkdirs()
        patchedFiles.mkdirs()
    }
}
