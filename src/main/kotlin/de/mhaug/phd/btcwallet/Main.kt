package de.mhaug.phd.btcwallet

import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.Security
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.Cipher
import java.awt.SystemColor.text
import java.nio.charset.Charset
import java.security.SecureRandom
import javax.crypto.spec.IvParameterSpec

/**
 * Blocklänge in Bytes
 */
val BLOCKLENGTH: Int = 16
val algorithm = "AES"
val cryptoprovider = "BC"

fun main(args : Array<String>) {
    val message = args[0]
    Security.addProvider(BouncyCastleProvider())

    val prng = SecureRandom()
    val keygenerator = KeyGenerator.getInstance(algorithm, cryptoprovider)
    val key = keygenerator.generateKey()
    val cipher: Cipher = Cipher.getInstance("$algorithm/CBC/PKCS5Padding", cryptoprovider)

    val iv = ByteArray(BLOCKLENGTH)
    cipher.init(Cipher.ENCRYPT_MODE, key, IvParameterSpec(iv))
    val textEncrypted: ByteArray = cipher.doFinal(message.toByteArray())

    cipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(iv))
    val textDecrypted: ByteArray = cipher.doFinal(textEncrypted)
    println(textDecrypted.toString(Charset.defaultCharset()))
}


