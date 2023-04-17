package com.eps.todoturtle.nfc.logic

import android.nfc.NdefMessage

interface NfcParcelable {
    fun toMessage(): NdefMessage
}
