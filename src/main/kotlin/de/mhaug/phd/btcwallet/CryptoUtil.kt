package de.mhaug.phd.btcwallet

import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.*


object CryptoUtil {
    init {
        Security.addProvider(BouncyCastleProvider())
    }

    val prng = SecureRandom()
    val algorithm = "ECDSA"
    val cryptoprovider = "BC"

    val keyGen = KeyPairGenerator.getInstance("DSA", cryptoprovider)

    init {
        keyGen.initialize(1024, prng)
    }

    fun generateKey(): KeyPair = keyGen.generateKeyPair()
    fun signData(data: ByteArray, keys: KeyPair): ByteArray {
        var signengine = Signature.getInstance("SHA1withDSA", cryptoprovider)
        signengine.initSign(keys.private)
        signengine.update(data)
        val result = signengine.sign()
        return result
    }

    fun verifySignature(data: ByteArray, key: PublicKey, signature: ByteArray): Boolean {
        var signengine = Signature.getInstance("SHA1withDSA", cryptoprovider)
        signengine.initVerify(key)
        signengine.update(data)

        val result = signengine.verify(signature)
        return result
    }
}