package de.mhaug.phd.btcwallet

import java.security.KeyPair

class JBOK : Wallet() {
    override fun importAddressFromKeypair(keyPair: KeyPair) {
        TODO("not implemented")
    }

    override fun generateNewAddress(): BitcoinAddress {
        TODO("not implemented")
    }

    override fun retrieveKeypair(addr: BitcoinAddress): KeyPair {
        TODO("not implemented")
    }

    override fun deleteKey(addr: BitcoinAddress): Nothing {
        TODO("not implemented")
    }

    override fun signTransaction(tx: ByteArray, addr: BitcoinAddress?): ByteArray {
        TODO("not implemented")
    }

    override fun verifyTransaction(tx: ByteArray, signature: ByteArray, addr: BitcoinAddress?): Boolean {
        TODO("not implemented")
    }
}

