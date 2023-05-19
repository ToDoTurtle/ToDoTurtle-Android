package com.eps.todoturtle.network.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eps.todoturtle.R

@Composable
fun NetworkWarningDialog(
    showDialog: Boolean,
    @StringRes reason: Int,
    @StringRes secondaryButtonText: Int,
    onSettingsClick: () -> Unit,
    onSecondaryButtonClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    if (!showDialog) return
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = stringResource(R.string.no_internet_connection)) },
        text = {
            Column {
                Text(
                    text = stringResource(reason),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.no_internet_warning_body),
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onSettingsClick() },
            ) {
                Text(text = stringResource(R.string.go_to_settings))
            }
        },
        dismissButton = {
            Row {
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(
                    onClick = { onSecondaryButtonClick() },
                ) {
                    Text(text = stringResource(secondaryButtonText))
                }
            }
        },
    )
}
