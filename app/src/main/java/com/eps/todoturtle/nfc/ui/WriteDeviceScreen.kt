package com.eps.todoturtle.nfc.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.eps.todoturtle.nfc.logic.NfcWriteViewModel
import com.eps.todoturtle.nfc.logic.WriteOperationStatus
import com.eps.todoturtle.ui.ErrorAlert
import com.eps.todoturtle.ui.theme.ToDoTurtleTheme

@Composable
fun WriteDevice(
    viewModel: NfcWriteViewModel,
    onNfcNotSupported: () -> Unit = {},
    onTagNotWriteable: () -> Unit = {},
    onTagLost: () -> Unit = {},
    unknownError: () -> Unit = {},
    onWriteSuccessful: () -> Unit = {},
) {
    var deviceStatus: WriteOperationStatus? by rememberSaveable { mutableStateOf(null) }
    viewModel.onNfcOperation { deviceStatus = it }
    WriteDevice(
        deviceStatus,
        onNfcNotEnabled = viewModel::goToNfcSettings,
        onNfcNotSupported = onNfcNotSupported,
        onTagNotWriteable = onTagNotWriteable,
        onTagLost = onTagLost,
        unknownError = unknownError,
        onWriteSuccessful = onWriteSuccessful,
    )
}

@Composable
fun WriteDevice(
    deviceStatus: WriteOperationStatus?,
    onNfcNotEnabled: () -> Unit = {},
    onNfcNotSupported: () -> Unit = {},
    onTagNotWriteable: () -> Unit = {},
    onTagLost: () -> Unit = {},
    unknownError: () -> Unit = {},
    onWriteSuccessful: () -> Unit = {},
) {
    when (deviceStatus) {
        WriteOperationStatus.PREPARED -> LoadingAnimation()
        WriteOperationStatus.MESSAGE_FORMAT_ERROR -> ProgrammingError()
        WriteOperationStatus.NFC_NOT_ENABLED -> NfcNotEnabled(onNfcNotEnabled)
        WriteOperationStatus.SUCCESS -> onWriteSuccessful()
        WriteOperationStatus.NOT_WRITABLE -> TagNotWriteable(onTagNotWriteable)
        WriteOperationStatus.TAG_LOST -> TagLost(onTagLost)
        WriteOperationStatus.UNKNOWN_ERROR -> UnknownError(unknownError)
        WriteOperationStatus.NFC_NOT_SUPPORTED -> NfcNotSupported(onNfcNotSupported)
        null -> {}
    }
}

@Composable
fun ProgrammingError() {
    ErrorAlert(
        "Programming Error",
        "Something very bad happened, it may be a fault from the programmers, please send an email with the logcat",
        "Send"
    ) {}
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

@Composable
fun NfcNotSupported(action: () -> Unit) {
    ErrorAlert(
        "NFC isn't supported on your device!",
        "You can't use this feature on your device",
        "close",
        action
    )
}

@Composable
fun TagNotWriteable(action: () -> Unit) {
    ErrorAlert(
        "NFC tag isn't supported!",
        "Please use an NFC compatible tag, if you are using an NFC compatible tag, please try again",
        "close",
        action
    )
}

@Composable
fun TagLost(action: () -> Unit) {
    ErrorAlert(
        "You moved too fast!",
        "You moved too fast the NFC tag, please try again",
        "retry",
        action
    )
}

@Composable
fun UnknownError(action: () -> Unit) {
    ErrorAlert(
        "Something bad happened",
        "The write operation didn't go well, please try again",
        "retry",
        action
    )
}

@Preview(showBackground = true)
@Composable
fun WriteDeviceScreenPreview() {
    ToDoTurtleTheme {
        WriteDevice(deviceStatus = WriteOperationStatus.NFC_NOT_SUPPORTED)
    }
}

