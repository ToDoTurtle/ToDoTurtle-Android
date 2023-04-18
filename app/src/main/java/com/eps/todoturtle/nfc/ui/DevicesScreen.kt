package com.eps.todoturtle.nfc.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eps.todoturtle.R
import com.eps.todoturtle.nfc.logic.DevicesViewModel
import com.eps.todoturtle.nfc.logic.NFCDevice
import com.eps.todoturtle.note.ui.CompleteAddNoteFormDialog
import com.eps.todoturtle.shared.logic.extensions.dataStore
import com.eps.todoturtle.ui.theme.ToDoTurtleTheme
import com.eps.todoturtle.ui.theme.noteScreenButton

@Composable
fun DeviceScreen(
    devicesViewModel: DevicesViewModel,
    newDeviceAdded: Boolean = false,
    onNewDeviceAddedOkay: () -> Unit = {},
    onAddDevice: () -> Unit = {},
) {
    DeviceScreenLayout(
        devices = devicesViewModel.getDevices(),
        newDeviceAdded,
        onNewDeviceAddedOkay,
        onAddDevice,
    )
}

@Composable
fun DeviceScreenLayout(
    devices: List<NFCDevice>,
    newDeviceAdded: Boolean,
    onNewDeviceAddedOkay: () -> Unit,
    addDevice: () -> Unit,
) {
    Scaffold(
        floatingActionButton = { AddDeviceButton(onClick = addDevice) },
        snackbarHost = {
            if (newDeviceAdded) {
                NfcWriteSuccessSnackbar(onNewDeviceAddedOkay)
            }
        },
    ) {
        NFCDeviceList(devices)
    }
}

@Composable
fun AddDeviceButton(onClick: () -> Unit) {
    FloatingActionButton(
        containerColor = colorScheme.noteScreenButton,
        modifier = Modifier.padding(0.dp),
        onClick = onClick,
    ) {
        Icon(Icons.Filled.Add, contentDescription = null)
    }
}

@Composable
fun NFCDeviceList(devices: List<NFCDevice>) {
    LazyColumn(
        modifier = Modifier.padding(4.dp),
    ) {
        items(devices.size) { index ->
            NFCDeviceListItem(device = devices[index])
        }
    }
}

@Composable
fun NFCDeviceListItem(device: NFCDevice) {
    Card(
        modifier = Modifier.padding(4.dp),
    ) {
        DeviceCard(device = device)
    }
}

@Composable
fun DeviceCard(device: NFCDevice) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        var inDialog by rememberSaveable { mutableStateOf(false) }
        DeviceIcon(device = device)
        DeviceInformation(device = device)
        EditDeviceButton(alreadyConfigured = device.configured) {
            inDialog = true
        }
        if (inDialog) {
            CompleteAddNoteFormDialog(
                onDismissRequest = { inDialog = false },
                onDoneClick = { inDialog = false },
                onCloseClick = { inDialog = false },
            )
        }
    }
}

@Composable
fun DeviceIcon(device: NFCDevice) {
    Icon(
        painter = painterResource(id = device.iconResId),
        contentDescription = null,
        tint = colorScheme.onSurface,
        modifier = Modifier
            .size(48.dp)
            .padding(end = 16.dp),
    )
}

@Composable
fun RowScope.DeviceInformation(device: NFCDevice) {
    Column(modifier = Modifier.weight(1f)) {
        Text(text = device.name, fontWeight = FontWeight.Bold)
        Text(text = device.description, color = colorScheme.onSurface.copy(alpha = 0.6f))
        Text(text = device.identifier, color = colorScheme.onSurface.copy(alpha = 0.6f))
    }
}

@Composable
fun EditDeviceButton(alreadyConfigured: Boolean, onClick: () -> Unit) {
    val icon = if (alreadyConfigured) Icons.Filled.Edit else Icons.Filled.Add
    IconButton(onClick = onClick) {
        Icon(icon, contentDescription = null)
    }
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
                    contentDescription = stringResource(R.string.close),
                )
            }
        },
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = stringResource(R.string.nfc_write_success),
                tint = colorScheme.secondary,
                modifier = Modifier.padding(end = 8.dp),
            )
            Text(
                text = stringResource(R.string.nfc_write_success),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DevicesPreview() {
    ToDoTurtleTheme(LocalContext.current.dataStore) {
        DeviceScreenLayout(
            devices = listOf(
                NFCDevice(
                    name = "Car",
                    description = "My car",
                    identifier = "1234567890",
                    iconResId = R.drawable.car,
                    true,
                ),
                NFCDevice(
                    name = "Kitchen",
                    description = "My Kitchen",
                    identifier = "1234567890",
                    iconResId = R.drawable.headphones,
                    false,
                ),
            ),
            false,
            {},
        ) {}
    }
}
