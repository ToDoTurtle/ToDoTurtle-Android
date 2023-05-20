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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.eps.todoturtle.R
import com.eps.todoturtle.action.logic.ActionViewModel
import com.eps.todoturtle.action.logic.NoteAction
import com.eps.todoturtle.action.logic.NoteActionBuilderError
import com.eps.todoturtle.note.ui.AddDeadlineDialog
import com.eps.todoturtle.note.ui.AddNotificationDialog
import com.eps.todoturtle.note.ui.NoteFormDescriptionTextField
import com.eps.todoturtle.note.ui.NoteFormTitle
import com.eps.todoturtle.note.ui.NoteFormTitleTextField
import com.eps.todoturtle.shared.logic.forms.Timestamp
import com.eps.todoturtle.shared.ui.ResourceIcon

@Composable
fun LinkNoteFormDialog(
    actionViewModel: ActionViewModel,
    onDismissRequest: () -> Unit = {},
    onDoneClick: () -> Unit = {},
    onCloseClick: () -> Unit = {},
    onSavedAction: (NoteAction) -> Unit = {},
    @StringRes titleTextId: Int = R.string.link_note,
) {
    var title by rememberSaveable { mutableStateOf(actionViewModel.builder.title) }
    var description by rememberSaveable { mutableStateOf(actionViewModel.builder.description) }
    val errors by actionViewModel.actionBuildErrors.collectAsStateWithLifecycle()
    var saveLocation by rememberSaveable { mutableStateOf(actionViewModel.builder.getLocation) }
    LinkNoteFormDialog(
        titleTextId = titleTextId,
        title = title,
        description = description,
        errors = errors,
        onTitleChange = {
            title = it
            actionViewModel.builder.title = it
        },
        onDescriptionChange = {
            description = it
            actionViewModel.builder.description = it
        },
        onDismissRequest = onDismissRequest,
        onDoneClick = onDoneClick,
        onCloseClick = onCloseClick,
        onLocationSettingChange = {
            saveLocation = it
            actionViewModel.builder.getLocation = it
        },
        getLocation = saveLocation,
        onAddDeadlineClick = {
            actionViewModel.builder.deadline = it
        },
        onAddNotificationClick = {
            actionViewModel.builder.notification = it
        },
    )
    val action by actionViewModel.actionCreated.collectAsStateWithLifecycle(initialValue = null)
    action?.let { onSavedAction(it) }
}

@Composable
fun LinkNoteFormDialog(
    title: String,
    description: String,
    errors: List<NoteActionBuilderError>,
    @StringRes titleTextId: Int = R.string.add_note_form_title,
    onTitleChange: (String) -> Unit = {},
    onDescriptionChange: (String) -> Unit = {},
    onDismissRequest: () -> Unit = {},
    onDoneClick: () -> Unit = {},
    onCloseClick: () -> Unit = {},
    onLocationSettingChange: (Boolean) -> Unit = {},
    getLocation: Boolean = false,
    onAddDeadlineClick: (Timestamp) -> Unit = {},
    onAddNotificationClick: (Timestamp) -> Unit = {},
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Card {
            LinkNoteForm(
                title = title,
                titleTextId = titleTextId,
                description = description,
                errors = errors,
                onTitleChange = onTitleChange,
                onDescriptionChange = onDescriptionChange,
                onDoneClick = onDoneClick,
                onCloseClick = onCloseClick,
                onLocationSettingChange = onLocationSettingChange,
                getLocation = getLocation,
                onAddDeadlineClick = onAddDeadlineClick,
                onAddNotificationClick = onAddNotificationClick,
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LinkNoteForm(
    modifier: Modifier = Modifier,
    title: String,
    @StringRes titleTextId: Int,
    errors: List<NoteActionBuilderError>,
    description: String,
    onTitleChange: (String) -> Unit = {},
    onDescriptionChange: (String) -> Unit = {},
    onDoneClick: () -> Unit = {},
    onCloseClick: () -> Unit = {},
    onLocationSettingChange: (Boolean) -> Unit = {},
    getLocation: Boolean = false,
    onAddDeadlineClick: (Timestamp) -> Unit = {},
    onAddNotificationClick: (Timestamp) -> Unit = {},
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
        NoteFormTitle(titleTextId = titleTextId)
        Spacer(Modifier.height(8.dp))
        NoteFormTitleTextField(
            isTitleEmpty = errors.contains(NoteActionBuilderError.EMPTY_TITLE),
            isTitleTooLong = errors.contains(NoteActionBuilderError.TITLE_TOO_LONG),
            value = title,
            onValueChange = onTitleChange,
            focusRequester = titleFocusRequester,
        )
        NoteFormDescriptionTextField(
            value = description,
            isDescriptionTooLong = errors.contains(NoteActionBuilderError.DESCRIPTION_TOO_LONG),
            onValueChange = onDescriptionChange,
            focusRequester = descriptionFocusRequester,
        )
        LocationFetchSwitch(checkedChanged = onLocationSettingChange, checked = getLocation)
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
            onAddNotificationClick(chosenTime)
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
            onAddDeadlineClick(chosenTime)
        },
        onNextClick = {
            choosingDeadlineTime = true
        },
    )
}

@Composable
fun LocationFetchSwitch(checked: Boolean = false, checkedChanged: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(stringResource(R.string.location_edit_link_action))
        Switch(
            checked = checked,
            onCheckedChange = { changed ->
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
    LinkNoteFormDialog(title = "Test", description = "This is a test desc", errors = emptyList())
}
