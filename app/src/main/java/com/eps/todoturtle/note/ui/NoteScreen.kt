package com.eps.todoturtle.note.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.eps.todoturtle.note.logic.NoteScreenViewModel

@Composable
fun NoteScreen(
    viewModel: NoteScreenViewModel,
) {
    var inHistory by rememberSaveable { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.End,
    ) {
        Row(

        ) {
            CheckCounter(
                count = viewModel.doneNotes.size,
                inHistory = inHistory,
            )
            HistoryToggle(
                inHistory = inHistory,
                onHistoryClick = { inHistory = !inHistory },
            )
        }
        NoteList(
            inHistory = inHistory,
            notes = if (inHistory) viewModel.doneNotes else viewModel.toDoNotes,
            onCheckClick = { note ->
                if (inHistory) viewModel.doNote(note) else viewModel.undoNote(note)
            },
        )
    }
}
