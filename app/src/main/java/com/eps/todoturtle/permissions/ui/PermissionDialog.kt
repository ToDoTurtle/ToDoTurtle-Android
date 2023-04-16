package com.eps.todoturtle.permissions.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.eps.todoturtle.R
import com.eps.todoturtle.permissions.logic.providers.text.PermissionTextProvider

@Composable
fun PermissionDialog(
    permissionTextProvider: PermissionTextProvider,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Divider()
                Text(
                    text = stringResource(id = if (isPermanentlyDeclined) R.string.permission_grant_permission else R.string.permission_ok),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { if (isPermanentlyDeclined) onGoToAppSettingsClick() else onOkClick() }
                        .padding(16.dp),
                )
            }
        },
        title = {
            Text(text = stringResource(id = R.string.permission_required))
        },
        text = {
            Text(
                text = permissionTextProvider.getDescription(isPermanentlyDeclined),
            )
        },
        modifier = Modifier,
    )
}
