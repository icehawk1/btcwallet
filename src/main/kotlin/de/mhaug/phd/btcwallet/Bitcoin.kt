package de.mhaug.phd.btcwallet

import java.util.*


class BitcoinAddress

class Base58Parser {
    private val ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz"
    private val ENCODED_ZERO = ALPHABET[0]

    private val INDEXES = IntArray(128)

    init {
        Arrays.fill(INDEXES, -1)
        for (i in 0 until ALPHABET.length) {
            INDEXES[ALPHABET[i].toInt()] = i
        }
    }

    fun encode(input: ByteArray, checksum: Boolean = true): String {
        if (input.isEmpty()) return ""
        var raw = input

        // Count leading zeros.
        var zeros: Int = 0
        while (zeros < raw.size && raw[zeros] == 0.toByte()) {
            ++zeros // optimization - skip leading zeros
        }

        // Convert base-256 digits to base-58 digits (plus conversion to ASCII characters)
        raw = Arrays.copyOf(raw, raw.size) // since we modify it in-place
        val encoded = CharArray(raw.size * 2) // upper bound
        var outputStart = encoded.size
        var inputStart: Int = zeros
        while (inputStart < raw.size) {
            encoded[--outputStart] = ALPHABET[divmod(raw, inputStart, 256, 58)]
            if (raw[inputStart] == 0.toByte()) {
                ++inputStart // optimization - skip leading zeros
            }
        }


        // Preserve exactly as many leading encoded zeros in output as there were leading zeros in input.
        while (outputStart < encoded.size && encoded[outputStart] == ENCODED_ZERO) {
            ++outputStart
        }
        while (--zeros >= 0) {
            encoded[--outputStart] = ENCODED_ZERO
        }
        // Return encoded string (including encoded leading zeros).
        return String(encoded, outputStart, encoded.size - outputStart)
    }

    fun decode(encoded: String, checksum: Boolean = true): ByteArray {
        if (encoded.isEmpty()) {
            return ByteArray(0)
        }
        // Convert the base58-encoded ASCII chars to a base58 byte sequence (base58 digits).
        val input58 = ByteArray(encoded.length)
        for (i in 0 until encoded.length) {
            val c = encoded[i]
            val digit = if (c.toInt() < 128) INDEXES[c.toInt()] else -1
            if (digit < 0) {
                throw IllegalArgumentException("Illegal character $c at position $i")
            }
            input58[i] = digit.toByte()
        }
        // Count leading zeros.
        var zeros = 0
        while (zeros < input58.size && input58[zeros].toInt() == 0) {
            ++zeros
        }
        // Convert base-58 digits to base-256 digits.
        val decoded = ByteArray(encoded.length)
        var outputStart = decoded.size
        var inputStart = zeros
        while (inputStart < input58.size) {
            decoded[--outputStart] = divmod(input58, inputStart, 58, 256).toByte()
            if (input58[inputStart].toInt() == 0) {
                ++inputStart // optimization - skip leading zeros
            }
        }
        // Ignore extra leading zeroes that were added during the calculation.
        while (outputStart < decoded.size && decoded[outputStart].toInt() == 0) {
            ++outputStart
        }
        // Return decoded data (including original number of leading zeros).
        return Arrays.copyOfRange(decoded, outputStart - zeros, decoded.size)
    }


    /**
     * Divides a number, represented as an array of bytes each containing a single digit
     * in the specified base, by the given divisor. The given number is modified in-place
     * to contain the quotient, and the return value is the remainder.
     *
     * @param number the number to divide
     * @param firstDigit the index within the array of the first non-zero digit
     * (this is used for optimization by skipping the leading zeros)
     * @param base the base in which the number's digits are represented (up to 256)
     * @param divisor the number to divide by (up to 256)
     * @return the remainder of the division operation
     */
    private fun divmod(number: ByteArray, firstDigit: Int, base: Int, divisor: Int): Int {
        // this is just long division which accounts for the base of the input digits
        var remainder = 0
        for (i in firstDigit until number.size) {
            val digit = number[i].toInt() and 0xFF
            val temp = remainder * base + digit
            number[i] = (temp / divisor).toByte()
            remainder = temp % divisor
        }
        return remainder
    }
}
