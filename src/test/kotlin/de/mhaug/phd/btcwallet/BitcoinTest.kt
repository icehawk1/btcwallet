package de.mhaug.phd.btcwallet

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class Base58ParserTest {
    val instance = Base58Parser()

    @Test
    fun testSimpleEncode() {
        assertEquals("ZiCa", instance.encode("abc".toByteArray(), false))
    }

    @Test
    fun testSimpleDecode() {
        assertEquals("abc".toByteArray(), instance.decode("ZiCa", false))
    }

    @Test
    fun testValidChecksum() {
        val input = "sadfdas dsazfoguziol32 dsauiofhou324 xcyvno84378".toByteArray()
        val encoded = instance.encode(input, true)
        val decoded = instance.decode(encoded, true)
        assertEquals(input, decoded)
    }

    @Test
    fun testInvalidChecksum() {
        val input = "sadfdas dsazfoguziol xcyvno84378".toByteArray()
        val encoded = instance.encode(input, true) + 0x00
        assertThrows(IllegalArgumentException::class.java) { instance.decode(encoded, true) }
    }
}