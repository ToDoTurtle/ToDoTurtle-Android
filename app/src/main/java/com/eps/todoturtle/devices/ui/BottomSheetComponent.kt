package com.eps.todoturtle.devices.ui

import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eps.todoturtle.R
import com.eps.todoturtle.devices.logic.NFCDevice
import com.eps.todoturtle.note.logic.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    openState: MutableState<Boolean>,
    bottomSheetState: SheetState,
    composable: @Composable () -> Unit,
) {
    var openBottomSheet by rememberSaveable { openState }
    if (openBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { openBottomSheet = false },
            sheetState = bottomSheetState,
        ) {
            composable()
        }
    }
}

@Composable
fun noteMenu(
    note: Note,
    onEditListener: (Note) -> Unit = {},
    onDeleteListener: (Note) -> Unit = {},
    onCloseListener: () -> Unit = {},
): @Composable () -> Unit {
    return {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        ) {
            optionMenu(
                icon = Icons.Filled.Edit,
                text = stringResource(id = R.string.edit_note_menu),
                onEditClicked = { onEditListener(note) },
            )
            optionMenu(
                icon = Icons.Filled.Delete,
                text = stringResource(id = R.string.delete_note_menu),
                onEditClicked = { onDeleteListener(note) },
            )
            optionMenu(
                icon = Icons.Filled.Close,
                text = stringResource(id = R.string.close_menu),
                onEditClicked = onCloseListener,
            )
        }
    }
}

@Composable
fun deviceMenu(
    device: NFCDevice,
    drawableConverter: @Composable (Int) -> Drawable?,
    onEditListener: (NFCDevice) -> Unit = {},
    onDeleteListener: (NFCDevice) -> Unit = {},
    onDeleteActionListener: (String) -> Unit = {},
    onCloseListener: () -> Unit = {},
): @Composable () -> Unit {
    return {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        ) {
            NoteCardInfo(device = device, drawableConverter)
            optionMenu(
                icon = Icons.Filled.Edit,
                text = stringResource(id = R.string.edit_device_menu),
                onEditClicked = { onEditListener(device) },
            )
            optionMenu(
                icon = Icons.Filled.Delete,
                text = stringResource(id = R.string.delete_device_menu),
                onEditClicked = { onDeleteListener(device) },
            )
            optionMenu(
                icon = Icons.Filled.Delete,
                text = stringResource(id = R.string.delete_action_menu),
                onEditClicked = { onDeleteActionListener(device.identifier) },
            )
            optionMenu(
                icon = Icons.Filled.Close,
                text = stringResource(id = R.string.close_menu),
                onEditClicked = onCloseListener,
            )
        }
    }
}

@Composable
fun NoteCardInfo(device: NFCDevice, drawableConverter: @Composable (Int) -> Drawable?) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DeviceIcon(drawableConverter, device)
            Column {
                Text(
                    modifier = Modifier.padding(2.dp),
                    text = device.name,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                )
                Text(
                    modifier = Modifier.padding(2.dp),
                    text = device.description,
                    textAlign = TextAlign.Center,
                    maxLines = 3,
                )
                Text(
                    modifier = Modifier.padding(2.dp),
                    text = device.identifier,
                    fontStyle = FontStyle.Italic,
                )
            }
        }
    }
}

@Composable
fun optionMenu(
    icon: ImageVector,
    text: String,
    onEditClicked: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(10.dp)
            .clickable(onClick = onEditClicked)
            .fillMaxWidth(),
    ) {
        Icon(
            modifier = Modifier.padding(10.dp),
            imageVector = icon,
            contentDescription = text,
        )
        Text(
            text = text,
            fontSize = 14.sp,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ShowDeviceLongPressOptions() {
    deviceMenu(
        device = NFCDevice(
            name = "Test",
            description = "Dummy Description",
            identifier = "fakeidentifier",
            iconResId = R.drawable.car,
        ),
        drawableConverter = @Composable { id ->
            AppCompatResources.getDrawable(
                LocalContext.current,
                id,
            )
        },
        onCloseListener = {},
    )
}
