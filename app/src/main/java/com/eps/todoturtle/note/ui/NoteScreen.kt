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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.eps.todoturtle.R
import com.eps.todoturtle.network.logic.NetworkAvailability
import com.eps.todoturtle.network.ui.NetworkWarningDialog
import com.eps.todoturtle.note.logic.NotesViewModelInt
import com.eps.todoturtle.note.logic.location.LocationClient
import com.eps.todoturtle.permissions.logic.PermissionRequester
import com.eps.todoturtle.permissions.logic.RequestPermissionContext
import kotlinx.coroutines.flow.Flow

@Composable
fun NoteScreen(
    locationClient: LocationClient,
    hasLocationPermission: () -> Boolean,
    locationPermissionRequester: PermissionRequester,
    connectionAvailability: Flow<NetworkAvailability>,
    onGoToSettingsClick: () -> Unit,
    onCloseAppClick: () -> Unit,
    viewModel: NotesViewModelInt,
) {
    var inHistory by rememberSaveable { mutableStateOf(false) }
    var isFormVisible by rememberSaveable { mutableStateOf(false) }
    var shouldShowNetworkDialog by rememberSaveable { mutableStateOf(false) }
    val networkAvailability by connectionAvailability.collectAsStateWithLifecycle(NetworkAvailability.AVAILABLE)
    shouldShowNetworkDialog = networkAvailability != NetworkAvailability.AVAILABLE

    Scaffold(
        floatingActionButton = { AddNoteButton(onClick = { isFormVisible = true }) },
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
                notes = if (inHistory) viewModel.doneNotes else viewModel.toDoNotes,
                onCheckClick = { note ->
                    if (inHistory) viewModel.doNote(note) else viewModel.undoNote(note)
                },
            )
        }
    }

    if (isFormVisible) {
        RequestPermissionContext(locationPermissionRequester) {
            AddNoteFormDialog(
                locationClient = locationClient,
                requestPermisions = { requestPermissions() },
                hasLocationPermission = hasLocationPermission,
                onCloseClick = { isFormVisible = false },
                onDoneClick = { isFormVisible = false },
                onDismissRequest = { isFormVisible = false },
                viewModel = viewModel,
            )
        }
    }

    NetworkWarningDialog(
        showDialog = shouldShowNetworkDialog,
        reason = R.string.app_requires_internet,
        onSettingsClick = onGoToSettingsClick,
        onSecondaryButtonClick = { onCloseAppClick() },
        secondaryButtonText = R.string.close_app,
        onDismiss = {},
    )
}

@Composable
fun NoteScreenHeadline(
    viewModel: NotesViewModelInt,
    inHistory: Boolean,
    onHistoryToggle: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 8.dp, top = 8.dp),
    ) {
        CheckCounter(
            modifier = Modifier.weight(0.1f),
            count = viewModel.doneNotes.size,
            inHistory = inHistory,
        )
        Spacer(modifier = Modifier.weight(0.8f))
        HistoryToggle(
            inHistory = inHistory,
            onHistoryClick = onHistoryToggle,
        )
    }
}
