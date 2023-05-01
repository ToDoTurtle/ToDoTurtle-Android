package com.eps.todoturtle.nfc.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.eps.todoturtle.R
import com.eps.todoturtle.nfc.logic.state.NfcStatus
import com.eps.todoturtle.nfc.logic.NfcWriteViewModel
import com.eps.todoturtle.nfc.logic.write.WriteOperation
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
    NfcStatusScreen(
        viewModel = viewModel,
        onNfcNotEnabled = viewModel::showNfcSettings,
        onNfcNotSupported = onNfcNotSupported
    ) {
        WritingScreen(
            viewModel = viewModel,
            onTagNotWriteable = onTagNotWriteable,
            onTagLost = onTagLost,
            unknownError = unknownError,
            onWriteSuccessful = onWriteSuccessful
        )
    }
}

@Composable
fun WritingScreen(
    viewModel: NfcWriteViewModel,
    onTagNotWriteable: () -> Unit = {},
    onTagLost: () -> Unit = {},
    unknownError: () -> Unit = {},
    onWriteSuccessful: () -> Unit = {},
) {
    WriteDevice(
        viewModel,
        onTagNotWriteable = onTagNotWriteable,
        onTagLost = onTagLost,
        unknownError = unknownError,
        onWriteSuccessful = onWriteSuccessful,
    )
}

@Composable
fun NfcStatusScreen(
    viewModel: NfcWriteViewModel,
    onNfcNotEnabled: () -> Unit = {},
    onNfcNotSupported: () -> Unit = {},
    onNfcEnabled: @Composable () -> Unit
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    when (status) {
        NfcStatus.NFC_NOT_ENABLED -> NfcNotEnabled { onNfcNotEnabled() }
        NfcStatus.NFC_NOT_SUPPORTED -> NfcNotSupported { onNfcNotSupported() }
        NfcStatus.NFC_WORKING -> onNfcEnabled()
    }
}

@Composable
fun WriteDevice(
    viewModel: NfcWriteViewModel,
    onTagNotWriteable: () -> Unit = {},
    onTagLost: () -> Unit = {},
    unknownError: () -> Unit = {},
    onWriteSuccessful: () -> Unit = {},
) {
    val deviceStatus by viewModel.writeResults.collectAsStateWithLifecycle()
    when (deviceStatus) {
        WriteOperation.MESSAGE_FORMAT_ERROR -> ProgrammingError()
        WriteOperation.SUCCESS -> onWriteSuccessful()
        WriteOperation.NOT_WRITABLE -> TagNotWriteable(onTagNotWriteable)
        WriteOperation.TAG_LOST -> TagLost(onTagLost)
        WriteOperation.UNKNOWN_ERROR -> UnknownError(unknownError)
        null -> WaitForNfcTag()
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
