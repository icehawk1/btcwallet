package de.mhaug.phd.btcwallet

import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.SecureRandom
import java.security.Security


object CryptoUtil {
    init {
        Security.addProvider(BouncyCastleProvider())
        val prng = SecureRandom()
        val algorithm = "ECDSA"
        val cryptoprovider = "BC"

        val keyGen = KeyPairGenerator.getInstance("DSA", cryptoprovider)
        keyGen.initialize(1024, prng)
    }

    fun generateKey(): KeyPair = TODO("implement")
    fun signData(data: ByteArray, keys: KeyPair): ByteArray = TODO("implement")
    fun verifySignature(data: ByteArray, keys: KeyPair, signature: ByteArray): Boolean = TODO("implement")
}