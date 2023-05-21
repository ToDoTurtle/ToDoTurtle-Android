package com.eps.todoturtle.note.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.eps.todoturtle.R
import com.eps.todoturtle.note.logic.NotesViewModel
import com.eps.todoturtle.note.logic.location.LocationClient
import com.eps.todoturtle.permissions.logic.PermissionRequester
import com.eps.todoturtle.permissions.logic.RequestPermissionContext

@Composable
fun NoteScreen(
    locationClient: LocationClient,
    hasLocationPermission: () -> Boolean,
    locationPermissionRequester: PermissionRequester,
    viewModel: NotesViewModel,
) {
    var inHistory by rememberSaveable { mutableStateOf(false) }
    var isAddNoteFormVisible by rememberSaveable { mutableStateOf(false) }
    var isEditFormVisible by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            NotesFloatingButton(
                inHistory = inHistory,
                onClick = {
                    if (inHistory) {
                        viewModel.clearHistory()
                    } else isAddNoteFormVisible = true
                },
            )
        },
    ) {
        Column(
            horizontalAlignment = Alignment.End,
        ) {
            NoteScreenHeadline(
                viewModel = viewModel,
                inHistory = inHistory,
                onHistoryToggle = { inHistory = !inHistory },
            )
            NoteList(
                inHistory = inHistory,
                notes = if (inHistory) viewModel.getDoneNotes() else viewModel.getToDoNotes(),
                onEditClick = { note ->
                    viewModel.load(note)
                    isEditFormVisible = true
                },
                onDeleteClick = { note ->
                    if (inHistory) {
                        viewModel.deleteDone(note)
                    } else viewModel.deleteToDo(note)
                },
                onCheckClick = { note ->
                    if (inHistory) viewModel.undoNote(note) else viewModel.doNote(note)
                },
            )
        }

        if (isAddNoteFormVisible) {
            RequestPermissionContext(locationPermissionRequester) {
                AddNoteFormDialog(
                    locationClient = locationClient,
                    requestPermisions = { requestPermissions() },
                    hasLocationPermission = hasLocationPermission,
                    onCloseClick = { isAddNoteFormVisible = false },
                    onDoneClick = {
                        viewModel.addToDo()
                        if (viewModel.noteErrors.value.isEmpty()) isAddNoteFormVisible = false
                    },
                    onDismissRequest = { isAddNoteFormVisible = false },
                    viewModel = viewModel,
                )
            }
        }

        if (isEditFormVisible) {
            RequestPermissionContext(locationPermissionRequester) {
                AddNoteFormDialog(
                    locationClient = locationClient,
                    requestPermisions = { requestPermissions() },
                    hasLocationPermission = hasLocationPermission,
                    onCloseClick = { isEditFormVisible = false; viewModel.clearNoteFields() },
                    onDoneClick = {
                        if (inHistory) viewModel.updateDone() else viewModel.updateToDo()
                        if (viewModel.noteErrors.value.isEmpty()) isEditFormVisible = false
                    },
                    onDismissRequest = {
                        isEditFormVisible = false; viewModel.clearNoteFields()
                    },
                    titleTextId = R.string.edit_note,
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
fun NoteScreenHeadline(
    viewModel: NotesViewModel,
    inHistory: Boolean,
    onHistoryToggle: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 8.dp, top = 8.dp),
    ) {
        CheckCounter(
            modifier = Modifier.weight(0.1f),
            count = viewModel.getDoneNotes().size,
            inHistory = inHistory,
        )
        Spacer(modifier = Modifier.weight(0.8f))
        HistoryToggle(
            inHistory = inHistory,
            onHistoryClick = onHistoryToggle,
        )
    }
}
