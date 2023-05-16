package com.eps.todoturtle.action.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.eps.todoturtle.R
import com.eps.todoturtle.note.ui.AddDeadlineDialog
import com.eps.todoturtle.note.ui.AddNotificationDialog
import com.eps.todoturtle.note.ui.NoteFormDescriptionTextField
import com.eps.todoturtle.note.ui.NoteFormTitle
import com.eps.todoturtle.note.ui.NoteFormTitleTextField
import com.eps.todoturtle.shared.ui.ResourceIcon

@Composable
fun LinkNoteFormDialog(
    onDismissRequest: () -> Unit = {},
    onDoneClick: () -> Unit = {},
    onCloseClick: () -> Unit = {},
    @StringRes titleTextId: Int = R.string.add_note_form_title,
) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
    ) {
        Card {
            LinkNoteForm(onDoneClick = {
                onDoneClick()

            }, onCloseClick = {
                onCloseClick()
                onDismissRequest()
            })
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LinkNoteForm(
    modifier: Modifier = Modifier,
    onDoneClick: () -> Unit = {},
    onCloseClick: () -> Unit = {}
) {
    val (titleFocusRequester, descriptionFocusRequester) = remember { FocusRequester.createRefs() }
    var choosingNotification by remember { mutableStateOf(false) }
    var choosingNotificationTime by remember { mutableStateOf(false) }
    var choosingDeadline by remember { mutableStateOf(false) }
    var choosingDeadlineTime by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        NoteFormTitle(titleTextId = R.string.add_note_form_title)
        Spacer(Modifier.height(8.dp))
        NoteFormTitleTextField(
            value = "Dummy",
            onValueChange = { },
            focusRequester = titleFocusRequester,
        )
        NoteFormDescriptionTextField(
            value = "Dummy",
            onValueChange = {},
            focusRequester = descriptionFocusRequester,
        )
        LocationFetchSwitch(checkedChanged = {})
        NoteFormIconTray(
            onAddNotificationClick = { choosingNotification = true },
            onAddDeadlineClick = { choosingDeadline = true },
            onCloseClick = onCloseClick,
            onDoneClick = onDoneClick,
        )
    }
    AddNotificationDialog(
        choosingDate = choosingNotification,
        choosingTime = choosingNotificationTime,
        onDismissRequest = {
            choosingNotification = false
            choosingNotificationTime = false
        },
        onBackClick = {
            choosingNotificationTime = false
        },
        onAddNotificationClick = { chosenTime ->
            choosingNotification = false
            choosingNotificationTime = false
//            viewModel.noteNotificationTime = chosenTime
        },
        onNextClick = {
            choosingNotificationTime = true
        },
    )

    AddDeadlineDialog(
        choosingDate = choosingDeadline,
        choosingTime = choosingDeadlineTime,
        onDismissRequest = {
            choosingDeadline = false
            choosingDeadlineTime = false
        },
        onBackClick = {
            choosingDeadlineTime = false
        },
        onAddDeadlineClick = { chosenTime ->
            choosingDeadline = false
            choosingDeadlineTime = false
//            viewModel.noteDeadlineTime = chosenTime
        },
        onNextClick = {
            choosingDeadlineTime = true
        },
    )
}

@Composable
fun LocationFetchSwitch(checkedChanged: (Boolean) -> Unit) {
    var checked by rememberSaveable { mutableStateOf(false) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text("Location")
        Switch(
            checked = checked,
            onCheckedChange = { changed ->
                checked = changed
                checkedChanged(changed)
            },
        )
    }
}

@Composable
fun NoteFormIconTray(
    onAddNotificationClick: () -> Unit,
    onAddDeadlineClick: () -> Unit,
    onCloseClick: () -> Unit,
    onDoneClick: () -> Unit,
) {
    Row {
        Row(
            modifier = Modifier.weight(0.5f),
            horizontalArrangement = Arrangement.Start,
        ) {
            IconButton(onClick = onAddNotificationClick) {
                ResourceIcon(
                    contentDescriptionId = R.string.add_notification_icon_desc,
                    imageId = R.drawable.add_notification_filled,
                )
            }
            IconButton(onClick = onAddDeadlineClick) {
                ResourceIcon(
                    contentDescriptionId = R.string.add_deadline_icon_desc,
                    imageId = R.drawable.add_deadline_filled,
                )
            }
        }
        Row(
            modifier = Modifier.weight(0.5f),
            horizontalArrangement = Arrangement.End,
        ) {
            IconButton(onClick = onCloseClick) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = stringResource(R.string.quick_note_form_close_button_desc),
                )
            }
            IconButton(onClick = onDoneClick) {
                Icon(
                    Icons.Default.Done,
                    contentDescription = stringResource(R.string.note_quick_form_done_button),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LinkNoteFormPreview() {
    LinkNoteFormDialog()
}