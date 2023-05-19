package com.eps.todoturtle.note.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.eps.todoturtle.R
import com.eps.todoturtle.note.logic.NoteBuildError
import com.eps.todoturtle.note.logic.NotesViewModel
import com.eps.todoturtle.note.logic.location.LocationClient
import com.eps.todoturtle.note.logic.location.toGeoPoint
import com.eps.todoturtle.shared.logic.forms.Timestamp
import com.eps.todoturtle.shared.ui.ClearTextIcon
import com.eps.todoturtle.shared.ui.FormOutlinedTextField
import com.eps.todoturtle.shared.ui.ResourceIcon
import com.eps.todoturtle.ui.theme.noteScreenButton
import com.eps.todoturtle.ui.theme.onFormContainer
import com.google.android.gms.tasks.Tasks
import org.osmdroid.util.GeoPoint


@Composable
fun AddNoteButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        containerColor = MaterialTheme.colorScheme.noteScreenButton,
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.note_add_note_button_desc),
        )
    }
}

@Composable
fun AddNoteFormDialog(
    locationClient: LocationClient,
    requestPermisions: () -> Unit,
    hasLocationPermission: () -> Boolean,
    onDismissRequest: () -> Unit = {},
    onDoneClick: () -> Unit = {},
    onCloseClick: () -> Unit = {},
    viewModel: NotesViewModel,
    @StringRes titleTextId: Int = R.string.add_note_form_title,
) {
    Dialog(
        onDismissRequest = { onDismissRequest(); viewModel.clearNoteFields() },
    ) {
        Card {
            AddNoteForm(
                locationClient = locationClient,
                hasPermisions = hasLocationPermission,
                requestPermisions = requestPermisions,
                onCloseClick = onCloseClick,
                onDoneClick = onDoneClick,
                viewModel = viewModel,
                titleTextId = titleTextId,
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddNoteFormBody(
    modifier: Modifier = Modifier,
    errors: List<NoteBuildError>,
    @StringRes titleTextId: Int,
    titleValue: String,
    onTitleValueChange: (String) -> Unit,
    descriptionValue: String,
    onDescriptionValueChange: (String) -> Unit,
    onAddNotificationClick: () -> Unit,
    onAddDeadlineClick: () -> Unit,
    onAddLocationClick: () -> Unit,
    onCloseClick: () -> Unit,
    onDoneClick: () -> Unit,
) {
    val (titleFocusRequester, descriptionFocusRequester) = remember { FocusRequester.createRefs() }

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        NoteFormTitle(titleTextId = titleTextId)
        Spacer(Modifier.height(8.dp))
        NoteFormTitleTextField(
            isTitleTooLong = errors.contains(NoteBuildError.TITLE_TOO_LONG),
            isTitleEmpty = errors.contains(NoteBuildError.TITLE_EMPTY),
            value = titleValue,
            onValueChange = { onTitleValueChange(it) },
            focusRequester = titleFocusRequester,
        )
        NoteFormDescriptionTextField(
            isDescriptionTooLong = errors.contains(NoteBuildError.DESCRIPTION_TOO_LONG),
            value = descriptionValue,
            onValueChange = { onDescriptionValueChange(it) },
            focusRequester = descriptionFocusRequester,
        )
        NoteFormIconTray(
            onAddNotificationClick = onAddNotificationClick,
            onAddDeadlineClick = onAddDeadlineClick,
            onAddLocationClick = onAddLocationClick,
            onCloseClick = onCloseClick,
            onDoneClick = onDoneClick,
        )
    }
}

@Composable
fun AddNoteForm(
    modifier: Modifier = Modifier,
    locationClient: LocationClient,
    hasPermisions: () -> Boolean,
    requestPermisions: () -> Unit,
    onCloseClick: () -> Unit,
    onDoneClick: () -> Unit,
    viewModel: NotesViewModel,
    @StringRes titleTextId: Int = R.string.add_note_form_title,
) {
    var choosingLocation by remember { mutableStateOf(false) }
    var choosingNotification by remember { mutableStateOf(false) }
    var choosingNotificationTime by remember { mutableStateOf(false) }
    var choosingDeadline by remember { mutableStateOf(false) }
    var choosingDeadlineTime by remember { mutableStateOf(false) }
    val errors by viewModel.noteErrors.collectAsStateWithLifecycle()

    AddNoteFormBody(
        titleTextId = titleTextId,
        errors = errors,
        titleValue = viewModel.noteTitle.value,
        onTitleValueChange = { viewModel.noteTitle.value = it },
        descriptionValue = viewModel.noteDescription.value,
        onDescriptionValueChange = { viewModel.noteDescription.value = it },
        onAddNotificationClick = { choosingNotification = true },
        onAddDeadlineClick = { choosingDeadline = true },
        onAddLocationClick = {
            if (hasPermisions()) choosingLocation = true else requestPermisions()
        },
        onCloseClick = { onCloseClick(); viewModel.clearNoteFields() },
        onDoneClick = {
            viewModel.addToDo()
            if (viewModel.noteErrors.value.isEmpty()) onDoneClick()
        },
    )

    AddNotificationDialog(
        choosingDate = choosingNotification,
        choosingTime = choosingNotificationTime,
        onDismissRequest = {
            choosingNotification = false
            choosingNotificationTime = false
        },
        onBackClick = { choosingNotificationTime = false },
        onAddNotificationClick = { chosenTime ->
            choosingNotification = false
            choosingNotificationTime = false
            viewModel.noteNotificationTime = chosenTime
        },
        onNextClick = { choosingNotificationTime = true },
    )

    AddDeadlineDialog(
        choosingDate = choosingDeadline,
        choosingTime = choosingDeadlineTime,
        onDismissRequest = {
            choosingDeadline = false
            choosingDeadlineTime = false
        },
        onBackClick = { choosingDeadlineTime = false },
        onAddDeadlineClick = { chosenTime ->
            choosingDeadline = false
            choosingDeadlineTime = false
            viewModel.noteDeadlineTime = chosenTime
        },
        onNextClick = { choosingDeadlineTime = true },
    )

    AddLocationDialog(
        choosingLocation = choosingLocation,
        locationClient = locationClient,
        onDismissRequest = {
            choosingLocation = false
        },
        onCancelClick = {
            choosingLocation = false
        },
        onAddLocationClick = { location ->
            choosingLocation = false
            viewModel.noteLocation = location
        },
    )
}

@Composable
fun AddLocationDialog(
    choosingLocation: Boolean,
    locationClient: LocationClient,
    onDismissRequest: () -> Unit = {},
    onCancelClick: () -> Unit = {},
    onAddLocationClick: (GeoPoint) -> Unit = {},
) {
    if (!choosingLocation) return
    var location by remember { mutableStateOf<GeoPoint?>(null) }
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            locationClient.getCurrentLocation().onSuccessTask { newLocation ->
                location = newLocation.toGeoPoint()
                Tasks.forResult(newLocation)
            }
            LocationPicker(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                source = location,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.End,
            ) {
                TextButton(onClick = onCancelClick) {
                    Text(text = stringResource(id = R.string.cancel))
                }
                TextButton(
                    onClick = { onAddLocationClick(location!!) },
                    enabled = location != null,
                ) {
                    Text(text = stringResource(id = R.string.confirm))
                }
            }
        }
    }
}

@Composable
fun AddNotificationDialog(
    modifier: Modifier = Modifier,
    choosingDate: Boolean,
    choosingTime: Boolean,
    onDismissRequest: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onAddNotificationClick: (Timestamp) -> Unit = {},
    onNextClick: () -> Unit = {},
) {
    DateTimePickerDialog(
        modifier = modifier,
        choosingDate = choosingDate,
        choosingTime = choosingTime,
        onDismissRequest = onDismissRequest,
        onBackClick = onBackClick,
        onConfirmClick = onAddNotificationClick,
        onNextClick = onNextClick,
        confirmationRequestTextId = R.string.add_notification_confirmation,
    )
}

@Composable
fun AddDeadlineDialog(
    modifier: Modifier = Modifier,
    choosingDate: Boolean,
    choosingTime: Boolean,
    onDismissRequest: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onAddDeadlineClick: (Timestamp) -> Unit = {},
    onNextClick: () -> Unit = {},
) {
    DateTimePickerDialog(
        modifier = modifier,
        choosingDate = choosingDate,
        choosingTime = choosingTime,
        onDismissRequest = onDismissRequest,
        onBackClick = onBackClick,
        onConfirmClick = onAddDeadlineClick,
        onNextClick = onNextClick,
        confirmationRequestTextId = R.string.add_deadline_confirmation,
    )
}

@Composable
fun BackToDatePickerButton(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
) {
    Button(
        modifier = modifier
            .padding(start = 8.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        onClick = onBackClick,
    ) {
        ResourceIcon(
            modifier = Modifier.offset(x = (-8).dp),
            contentDescriptionId = R.string.go_back_to_date_picker_icon_desc,
            imageId = R.drawable.left_arrow,
        )
        Text(text = stringResource(R.string.date))
    }
}

@Composable
fun ConfirmDateTimeButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    @StringRes confirmationRequestTextId: Int,
) {
    Button(
        onClick = onClick,
    ) {
        Text(text = stringResource(confirmationRequestTextId))
    }
}

@Composable
fun NextButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Button(
        modifier = modifier,
        onClick = onClick,
    ) {
        Text(text = stringResource(R.string.next))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePickerDialog(
    modifier: Modifier = Modifier,
    choosingDate: Boolean,
    choosingTime: Boolean,
    onDismissRequest: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onConfirmClick: (Timestamp) -> Unit = {},
    onNextClick: () -> Unit = {},
    @StringRes confirmationRequestTextId: Int,
) {
    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState()

    if (!choosingDate) return

    DatePickerDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        confirmButton = {
            if (choosingTime) {
                Row {
                    BackToDatePickerButton(onBackClick = onBackClick)
                    Spacer(modifier = Modifier.weight(1f))
                    ConfirmDateTimeButton(
                        confirmationRequestTextId = confirmationRequestTextId,
                        onClick = {
                            onConfirmClick(Timestamp.fromStates(datePickerState, timePickerState))
                        },
                    )
                }
            } else {
                NextButton(onClick = onNextClick)
            }
        },
    ) {
        if (choosingTime) {
            TimePicker(
                modifier = Modifier.padding(38.dp),
                state = timePickerState,
            )
        } else {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
fun NoteFormTitle(
    modifier: Modifier = Modifier,
    @StringRes titleTextId: Int = R.string.add_note_form_title,
) {
    Text(
        text = stringResource(titleTextId),
        style = MaterialTheme.typography.headlineSmall.copy(
            color = MaterialTheme.colorScheme.onFormContainer,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
        ),
    )
}

@Composable
fun NoteFormTitleTextField(
    modifier: Modifier = Modifier,
    isTitleTooLong: Boolean,
    isTitleEmpty: Boolean,
    value: String,
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester,
) {
    FormOutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        value = value,
        singleLine = true,
        hasError = isTitleTooLong || isTitleEmpty,
        errorMessage = if (isTitleTooLong) stringResource(R.string.note_form_title_too_long_error)
        else stringResource(R.string.note_form_title_empty_error),
        maxLines = 1,
        onValueChange = { onValueChange(it) },
        labelId = R.string.note_form_title_field,
        trailingIcon = {
            if (value.isNotEmpty()) {
                ClearTextIcon(onClick = { onValueChange(""); focusRequester.requestFocus() })
            }
        },
    )
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Composable
fun NoteFormDescriptionTextField(
    modifier: Modifier = Modifier,
    isDescriptionTooLong: Boolean,
    value: String,
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester,
) {
    FormOutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        value = value,
        singleLine = false,
        maxLines = 3,
        onValueChange = { onValueChange(it) },
        hasError = isDescriptionTooLong,
        errorMessage = stringResource(R.string.note_form_description_too_long_error),
        labelId = R.string.note_form_description_field,
        trailingIcon = {
            if (value.isNotEmpty()) {
                ClearTextIcon(onClick = { onValueChange(""); focusRequester.requestFocus() })
            }
        },
    )
}

@Composable
fun NoteFormIconTray(
    onAddNotificationClick: () -> Unit,
    onAddDeadlineClick: () -> Unit,
    onAddLocationClick: () -> Unit,
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
            IconButton(onClick = onAddLocationClick) {
                Icon(
                    Icons.Default.LocationOn,
                    contentDescription = stringResource(id = R.string.more_icon_desc),
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
