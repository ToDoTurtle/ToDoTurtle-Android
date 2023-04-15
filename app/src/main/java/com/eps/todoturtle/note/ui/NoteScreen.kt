package com.eps.todoturtle.note.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.eps.todoturtle.note.logic.NoteScreenViewModel

@Composable
fun NoteScreen(
    viewModel: NoteScreenViewModel,
) {
    var inHistory by rememberSaveable { mutableStateOf(false) }

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

@Composable
fun NoteScreenHeadline(
    viewModel: NoteScreenViewModel,
    inHistory: Boolean,
    onHistoryToggle: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 8.dp)
    ) {
        CheckCounter(
            modifier = Modifier
                .weight(0.1f),
            count = viewModel.doneNotes.size,
            inHistory = inHistory,
        )
        Spacer(modifier = Modifier.weight(0.8f))
        HistoryToggle(
            modifier = Modifier.weight(0.1f),
            inHistory = inHistory,
            onHistoryClick = onHistoryToggle,
        )
    }
}