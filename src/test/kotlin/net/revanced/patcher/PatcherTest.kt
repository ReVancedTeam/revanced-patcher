package net.revanced.patcher

import net.revanced.patcher.cache.Cache
import net.revanced.patcher.patch.Patch
import net.revanced.patcher.patch.PatchResult
import net.revanced.patcher.patch.PatchResultSuccess
import net.revanced.patcher.signature.Signature
import net.revanced.patcher.util.ExtraTypes
import net.revanced.patcher.util.TestUtil
import net.revanced.patcher.writer.ASMWriter.insertAt
import net.revanced.patcher.writer.ASMWriter.setAt
import org.junit.jupiter.api.assertThrows
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.Type
import org.objectweb.asm.tree.*
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.Test
import kotlin.test.assertEquals

internal class PatcherTest {
    companion object {
        val testSignatures: Array<Signature> = arrayOf(
            // Java:
            // public static void main(String[] args) {
            //     System.out.println("Hello, world!");
            // }
            // Bytecode:
            // public static main(java.lang.String[] arg0) { // Method signature: ([Ljava/lang/String;)V
            //     getstatic java/lang/System.out:java.io.PrintStream
            //     ldc "Hello, world!" (java.lang.String)
            //     invokevirtual java/io/PrintStream.println(Ljava/lang/String;)V
            //     return
            // }
            Signature(
                "mainMethod",
                Type.VOID_TYPE,
                ACC_PUBLIC or ACC_STATIC,
                arrayOf(ExtraTypes.ArrayAny),
                arrayOf(
                    LDC,
                    INVOKEVIRTUAL
                )
            )
        )
    }

    @Test
    fun testPatcher() {
        val patcher = Patcher(
            PatcherTest::class.java.getResourceAsStream("/test1.jar")!!,
            ByteArrayOutputStream(),
            testSignatures
        )

        patcher.addPatches(
            object : Patch("TestPatch") {
                override fun execute(cache: Cache): PatchResult {
                    // Get the method from the resolver cache
                    val mainMethod = patcher.cache.methods["mainMethod"]
                    // Get the instruction list
                    val instructions = mainMethod.method.instructions!!

                    // Let's modify it, so it prints "Hello, ReVanced! Editing bytecode."
                    // Get the start index of our opcode pattern.
                    // This will be the index of the LDC instruction.
                    val startIndex = mainMethod.scanData.startIndex
                    TestUtil.assertNodeEqual(LdcInsnNode("Hello, world!"), instructions[startIndex]!!)
                    // Create a new LDC node and replace the LDC instruction.
                    val stringNode = LdcInsnNode("Hello, ReVanced! Editing bytecode.")
                    instructions.setAt(startIndex, stringNode)

                    // Now lets print our string twice!
                    // Insert our instructions after the second instruction by our pattern.
                    // This will place our instructions after the original INVOKEVIRTUAL call.
                    // You could also copy the instructions from the list and then modify the LDC instruction again,
                    // but this is to show a more advanced example of writing bytecode using the patcher and ASM.
                    instructions.insertAt(
                        startIndex + 1,
                        FieldInsnNode(
                            GETSTATIC,
                            Type.getInternalName(System::class.java), // "java/lang/System"
                            "out",
                            "L" + Type.getInternalName(PrintStream::class.java) // "Ljava/io/PrintStream"
                        ),
                        LdcInsnNode("Hello, ReVanced! Adding bytecode."),
                        MethodInsnNode(
                            INVOKEVIRTUAL,
                            Type.getInternalName(PrintStream::class.java), // "java/io/PrintStream"
                            "println",
                            Type.getMethodDescriptor(
                                Type.VOID_TYPE,
                                Type.getType(String::class.java)
                            ) // "(Ljava/lang/String;)V"
                        )
                    )

                    // Our code now looks like this:
                    // public static main(java.lang.String[] arg0) { // Method signature: ([Ljava/lang/String;)V
                    //     getstatic java/lang/System.out:java.io.PrintStream
                    //     ldc "Hello, ReVanced! Editing bytecode." (java.lang.String) // We overwrote this instruction.
                    //     invokevirtual java/io/PrintStream.println(Ljava/lang/String;)V
                    //     getstatic java/lang/System.out:java.io.PrintStream // This instruction and the 2 instructions below are written manually.
                    //     ldc "Hello, ReVanced! Adding bytecode." (java.lang.String)
                    //     invokevirtual java/io/PrintStream.println(Ljava/lang/String;)V
                    //     return
                    // }

                    // Finally, tell the patcher that this patch was a success.
                    // You can also return PatchResultError with a message.
                    // If an exception is thrown inside this function,
                    // a PatchResultError will be returned with the error message.
                    return PatchResultSuccess()
                }
            }
        )

        // Apply all patches loaded in the patcher
        val patchResult = patcher.applyPatches()
        // You can check if an error occurred
        for ((patchName, result) in patchResult) {
            if (result.isFailure) {
                throw Exception("Patch $patchName failed", result.exceptionOrNull()!!)
            }
        }

        patcher.save()
    }

    @Test
    fun `test patcher with no changes`() {
        val patcher = Patcher(
            PatcherTest::class.java.getResourceAsStream("/test1.jar")!!,
            ByteArrayOutputStream(),
            testSignatures
        )

        patcher.save()
    }

    @Test
    fun `should raise an exception because opcodes is empty`() {
        val sigName = "testMethod"
        val e = assertThrows<IllegalArgumentException>("Should raise an exception because opcodes is empty") {
            Patcher(
                PatcherTest::class.java.getResourceAsStream("/test1.jar")!!,
                ByteArrayOutputStream(),
                arrayOf(
                    Signature(
                        sigName,
                        Type.VOID_TYPE,
                        ACC_PUBLIC or ACC_STATIC,
                        arrayOf(ExtraTypes.ArrayAny),
                        emptyArray() // this is not allowed for non-search signatures!
                    )
                )
            )
        }
        assertEquals(
            "Opcode list for signature $sigName is empty. This is not allowed for non-search signatures.",
            e.message
        )
    }
}