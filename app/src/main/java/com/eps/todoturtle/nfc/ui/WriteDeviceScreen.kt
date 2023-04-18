package com.eps.todoturtle.nfc.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eps.todoturtle.R
import com.eps.todoturtle.nfc.logic.NfcWriteViewModel
import com.eps.todoturtle.nfc.logic.WriteOperationStatus
import com.eps.todoturtle.ui.ErrorAlert
import com.eps.todoturtle.ui.theme.ToDoTurtleTheme
import kotlinx.coroutines.delay

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
fun ProgrammingError() {
    ErrorAlert(
        stringResource(R.string.programming_error_nfc_tag),
        stringResource(R.string.programing_error_explain_nfc_tag),
        stringResource(R.string.send_error_nfc_tag)
    ) {}
}

@Composable
fun NfcNotEnabled(action: () -> Unit) {
    ErrorAlert(
        stringResource(R.string.nfc_not_enabled),
        stringResource(R.string.nfc_not_enabled_solution),
        stringResource(R.string.enable_nfc),
        action
    )
}

@Composable
fun NfcNotSupported(action: () -> Unit) {
    ErrorAlert(
        stringResource(R.string.nfc_not_supported),
        stringResource(R.string.nfc_not_supported_solution),
        stringResource(R.string.close),
        action,
    )
}

@Composable
fun TagNotWriteable(action: () -> Unit) {
    ErrorAlert(
        stringResource(R.string.nfc_tag_not_supported),
        stringResource(R.string.nfc_not_supported_solution),
        stringResource(R.string.close),
        action,
    )
}

@Composable
fun TagLost(action: () -> Unit) {
    ErrorAlert(
        stringResource(R.string.move_too_fast),
        stringResource(R.string.move_too_fast_solution),
        stringResource(R.string.retry),
        action,
    )
}

@Composable
fun UnknownError(action: () -> Unit) {
    ErrorAlert(
        stringResource(R.string.unknown_error_nfc),
        stringResource(R.string.unknown_error_nfc_solution),
        stringResource(R.string.retry),
        action,
    )
}

@Composable
fun WaitForNfcTag() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        AnimatedDisappearingText(text = stringResource(R.string.approach_nfc_tag), modifier = Modifier.weight(1f))
        LoadingAnimation(
            modifier = Modifier
                .padding(16.dp)
                .weight(1.5f)
        )
    }
}

@Composable
fun ColumnScope.AnimatedDisappearingText(text: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        var visible by rememberSaveable { mutableStateOf(true) }
        val animationDuration = 2000
        this@AnimatedDisappearingText.AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomCenter),
            visible = visible,
            enter = fadeIn(animationSpec = tween(durationMillis = animationDuration)),
            exit = fadeOut(animationSpec = tween(durationMillis = animationDuration))
        ) {
            Text(text)
        }
        LaunchedEffect(
            key1 = true,
            block = {
                while (true) {
                    delay(4000)
                    visible = !visible
                    delay(3000)
                    visible = !visible
                }
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WriteDeviceScreenPreview() {
    ToDoTurtleTheme {
        WaitForNfcTag()
    }
}
