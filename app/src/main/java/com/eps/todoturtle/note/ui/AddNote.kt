package com.eps.todoturtle.note.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eps.todoturtle.R
import com.eps.todoturtle.ui.theme.noteScreenButton

@Composable
fun AddNoteButton(
    onClick: () -> Unit,
) {
    FloatingActionButton(
        containerColor = MaterialTheme.colorScheme.noteScreenButton,
        modifier = Modifier
            .scale(1.25f)
            .padding(8.dp),
        onClick = onClick,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.add_notes_big),
            modifier = Modifier.scale(0.8f),
            contentDescription = stringResource(id = R.string.note_add_note_button_desc),
        )
    }
}

@Composable
fun FormContainer(
    onCloseClicked: () -> Unit,
    onDoneClicked: () -> Unit,
    noteForm: @Composable () -> Unit,
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .wrapContentHeight()
            .wrapContentWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        ),
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
        ) {
            Row(
                horizontalArrangement = Arrangement.End,
            ) {
                Spacer(modifier = Modifier.weight(0.7f))
                IconButton(modifier = Modifier.weight(0.15f), onClick = onCloseClicked) {
                    Icon(Icons.Default.Close, contentDescription = "Close button")
                }
                IconButton(modifier = Modifier.weight(0.15f), onClick = onDoneClicked) {
                    Icon(Icons.Default.Done, contentDescription = "Done button")
                }
            }
            noteForm()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteForm() {
    var titleText by remember { mutableStateOf("") }
//    var descriptionText by remember { mutableStateOf("") }

    NoteFormTextField(
        text = titleText,
        labelId = R.string.note_form_title_field,
        onValueChange = { newText -> titleText = newText },
    )
//        Spacer(modifier = Modifier.height(8.dp))
//        NoteFormOutlinedTextField(
//            text = descriptionText,
//            labelId = R.string.note_form_description_field,
//            singleLine = false,
//            onValueChange = { newText -> descriptionText = newText }
//        )
    Spacer(modifier = Modifier.height(16.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        IconButton(onClick = { }) {
            Icon(Icons.Default.Favorite, contentDescription = "Favorite")
        }
        IconButton(onClick = { }) {
            Icon(Icons.Default.Share, contentDescription = "Share")
        }
        IconButton(onClick = { }) {
            Icon(Icons.Default.Delete, contentDescription = "Delete")
        }
        IconButton(onClick = { }) {
            Icon(Icons.Default.Favorite, contentDescription = "Favorite")
        }
        IconButton(onClick = { }) {
            Icon(Icons.Default.Share, contentDescription = "Share")
        }
        IconButton(onClick = { }) {
            Icon(Icons.Default.Delete, contentDescription = "Delete")
        }
    }
}

@Composable
fun NoteFormTextField(
    text: String,
    @StringRes labelId: Int,
    onValueChange: (String) -> Unit = {},
) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = { onValueChange(it) },
        label = { Text(stringResource(id = labelId)) },
    )
}

@Composable
fun NoteFormOutlinedTextField(
    text: String,
    @StringRes labelId: Int,
    onValueChange: (String) -> Unit = {},
) {
    val focusManager = LocalFocusManager.current
    var offset by remember { mutableStateOf(0f) }

    OutlinedTextField(
        modifier = Modifier
            .scrollable(
                orientation = Orientation.Horizontal,
                state = rememberScrollableState { delta ->
                    offset += delta
                    delta
                },
            )
            .fillMaxWidth(),
        value = text,
        onValueChange = { onValueChange(it) },
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        maxLines = 5,
        label = { Text(stringResource(id = labelId)) },
    )
}

@Preview(showBackground = true)
@Composable
fun NoteFormPreview() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        FormContainer(
            onCloseClicked = {},
            onDoneClicked = {},
        ) {
            NoteForm()
        }
    }
}
