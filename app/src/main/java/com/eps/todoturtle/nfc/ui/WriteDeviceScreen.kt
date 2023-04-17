package com.eps.todoturtle.nfc.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
) {
    var deviceStatus: WriteOperationStatus? by rememberSaveable { mutableStateOf(null) }
    viewModel.onNfcOperation { deviceStatus = it }
    WriteDevice(
        deviceStatus,
        onNfcNotEnabled = viewModel::goToNfcSettings,
        onNfcNotSupported = onNfcNotSupported,
        onTagNotWriteable = onTagNotWriteable,
        onTagLost = onTagLost,
        unknownError = unknownError
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
) {
    when (deviceStatus) {
        WriteOperationStatus.PREPARED -> LoadingAnimation()
        WriteOperationStatus.MESSAGE_FORMAT_ERROR -> ProgrammingError()
        WriteOperationStatus.NFC_NOT_ENABLED -> NfcNotEnabled(onNfcNotEnabled)
        WriteOperationStatus.SUCCESS -> NfcWriteSuccessSnackbar { }
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

@Composable
fun NfcWriteSuccessSnackbar(onClose: () -> Unit) {
    Snackbar(
        action = {
            IconButton(
                onClick = onClose,
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                )
            }
        }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "NFC write success",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = "NFC has been successfully written!",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WriteDeviceScreenPreview() {
    ToDoTurtleTheme {
        WriteDevice(deviceStatus = WriteOperationStatus.NFC_NOT_SUPPORTED)
    }
}

