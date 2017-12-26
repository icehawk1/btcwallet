package de.mhaug.phd.btcwallet

import org.bouncycastle.jce.ECNamedCurveTable
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.*

object CryptoUtil {
    init {
        Security.addProvider(BouncyCastleProvider())
    }

    val prng = SecureRandom()
    val algorithm = "NONEwithECDSA"
    val cryptoprovider = "BC"

    val keyGen = KeyPairGenerator.getInstance("ECDSA", cryptoprovider)
    init {
        // Das ist die Kurve die bei Bitcoin verwendet wird
        keyGen.initialize(ECNamedCurveTable.getParameterSpec("secp256k1"), prng)
    }

    fun generateKey(): KeyPair = keyGen.generateKeyPair()
    fun signData(data: ByteArray, keys: KeyPair): ByteArray {
        var signengine = Signature.getInstance(algorithm, cryptoprovider)
        signengine.initSign(keys.private)
        signengine.update(data)
        val result = signengine.sign()
        return result
    }

    fun verifySignature(data: ByteArray, key: PublicKey, signature: ByteArray): Boolean {
        var signengine = Signature.getInstance(algorithm, cryptoprovider)
        signengine.initVerify(key)
        signengine.update(data)

        val result = signengine.verify(signature)
        return result
    }

}