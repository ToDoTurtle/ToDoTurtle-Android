package com.eps.todoturtle.nfc.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eps.todoturtle.R
import com.eps.todoturtle.nfc.logic.NfcWriteViewModel
import com.eps.todoturtle.nfc.logic.WriteOperationStatus
import com.eps.todoturtle.shared.logic.extensions.dataStore
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
        WriteOperationStatus.PREPARED -> WaitForNfcTag()
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
fun WaitForNfcTag() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        AnimatedDisappearingText(
            text = stringResource(R.string.approach_nfc_tag),
            modifier = Modifier.weight(1f),
        )
        LoadingAnimation(
            modifier = Modifier
                .padding(16.dp)
                .weight(1.5f),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WriteDeviceScreenPreview() {
    ToDoTurtleTheme(LocalContext.current.dataStore) {
        WaitForNfcTag()
    }
}
