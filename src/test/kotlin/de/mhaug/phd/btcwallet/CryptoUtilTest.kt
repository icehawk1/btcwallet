package de.mhaug.phd.btcwallet

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths

class CryptoUtilTest {
    @Test
    fun signingTestPositive() {
        val keys = CryptoUtil.generateKey()
        val message = Files.readAllBytes(Paths.get("settings.gradle"))
        val signature = CryptoUtil.signData(message, keys)
        println(signature.joinToString())
        println(message.toString(Charset.defaultCharset()))
        assertTrue(CryptoUtil.verifySignature(message, keys.public, signature))
    }

    @Test
    fun signingTestNegative() {
        val keys = CryptoUtil.generateKey()
        val signedmessage = Files.readAllBytes(Paths.get("settings.gradle"))
        val verifiedmessage = Files.readAllBytes(Paths.get("build.gradle"))
        val signature = CryptoUtil.signData(signedmessage, keys)
        assertFalse(CryptoUtil.verifySignature(verifiedmessage, keys.public, signature))
    }
}
