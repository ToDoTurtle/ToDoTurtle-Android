package com.eps.todoturtle.note.ui

import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eps.todoturtle.R
import com.eps.todoturtle.shared.ui.ResourceIcon
import com.eps.todoturtle.ui.theme.formContainer
import com.eps.todoturtle.ui.theme.noteScreenButton
import com.eps.todoturtle.ui.theme.onFormContainer

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AddNoteMenu(
    onAddClick: () -> Unit,
    onDoneClick: () -> Unit,
    onCancelClick: () -> Unit,
) {
    var isFormVisible by rememberSaveable { mutableStateOf(false) }
    var noteText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .offset(y = 10.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessLow,
                ),
            ),
    ) {
        if (isFormVisible) {
            FormContainer(
                modifier = Modifier
                    .padding(20.dp)
                    .offset(x = 20.dp),
                onCloseClick = {
                    isFormVisible = false
                    onCancelClick()
                },
                onDoneClick = {
                    isFormVisible = false
                    onDoneClick()
                },
            )
        } else {
            AddNoteButton(
                modifier = Modifier.offset(y = -10.dp).scale(0.8f),
                onClick = {
                    isFormVisible = true
                    onAddClick()
                },
            )
        }
    }
}

@Composable
fun AddNoteButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        containerColor = MaterialTheme.colorScheme.noteScreenButton,
        modifier = modifier
            .scale(1.25f)
            .padding(8.dp),
        onClick = onClick,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.add_notes_big),
            contentDescription = stringResource(id = R.string.note_add_note_button_desc),
        )
    }
}

@Composable
fun FormContainer(
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit,
    onDoneClick: () -> Unit,
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .wrapContentHeight()
            .wrapContentWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.formContainer,
            contentColor = MaterialTheme.colorScheme.onFormContainer,
        ),
        elevation = CardDefaults.elevatedCardElevation(),
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .fillMaxWidth(),
        ) {
            QuickNoteForm(
                onCloseClicked = onCloseClick,
                onDoneClicked = onDoneClick,
            )
        }
    }
}

@Composable
fun QuickNoteForm(
    onCloseClicked: () -> Unit,
    onDoneClicked: () -> Unit,
) {
    var titleText by rememberSaveable { mutableStateOf("") }
    Row {
        Row(
            modifier = Modifier.weight(0.5f),
            horizontalArrangement = Arrangement.Start,
        ) {
            IconButton(onClick = { }) {
                ResourceIcon(
                    contentDescriptionId = R.string.add_notification_icon_desc,
                    imageId = R.drawable.add_notification_filled,
                )
            }
            IconButton(onClick = { }) {
                ResourceIcon(
                    contentDescriptionId = R.string.add_deadline_icon_desc,
                    imageId = R.drawable.add_deadline_filled,
                )
            }
            IconButton(onClick = { }) {
                Icon(
                    Icons.Default.AddCircle,
                    contentDescription = stringResource(id = R.string.more_icon_desc),
                )
            }
        }
        Row(
            modifier = Modifier.weight(0.5f),
            horizontalArrangement = Arrangement.End,
        ) {
            IconButton(onClick = onCloseClicked) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = stringResource(R.string.quick_note_form_close_button_desc),
                )
            }
            IconButton(onClick = onDoneClicked) {
                Icon(
                    Icons.Default.Done,
                    contentDescription = stringResource(R.string.note_quick_form_done_button),
                )
            }
        }
    }
    NoteFormTextField(
        modifier = Modifier.padding(bottom = 8.dp),
        text = titleText,
        labelId = R.string.note_form_title_field,
        onValueChange = { newText -> titleText = newText },
        trailingIcon = { ClearTextIcon(onClick = { if (titleText.isNotEmpty()) titleText = "" }) },
    )
}

@Composable
fun NoteFormTextField(
    modifier: Modifier = Modifier,
    text: String,
    @StringRes labelId: Int,
    singleLine: Boolean = true,
    onValueChange: (String) -> Unit = {},
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    TextField(
        modifier = modifier.fillMaxWidth(),
        value = text,
        singleLine = singleLine,
        onValueChange = { onValueChange(it) },
        label = { Text(stringResource(id = labelId)) },
        trailingIcon = { if (text.isNotEmpty()) trailingIcon?.invoke() },
    )
}

@Composable
fun ClearTextIcon(
    onClick: () -> Unit,
) {
    IconButton(onClick = onClick) {
        ResourceIcon(
            contentDescriptionId = R.string.clear_text_content_desc,
            imageId = R.drawable.clear,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NoteFormPreview() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        FormContainer(onCloseClick = {}, onDoneClick = {})
    }
}
