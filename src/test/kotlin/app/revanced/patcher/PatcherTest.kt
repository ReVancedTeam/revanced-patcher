package app.revanced.patcher

import app.revanced.patcher.patch.*
import app.revanced.patcher.patch.BytecodePatchContext.LookupMaps
import app.revanced.patcher.util.ProxyClassList
import com.android.tools.smali.dexlib2.immutable.ImmutableClassDef
import com.android.tools.smali.dexlib2.immutable.ImmutableMethod
import io.mockk.*
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertAll
import java.util.logging.Logger
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

internal object PatcherTest {
    private lateinit var patcher: Patcher

    @BeforeEach
    fun setUp() {
        patcher = mockk<Patcher> {
            // Can't mock private fields, until https://github.com/mockk/mockk/issues/1244 is resolved.
            setPrivateField(
                "config",
                mockk<PatcherConfig> {
                    every { resourceMode } returns ResourcePatchContext.ResourceMode.NONE
                },
            )
            setPrivateField(
                "logger",
                Logger.getAnonymousLogger(),
            )

            every { context.bytecodeContext.classes } returns mockk(relaxed = true)
            every { this@mockk() } answers { callOriginal() }
        }
    }

    @Test
    fun `executes patches in correct order`() {
        val executed = mutableListOf<String>()

        val patches = setOf(
            bytecodePatch { execute { executed += "1" } },
            bytecodePatch {
                dependsOn(
                    bytecodePatch {
                        execute { executed += "2" }
                        finalize { executed += "-2" }
                    },
                    bytecodePatch { execute { executed += "3" } },
                )

                execute { executed += "4" }
                finalize { executed += "-1" }
            },
        )

        assert(executed.isEmpty())

        patches()

        assertEquals(
            setOf("1", "2", "3", "4", "-1", "-2"),
            executed.toSet(),
            "Unexpected patch results",
        )
    }

    @Test
    fun `handles execution of patches correctly when exceptions occur`() {
        val executed = mutableListOf<String>()

        infix fun Patch<*>.produces(equals: List<String>) {
            val patches = setOf(this)

            patches()

            assertEquals(equals, executed, "Expected patches to be executed in correct order.")

            executed.clear()
        }

        // No patches execute successfully,
        // because the dependency patch throws an exception inside the execute block.
        bytecodePatch {
            dependsOn(
                bytecodePatch {
                    execute { throw PatchException("1") }
                    finalize { executed += "-2" }
                },
            )

            execute { executed += "2" }
            finalize { executed += "-1" }
        } produces emptyList()

        // The dependency patch is executed successfully,
        // because only the dependant patch throws an exception inside the finalize block.
        // Patches that depend on a failed patch should not be executed,
        // but patches that are depended on by a failed patch should be executed.
        bytecodePatch {
            dependsOn(
                bytecodePatch {
                    execute { executed += "1" }
                    finalize { executed += "-2" }
                },
            )

            execute { throw PatchException("2") }
            finalize { executed += "-1" }
        } produces listOf("1", "-2")

        // Because the finalize block of the dependency patch is executed after the finalize block of the dependant patch,
        // the dependant patch executes successfully, but the dependency patch raises an exception in the finalize block.
        bytecodePatch {
            dependsOn(
                bytecodePatch {
                    execute { executed += "1" }
                    finalize { throw PatchException("-2") }
                },
            )

            execute { executed += "2" }
            finalize { executed += "-1" }
        } produces listOf("1", "2", "-1")

        // The dependency patch is executed successfully,
        // because the dependant patch raises an exception in the finalize block.
        // Patches that depend on a failed patch should not be executed,
        // but patches that are depended on by a failed patch should be executed.
        bytecodePatch {
            dependsOn(
                bytecodePatch {
                    execute { executed += "1" }
                    finalize { executed += "-2" }
                },
            )

            execute { executed += "2" }
            finalize { throw PatchException("-1") }
        } produces listOf("1", "2", "-2")
    }

    @Test
    fun `throws if unmatched fingerprint match is delegated`() {
        val patch = bytecodePatch {
            execute {
                // Fingerprint can never match.
                val fingerprint = fingerprint { }

                // Throws, because the fingerprint can't be matched.
                fingerprint.patternMatch
            }
        }

        assertTrue(
            patch().exception != null,
            "Expected an exception because the fingerprint can't match.",
        )
    }

    @Test
    fun `matches fingerprint`() {
        every { patcher.context.bytecodeContext.classes } returns ProxyClassList(
            mutableListOf(
                ImmutableClassDef(
                    "class",
                    0,
                    null,
                    null,
                    null,
                    null,
                    null,
                    listOf(
                        ImmutableMethod(
                            "class",
                            "method",
                            emptyList(),
                            "V",
                            0,
                            null,
                            null,
                            null,
                        ),
                    ),
                ),
            ),
        )

        val fingerprint = fingerprint { returns("V") }
        val fingerprint2 = fingerprint { returns("V") }
        val fingerprint3 = fingerprint { returns("V") }

        val patches = setOf(
            bytecodePatch {
                execute {
                    fingerprint.match(classes.first().methods.first())
                    fingerprint2.match(classes.first())
                    fingerprint3.originalClassDef
                }
            },
        )

        patches()

        with(patcher.context.bytecodeContext) {
            assertAll(
                "Expected fingerprints to match.",
                { assertNotNull(fingerprint.originalClassDefOrNull) },
                { assertNotNull(fingerprint2.originalClassDefOrNull) },
                { assertNotNull(fingerprint3.originalClassDefOrNull) },
            )
        }
    }

    private operator fun Set<Patch<*>>.invoke(): List<PatchResult> {
        every { patcher.context.executablePatches } returns toMutableSet()
        every { patcher.context.bytecodeContext.lookupMaps } returns LookupMaps(patcher.context.bytecodeContext.classes)
        every { with(patcher.context.bytecodeContext) { mergeExtension(any<BytecodePatch>()) } } just runs

        return runBlocking { patcher().toList() }
    }

    private operator fun Patch<*>.invoke() = setOf(this)().first()

    private fun Any.setPrivateField(field: String, value: Any) {
        this::class.java.getDeclaredField(field).apply {
            this.isAccessible = true
            set(this@setPrivateField, value)
        }
    }
}
