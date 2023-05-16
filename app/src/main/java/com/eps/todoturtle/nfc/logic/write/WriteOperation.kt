package com.eps.todoturtle.nfc.logic.write

import com.eps.todoturtle.nfc.logic.NfcParcelable

sealed class WriteOperation {
    class SUCCESS(val device: NfcParcelable) : WriteOperation()
    object NOT_WRITABLE : WriteOperation()
    object TAG_LOST : WriteOperation()
    object MESSAGE_FORMAT_ERROR : WriteOperation()
    object UNKNOWN_ERROR : WriteOperation()
}