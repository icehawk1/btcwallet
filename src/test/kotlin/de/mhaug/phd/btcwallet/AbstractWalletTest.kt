package de.mhaug.phd.btcwallet

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.security.KeyPair

abstract class WalletTest {
    abstract val instance: Wallet
    private val addr = instance.generateNewAddress()

    @Test
    fun testGenerateNewAddress() {
        val actual = instance.generateNewAddress()
        assertNotNull(actual)
    }

    @Test
    fun testRetrieveKey() {
        val actual = instance.retrieveKeypair(addr)
        assertNotNull(actual)
        assertTrue(keypairIsSane(actual))
    }

    @Test
    fun testImportKeypair() {
        val expected = instance.retrieveKeypair(addr)
        instance.importAddressFromKeypair(expected)
        val actual = instance.retrieveKeypair(addr)
        assertEquals(expected, actual)
    }

    @Test
    fun testTxSigning() {
        val txdata = "ich bin eine transaction".toByteArray()
        val signature = instance.signTransaction(txdata, addr)
        assertTrue(instance.verifyTransaction(txdata, signature, addr))
    }

    private fun keypairIsSane(actual: KeyPair): Boolean {
        val data = "abcdefg".toByteArray()
        val signature = CryptoUtil.signData(data, actual)
        return CryptoUtil.verifySignature(data, actual.public, signature)
    }
}
