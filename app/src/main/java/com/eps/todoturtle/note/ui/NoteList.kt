package com.eps.todoturtle.note.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.eps.todoturtle.note.logic.Note

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteList(
    notes: List<Note>,
    inHistory: Boolean,
    onCheckClick: (Note) -> Unit,
) {
    NoteListContainer(inHistory = inHistory) {
        LazyColumn {
            items(
                items = notes,
                key = { it.id },
            ) { note ->
                Box(
                    modifier = Modifier.animateItemPlacement(),
                ) {
                    Note(
                        note = note,
                        inHistory = inHistory,
                        onCheckClick = { onCheckClick(note) },
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NoteListContainer(
    inHistory: Boolean,
    content: @Composable () -> Unit,
) {
    AnimatedContent(
        targetState = inHistory,
        transitionSpec = {
            if (targetState) {
                slideInHorizontally { width -> width } + fadeIn() with
                    slideOutHorizontally { width -> -width } + fadeOut()
            } else {
                slideInHorizontally { width -> -width } + fadeIn() with
                    slideOutHorizontally { width -> width } + fadeOut()
            }
        },
    ) {
        content()
    }
}
