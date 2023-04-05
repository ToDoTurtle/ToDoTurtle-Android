package com.eps.todoturtle.note.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.eps.todoturtle.R
import com.eps.todoturtle.note.logic.Note
import com.eps.todoturtle.ui.theme.activeOnPrimaryContainer
import com.eps.todoturtle.ui.theme.inactiveOnPrimaryContainer

@Composable
fun Note(
    modifier: Modifier = Modifier,
    note: Note,
    inHistory: Boolean,
    onCheckClick: () -> Unit = {},
) {
    var isExpanded by remember { mutableStateOf(false) }

    NoteContainer(
        isExpanded = isExpanded,
        onClick = { isExpanded = !isExpanded },
    ) {
        NoteHead(
            title = note.title,
            isChecked = inHistory,
            onCheckClick = { onCheckClick() },
        )
        if (isExpanded) NoteBody(note.description)
    }
}

@Composable
fun NoteContainer(
    isExpanded: Boolean,
    onClick: () -> Unit,
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
            .clickable { onClick() }
            .background(MaterialTheme.colorScheme.primaryContainer)
            .fillMaxWidth(),
    ) {
        content()
    }
}

@Composable
fun NoteHead(
    title: String,
    icons: NoteIcons = NoteIcons(
        hasDeadline = false,
        hasNfc = false,
        hasNotifications = false,
    ),
    isChecked: Boolean,
    onCheckClick: () -> Unit = {},
) {
    Row(
        modifier = Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = { onCheckClick() },
            modifier = Modifier
                .weight(0.1f)
                .padding(end = 4.dp),
        )
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                textDecoration = if (isChecked) TextDecoration.LineThrough else TextDecoration.None,
            ),
            modifier = Modifier
                .weight(0.8f)
                .padding(8.dp),
            textAlign = TextAlign.Start,
        )
        Column(
            modifier = Modifier
                .weight(0.1f)
                .padding(end = 4.dp),
        ) {
            NotificationsIcon(isActive = icons.hasNotifications)
            NfcIcon(isActive = icons.hasNfc)
            DeadlineIcon(isActive = icons.hasDeadline)
        }
    }
}

@Composable
fun NoteBody(description: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = description,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 13.dp, bottom = 8.dp, end = 8.dp),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}

data class NoteIcons(
    val hasNotifications: Boolean,
    val hasNfc: Boolean,
    val hasDeadline: Boolean,
)

@Composable
fun NfcIcon(isActive: Boolean) {
    val vectorResource = ImageVector.Companion.vectorResource(id = R.drawable.nfc)
    Icon(
        modifier = Modifier.scale(0.9f),
        imageVector = vectorResource,
        contentDescription = "NFC",
        tint = if (isActive) MaterialTheme.colorScheme.activeOnPrimaryContainer else MaterialTheme.colorScheme.inactiveOnPrimaryContainer,
    )
}

@Composable
fun NotificationsIcon(isActive: Boolean) {
    val vectorResource = ImageVector.Companion.vectorResource(id = R.drawable.notifications)
    Icon(
        modifier = Modifier.scale(0.95f),
        imageVector = vectorResource,
        contentDescription = "Notifications",
        tint = if (isActive) MaterialTheme.colorScheme.activeOnPrimaryContainer else MaterialTheme.colorScheme.inactiveOnPrimaryContainer,
    )
}

@Composable
fun DeadlineIcon(isActive: Boolean) {
    val vectorResource = ImageVector.Companion.vectorResource(id = R.drawable.deadline)
    Icon(
        modifier = Modifier.scale(0.9f),
        imageVector = vectorResource,
        contentDescription = "Deadline",
        tint = if (isActive) MaterialTheme.colorScheme.activeOnPrimaryContainer else MaterialTheme.colorScheme.inactiveOnPrimaryContainer,
    )
}
