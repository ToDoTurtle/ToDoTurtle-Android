package com.eps.todoturtle.network.ui

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
import androidx.compose.ui.unit.dp
import com.eps.todoturtle.network.logic.NetworkAvailability

@Composable
fun NetworkWarningDialog(
    availability: NetworkAvailability,
    showDialog: Boolean,
    onSettingsClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    if (!showDialog) return
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = "No Internet Connection",
            )
        },
        text = {
            Column {
                Text(
                    text = "This action requires an internet connection.",
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Please connect to Wi-Fi and try again or go to network settings " +
                        "and enable cellular data usage.",
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onSettingsClick() },
            ) {
                Text(text = "Go to Settings")
            }
        },
        dismissButton = {
            Row {
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(
                    onClick = { onDismiss() },
                ) {
                    Text(text = "Retry")
                }
            }
        },
    )
}
