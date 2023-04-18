package com.eps.todoturtle.nfc.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.eps.todoturtle.R
import com.eps.todoturtle.ui.ErrorAlert

@Composable
fun ProgrammingError() {
    ErrorAlert(
        stringResource(R.string.programming_error_nfc_tag),
        stringResource(R.string.programing_error_explain_nfc_tag),
        stringResource(R.string.send_error_nfc_tag),
    ) {}
}

@Composable
fun NfcNotEnabled(action: () -> Unit) {
    ErrorAlert(
        stringResource(R.string.nfc_not_enabled),
        stringResource(R.string.nfc_not_enabled_solution),
        stringResource(R.string.enable_nfc),
        action,
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
