package com.eps.todoturtle.nfc.logic

enum class WriteOperationStatus {
    PREPARED,
    SUCCESS,
    NOT_WRITABLE,
    TAG_LOST,
    MESSAGE_FORMAT_ERROR,
    UNKNOWN_ERROR,
    NFC_NOT_SUPPORTED,
    NFC_NOT_ENABLED
}
