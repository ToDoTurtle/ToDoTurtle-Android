package com.eps.todoturtle.nfc.logic

enum class WriteOperationStatus {
    SUCCESS,
    NOT_WRITABLE,
    TAG_LOST,
    MESSAGE_FORMAT_ERROR,
    UNKNOWN_ERROR
}

enum class NfcStatus {
    NFC_NOT_ENABLED,
    NFC_NOT_SUPPORTED,
    NFC_WORKING
}
