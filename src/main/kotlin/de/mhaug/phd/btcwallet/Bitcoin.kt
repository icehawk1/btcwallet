package de.mhaug.phd.btcwallet

import org.bitcoinj.core.AddressFormatException
import org.bitcoinj.core.Base58
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

class BitcoinAddress

class Base58Parser {

    fun encode(input: String, checksum: Boolean = true): String =
            encode(input.toByteArray(StandardCharsets.UTF_8), checksum)

    fun encode(input: ByteArray, checksum: Boolean = true): String {
        if (checksum) return Base58.encode(input + computeChecksum(input))
        else return Base58.encode(input)
    }

    fun decode(encoded: String, checksum: Boolean = true): ByteArray {
        try {
            if (checksum) return Base58.decodeChecked(encoded)
            else return Base58.decode(encoded)
        } catch (ex: AddressFormatException) {
            throw IllegalArgumentException(ex.message)
        }
    }

    /**
     * Computes the checksum for Base58Check
     * @param input Die Rohdaten ohne Prefix und ohne Checksum
     */
    private fun computeChecksum(input: ByteArray): ByteArray {
        val digest = MessageDigest.getInstance("SHA-256")
        val result = digest.digest(digest.digest(input))
        return result.sliceArray(0..3)
    }
}
