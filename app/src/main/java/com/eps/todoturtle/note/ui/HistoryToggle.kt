package com.eps.todoturtle.note.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eps.todoturtle.R
import com.eps.todoturtle.ui.theme.onNoteHeadlineContainer

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HistoryToggle(
    modifier: Modifier = Modifier,
    inHistory: Boolean,
    onHistoryClick: () -> Unit,
) {
    NoteScreenHeadLineContainer(
        modifier = Modifier
            .padding(start = 0.dp, end = 24.dp, bottom = 0.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable { onHistoryClick() }
    ) {
        HistoryToggleContainer(
            targetState = inHistory,
        ) {
            Row {
                if (inHistory) GoBackButton() else GoHistoryButton()
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HistoryToggleContainer(
    targetState: Boolean,
    content: @Composable () -> Unit,
) {
    AnimatedContent(
        targetState = targetState,
        transitionSpec = {
            if (targetState) {
                slideInHorizontally { width -> width } + fadeIn() with
                        slideOutHorizontally { width -> -width } + fadeOut()
            } else {
                slideInHorizontally { width -> -width } + fadeIn() with
                        slideOutHorizontally { width -> width } + fadeOut()
            }
        },
    ) { targetInHistory ->
        content()
    }
}

@Composable
fun GoHistoryButton() {
    Icon(
        imageVector = ImageVector.Companion.vectorResource(id = R.drawable.history),
        contentDescription = stringResource(R.string.note_history_icon_desc),
        tint = MaterialTheme.colorScheme.onNoteHeadlineContainer,
        modifier = Modifier
            .padding(start = 8.dp, end = 0.dp, top = 4.dp, bottom = 4.dp)
            .scale(scale = 1.2f)
    )
    Icon(
        imageVector = ImageVector.Companion.vectorResource(id = R.drawable.right_arrow),
        contentDescription = stringResource(R.string.go_history_icon_desc),
        tint = MaterialTheme.colorScheme.onNoteHeadlineContainer,
        modifier = Modifier
            .padding(start = 0.dp, end = 4.dp, top = 4.dp, bottom = 4.dp)
            .scale(scale = 1.2f)
    )
}

@Composable
fun GoBackButton() {
    Icon(
        imageVector = ImageVector.Companion.vectorResource(id = R.drawable.left_arrow),
        contentDescription = stringResource(R.string.go_back_to_notes_icon_desc),
        tint = MaterialTheme.colorScheme.onNoteHeadlineContainer,
        modifier = Modifier
            .padding(start = 4.dp, end = 0.dp, top = 4.dp, bottom = 4.dp)
            .scale(scale = 1.2f)
    )
    Icon(
        imageVector = ImageVector.Companion.vectorResource(id = R.drawable.note),
        contentDescription = stringResource(R.string.notes_icon_desc),
        tint = MaterialTheme.colorScheme.onNoteHeadlineContainer,
        modifier = Modifier
            .padding(start = 0.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
            .scale(scale = 1.2f)
    )
}

@Preview
@Composable
fun HistoryTogglePreview() {
    HistoryToggle(
        inHistory = true,
        onHistoryClick = {},
    )
}