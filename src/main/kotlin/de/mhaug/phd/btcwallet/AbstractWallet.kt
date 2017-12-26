package de.mhaug.phd.btcwallet

import java.security.KeyPair

abstract class Wallet {
    abstract fun generateNewAddress(): BitcoinAddress
    abstract fun retrieveKeypair(addr: BitcoinAddress): KeyPair
    abstract fun deleteKey(addr: BitcoinAddress): Nothing
    abstract fun importAddressFromKeypair(keyPair: KeyPair)
    abstract fun signTransaction(tx: ByteArray, addr: BitcoinAddress? = null): ByteArray
    abstract fun verifyTransaction(tx: ByteArray, signature: ByteArray, addr: BitcoinAddress? = null): Boolean

}

enum class AddressType {
    P2PKH_LEGACY {
        override val prefix = 1
    },
    SEGWIT_OLD {
        override val prefix: Int = 3
    },
    SEGWIT_NEW {
        override val prefix: Int = 0xbc1
    };

    abstract val prefix: Int
}

