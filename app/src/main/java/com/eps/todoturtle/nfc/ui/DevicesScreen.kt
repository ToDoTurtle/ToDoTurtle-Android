package com.eps.todoturtle.nfc.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.eps.todoturtle.nfc.logic.NfcWriteViewModel
import com.eps.todoturtle.nfc.logic.WriteOperationStatus
import com.eps.todoturtle.ui.ErrorAlert


@Composable
fun DevicesScreen(viewModel: NfcWriteViewModel) {
    var deviceStatus: WriteOperationStatus? by rememberSaveable { mutableStateOf(null) }
    viewModel.onNfcOperation { deviceStatus = it }
    if (deviceStatus != null) {
        when (deviceStatus) {
            WriteOperationStatus.PREPARED -> LoadingAnimation()
            WriteOperationStatus.MESSAGE_FORMAT_ERROR -> ProgrammingError()
            WriteOperationStatus.NFC_NOT_ENABLED -> NfcNotEnabled { viewModel.goToNfcSettings() }
            else -> Text(deviceStatus.toString())
        }
    }
}

@Composable
fun ProgrammingError() {
    ErrorAlert(
        "Unexpected Error",
        "Something very bad happened, it may be a fault from the programmers, please send an email with the logcat",
        "Send",
        {})
}

@Composable
fun NfcNotEnabled(action: () -> Unit) {
    ErrorAlert(
        "NFC isn't enabled!",
        "Please, enable NFC on your device and try again",
        "Enable NFC",
        action
    )
}
