package com.eps.todoturtle.nfc.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.eps.todoturtle.nfc.logic.NfcWriteViewModel
import com.eps.todoturtle.nfc.logic.WriteOperationStatus
import com.eps.todoturtle.ui.ErrorAlert


@Composable
fun DevicesScreen(viewModel: NfcWriteViewModel) {
    var deviceStatus: WriteOperationStatus? by rememberSaveable { mutableStateOf(null) }
    viewModel.onNfcOperation { deviceStatus = it }
    when (deviceStatus) {
        WriteOperationStatus.PREPARED -> LoadingAnimation()
        WriteOperationStatus.MESSAGE_FORMAT_ERROR -> ProgrammingError()
        WriteOperationStatus.NFC_NOT_ENABLED -> NfcNotEnabled { viewModel.goToNfcSettings() }
        WriteOperationStatus.SUCCESS -> NfcWriteSuccessSnackbar { }
        WriteOperationStatus.NOT_WRITABLE -> TODO()
        WriteOperationStatus.TAG_LOST -> TODO()
        WriteOperationStatus.UNKNOWN_ERROR -> TODO()
        WriteOperationStatus.NFC_NOT_SUPPORTED -> TODO()
        null -> Text("TODO")
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
                tint = colorScheme.secondary,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = "NFC has been successfully written!",
                style = typography.bodyMedium,
            )
        }
    }
}
