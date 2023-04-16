package com.eps.todoturtle.nfc.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.eps.todoturtle.nfc.logic.NfcWriteViewModel
import com.eps.todoturtle.nfc.logic.WriteOperationStatus


@Composable
fun DevicesScreen(viewModel: NfcWriteViewModel) {
    var message by rememberSaveable { mutableStateOf("") }
    viewModel.onNfcOperation {
        message = when (it) {
            WriteOperationStatus.SUCCESS -> "Written to NFC Tag"
            WriteOperationStatus.PREPARED -> "Prepared"
            WriteOperationStatus.NOT_WRITABLE -> "NFC isn't writable"
            WriteOperationStatus.TAG_LOST -> "You moved the NFC tag dummy"
            WriteOperationStatus.MESSAGE_FORMAT_ERROR -> "Error saving the NFC message because of the developer fault"
            WriteOperationStatus.UNKNOWN_ERROR -> "Unknown error during NFC"
            WriteOperationStatus.NFC_NOT_SUPPORTED -> "NFC isn't supported on this device"
            WriteOperationStatus.NFC_NOT_ENABLED -> "NFC isn't enabled on this device"
        }
    }
    Text(message)
}