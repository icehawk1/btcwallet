package de.mhaug.phd.btcwallet

import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.nio.file.Files
import java.nio.file.Paths
import java.security.KeyPairGenerator
import java.security.SecureRandom
import java.security.Security
import java.security.Signature

fun main(args : Array<String>) {
    Security.addProvider(BouncyCastleProvider())

    /**
     * Blocklänge in Bytes
     */
    val BLOCKLENGTH: Int = 16
    val algorithm = "ECDSA"
    val cryptoprovider = "BC"

    // Get instance and initialize a KeyPairGenerator object.
    val prng = SecureRandom()
    val keyGen = KeyPairGenerator.getInstance("DSA", cryptoprovider)
    keyGen.initialize(1024, prng)

    // Get a PrivateKey from the generated key pair.
    val keyPair = keyGen.generateKeyPair()

    // Get an instance of Signature object and initialize it.
    var signature = Signature.getInstance("SHA1withDSA", cryptoprovider)
    signature.initSign(keyPair.private)

    // Supply the data to be signed to the Signature object
    // using the update() method and generate the digital
    // signature.
    var bytes = Files.readAllBytes(Paths.get("settings.gradle"))
    signature.update(bytes)
    val digitalSignature = signature.sign()

//-----------------------
    bytes = Files.readAllBytes(Paths.get("settings.gradle"))
    signature = Signature.getInstance("SHA1withDSA", cryptoprovider)
    signature.initVerify(keyPair.public)
    signature.update(bytes)

    val verified = signature.verify(digitalSignature)
    if (verified) {
        println("Data verified.")
    } else {
        println("Cannot verify data.")
    }
}


