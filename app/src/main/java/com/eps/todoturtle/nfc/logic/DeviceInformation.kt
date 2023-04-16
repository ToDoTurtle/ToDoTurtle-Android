package com.eps.todoturtle.nfc.logic

import android.nfc.NdefMessage
import android.nfc.NdefRecord


const val PACKAGE_NAME = "com.eps.todoturtle"

data class DeviceInformation(val uuid: String = ""): NfcParcelable {

    override fun toMessage(): NdefMessage {
        return NdefMessage(
            arrayOf(
                NdefRecord.createMime("application/$PACKAGE_NAME", uuid.toByteArray()),
            )
        )
    }

    companion object {
        fun fromBytes(bytes: ByteArray): DeviceInformation {
            return DeviceInformation(String(bytes))
        }
    }

}