package com.eps.todoturtle.note.ui

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eps.todoturtle.R
import com.eps.todoturtle.devices.ui.BottomSheet
import com.eps.todoturtle.devices.ui.getOut
import com.eps.todoturtle.devices.ui.noteMenu
import com.eps.todoturtle.map.logic.MapLauncher
import com.eps.todoturtle.map.ui.MapView
import com.eps.todoturtle.note.logic.Note
import com.eps.todoturtle.ui.theme.activeOnSecondaryContainer
import com.eps.todoturtle.ui.theme.inactiveOnSecondaryContainer
import com.eps.todoturtle.ui.theme.noteContainer
import com.eps.todoturtle.ui.theme.onNoteContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Note(
    note: Note,
    inHistory: Boolean,
    onCheckClick: () -> Unit = {},
    onEditClick: (Note) -> Unit = {},
    onDeleteClick: (Note) -> Unit = {},
) {
    var isExpanded by remember { mutableStateOf(false) }
    val showBottomSheet = rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    NoteContainer(
        onClick = { isExpanded = !isExpanded },
        onLongClick = { showBottomSheet.value = true },
    ) {
        NoteHead(
            note = note,
            isChecked = inHistory,
            onCheckClick = { onCheckClick() },
        )
        if (isExpanded && (note.description.isNotEmpty() || note.location != null)) {
            val modifier = Modifier.padding(start = 13.dp, bottom = 8.dp, end = 8.dp)
            note.apply {
                location?.let {
                    MapNoteBody(
                        modifier = modifier,
                        description = note.description,
                        latitude = it.latitude,
                        longitude = it.longitude,
                    )
                } ?: NoteBody(modifier = modifier, description = note.description)
            }
        }
    }

    BottomSheet(
        showBottomSheet,
        bottomSheetState,
        noteMenu(
            note = note,
            onEditListener = onEditClick,
            onDeleteListener = onDeleteClick,
            onCloseListener = { bottomSheetState.getOut(scope, showBottomSheet) },
        )
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteContainer(
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(start = 32.dp, end = 24.dp, top = 8.dp, bottom = 8.dp)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 200,
                    delayMillis = 0,
                    easing = LinearEasing,
                ),
            )
            .clip(MaterialTheme.shapes.medium)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick,
            )
            .background(MaterialTheme.colorScheme.noteContainer)
            .fillMaxWidth(),
    ) {
        content()
    }
}

@Composable
fun NoteHead(
    note: Note,
    isChecked: Boolean,
    onCheckClick: () -> Unit = {},
) {
    Row(
        modifier = Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.onNoteContainer,
            ),
            checked = isChecked,
            onCheckedChange = { onCheckClick() },
            modifier = Modifier
                .weight(weight = 0.1f)
                .padding(end = 4.dp),
        )
        Text(
            text = note.title,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onNoteContainer,
                textDecoration = if (isChecked) TextDecoration.LineThrough else TextDecoration.None,
            ),
            modifier = Modifier
                .weight(weight = 0.8f)
                .padding(8.dp),
            textAlign = TextAlign.Start,
        )
        Column(
            modifier = Modifier
                .weight(weight = 0.1f)
                .padding(end = 4.dp),
        ) {
            NotificationsIcon(isActive = note.notificationTime != null)
            NfcIcon(isActive = note.isNFCGenerated)
            DeadlineIcon(isActive = note.deadlineTime != null)
        }
    }
}

@Composable
fun NoteBody(
    modifier: Modifier,
    description: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = description,
            fontWeight = FontWeight.W400,
            modifier = Modifier.padding(start = 13.dp, bottom = 8.dp, end = 8.dp),
            color = MaterialTheme.colorScheme.onNoteContainer,
        )
    }
}

@Composable
fun MapNoteBody(
    modifier: Modifier,
    description: String,
    latitude: Double,
    longitude: Double,
) {
    val mapIntent = MapLauncher.getMapIntent(latitude, longitude)
    val launcher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            onResult = { },
        )

    NoteBody(description = description, modifier = modifier)
    MapView(
        modifier = Modifier.height(200.dp),
        startLat = latitude,
        startLon = longitude,
    ) { launcher.launch(mapIntent) }
}

data class NoteIcons(
    val hasNotifications: Boolean = false,
    val hasNfc: Boolean = false,
    val hasDeadline: Boolean = false,
)

@Composable
fun NfcIcon(isActive: Boolean) {
    val vectorResource = ImageVector.Companion.vectorResource(id = R.drawable.devices_filled)
    Icon(
        imageVector = vectorResource,
        contentDescription = stringResource(id = R.string.note_icon_nfc_desc),
        tint = if (isActive) {
            MaterialTheme.colorScheme.activeOnSecondaryContainer
        } else {
            MaterialTheme.colorScheme.inactiveOnSecondaryContainer
        },
    )
}

@Composable
fun NotificationsIcon(isActive: Boolean) {
    val vectorResource = ImageVector.Companion.vectorResource(id = R.drawable.notification_filled)
    Icon(
        imageVector = vectorResource,
        contentDescription = stringResource(id = R.string.note_icon_notification_desc),
        tint = if (isActive) {
            MaterialTheme.colorScheme.activeOnSecondaryContainer
        } else {
            MaterialTheme.colorScheme.inactiveOnSecondaryContainer
        },
    )
}

@Composable
fun DeadlineIcon(isActive: Boolean) {
    val vectorResource = ImageVector.Companion.vectorResource(id = R.drawable.deadline_filled)
    Icon(
        imageVector = vectorResource,
        contentDescription = stringResource(id = R.string.note_icon_deadline_desc),
        tint = if (isActive) {
            MaterialTheme.colorScheme.activeOnSecondaryContainer
        } else {
            MaterialTheme.colorScheme.inactiveOnSecondaryContainer
        },
    )
}
