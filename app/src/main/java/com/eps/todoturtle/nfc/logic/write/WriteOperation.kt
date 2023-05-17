package com.eps.todoturtle.nfc.logic.write

import com.eps.todoturtle.nfc.logic.NfcParcelable

sealed class WriteOperation {
    class Success(val device: NfcParcelable) : WriteOperation()
    object NotWritable : WriteOperation()
    object TagLost : WriteOperation()
    object MessageFormatError : WriteOperation()
    object UnknownError : WriteOperation()
}
