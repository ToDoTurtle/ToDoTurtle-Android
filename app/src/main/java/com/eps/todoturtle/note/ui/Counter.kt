package com.eps.todoturtle.note.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eps.todoturtle.R
import com.eps.todoturtle.ui.theme.onNoteHeadlineContainer

@Composable
fun CheckCounter(
    modifier: Modifier = Modifier,
    count: Int = 0,
    inHistory: Boolean,
) {
    NoteScreenHeadLineContainer(
        modifier = Modifier
            .padding(start = 32.dp, bottom = 0.dp)
            .clip(MaterialTheme.shapes.medium)
    ) {
        Column {
            Row {
                Counter(count = count, inHistory = inHistory)
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = stringResource(id = R.string.note_counter_check_desc),
                    tint = MaterialTheme.colorScheme.onNoteHeadlineContainer,
                    modifier = Modifier
                        .padding(start = 10.dp, end = 6.dp, top = 4.dp, bottom = 4.dp)
                        .scale(scale = 1.2f),
                )
            }
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(2.dp)
                    .background(color = MaterialTheme.colorScheme.outlineVariant)
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Counter(
    count: Int,
    inHistory: Boolean,
) {
    AnimatedContent(
        targetState = count,
        transitionSpec = {
            scaleIn() with
                    slideOutOfContainer(
                        towards = if (inHistory) {
                            AnimatedContentScope.SlideDirection.Down
                        } else {
                            AnimatedContentScope.SlideDirection.Up
                        },
                        animationSpec = tween(durationMillis = 200),
                    )
        },
    ) {
        Text(
            text = stringResource(id = R.string.note_counter_count, count),
            color = MaterialTheme.colorScheme.onNoteHeadlineContainer,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(start = 8.dp),
        )
    }
}
