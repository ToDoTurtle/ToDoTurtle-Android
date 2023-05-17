package com.eps.todoturtle.devices.ui

import android.graphics.drawable.Drawable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eps.todoturtle.R
import com.eps.todoturtle.action.infra.InMemoryActionRepository
import com.eps.todoturtle.action.logic.ActionViewModel
import com.eps.todoturtle.action.ui.LinkNoteFormDialog
import com.eps.todoturtle.devices.logic.DevicesViewModel
import com.eps.todoturtle.devices.logic.NFCDevice
import com.eps.todoturtle.shared.logic.extensions.dataStore
import com.eps.todoturtle.ui.theme.ToDoTurtleTheme
import com.eps.todoturtle.ui.theme.noteScreenButton
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import kotlinx.coroutines.launch

@Composable
fun DeviceScreen(
    devicesViewModel: DevicesViewModel,
    actionViewModel: ActionViewModel,
    newDeviceAdded: Boolean = false,
    onEditDevice: (NFCDevice) -> Unit = {},
    onNewDeviceAddedOkay: () -> Unit = {},
    onAddDevice: () -> Unit = {},
) {
    DeviceScreenLayout(
        devices = devicesViewModel.getDevices(),
        actionViewModel = actionViewModel,
        newDeviceAdded,
        @Composable { id: Int -> devicesViewModel.getDrawable(id) },
        { device ->
            devicesViewModel.setCurrentEditDevice(device)
            onEditDevice(device)
        },
        { device -> devicesViewModel.delete(device) },
        onNewDeviceAddedOkay,
        onAddDevice
    )
}

@Composable
fun DeviceScreenLayout(
    devices: Collection<NFCDevice>,
    actionViewModel: ActionViewModel,
    newDeviceAdded: Boolean,
    iconToDrawableConverter: @Composable (Int) -> Drawable?,
    onEditListener: (NFCDevice) -> Unit = {},
    onDeleteListener: (NFCDevice) -> Unit = {},
    onNewDeviceAddedOkay: () -> Unit = {},
    addDevice: () -> Unit = {},
) {
    Scaffold(
        floatingActionButton = { AddDeviceButton(onClick = addDevice) },
        snackbarHost = {
            if (newDeviceAdded) {
                NfcWriteSuccessSnackbar(onNewDeviceAddedOkay)
            }
        },
    ) {
        NFCDeviceList(
            devices.toList(),
            actionViewModel = actionViewModel,
            onEditListener,
            onDeleteListener,
            iconToDrawableConverter
        )
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
fun NFCDeviceList(
    devices: List<NFCDevice>,
    actionViewModel: ActionViewModel,
    onEditListener: (NFCDevice) -> Unit,
    onDeleteListener: (NFCDevice) -> Unit,
    iconToDrawableConverter: @Composable (Int) -> Drawable?,
) {
    LazyColumn(
        modifier = Modifier.padding(4.dp),
    ) {
        items(devices.size) { index ->
            NFCDeviceListItem(
                device = devices[index],
                actionViewModel = actionViewModel,
                onEditListener,
                onDeleteListener,
                iconToDrawableConverter,
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NFCDeviceListItem(
    device: NFCDevice,
    actionViewModel: ActionViewModel,
    onEditListener: (NFCDevice) -> Unit,
    onDeleteListener: (NFCDevice) -> Unit,
    iconToDrawableConverter: @Composable (Int) -> Drawable?,
) {
    val showBottomSheet = rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    Card(
        modifier = Modifier
            .padding(4.dp)
            .combinedClickable(
                onLongClick = { showBottomSheet.value = true },
            ) { },
    ) {
        DeviceCard(
            device = device,
            actionViewModel = actionViewModel,
            iconToDrawableConverter = iconToDrawableConverter,
        )
    }
    BottomSheet(
        showBottomSheet,
        bottomSheetState,
        deviceMenu(
            drawableConverter = iconToDrawableConverter,
            device = device,
            onEditListener = onEditListener,
            onDeleteListener = onDeleteListener,
            onDeleteActionListener = {actionViewModel.removeAction(it)},
            onCloseListener = {
                scope.launch {
                    bottomSheetState.hide()
                }.invokeOnCompletion {
                    if (!bottomSheetState.isVisible) {
                        showBottomSheet.value = false
                    }
                }
            },
        ),
    )
}

@Composable
fun DeviceCard(
    device: NFCDevice,
    actionViewModel: ActionViewModel,
    iconToDrawableConverter: @Composable (Int) -> Drawable?,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        var inEditDeviceDialog by rememberSaveable { mutableStateOf(false) }
        DeviceIcon(iconToDrawableConverter, device = device)
        DeviceInformation(device = device)
        EditDeviceButton(alreadyConfigured = actionViewModel.getAction(device.identifier) != null) {
            inEditDeviceDialog = true
        }
        if (inEditDeviceDialog) {
            actionViewModel.loadActionForDevice(device.identifier)
            LinkNoteFormDialog(
                actionViewModel = actionViewModel,
                onDismissRequest = {
                    inEditDeviceDialog = false
                    actionViewModel.abortAction()
                },
                onDoneClick = {
                    actionViewModel.saveAction(device.identifier)
                },
                onSavedAction = {
                    inEditDeviceDialog = false
                },
                onCloseClick = {
                    actionViewModel.abortAction()
                    inEditDeviceDialog = false
                })
        }
    }
}

@Composable
fun DeviceIcon(iconToDrawableConverter: @Composable (Int) -> Drawable?, device: NFCDevice) {
    Icon(
        painter = rememberDrawablePainter(drawable = iconToDrawableConverter(device.iconResId)),
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
                ),
                NFCDevice(
                    name = "Kitchen",
                    description = "My Kitchen",
                    identifier = "1234567890",
                    iconResId = R.drawable.headphones,
                ),
            ),
            actionViewModel = ActionViewModel(InMemoryActionRepository()),
            false,
            iconToDrawableConverter = @Composable { null },
        )
    }
}
