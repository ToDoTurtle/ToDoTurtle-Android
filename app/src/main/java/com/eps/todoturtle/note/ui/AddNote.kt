package com.eps.todoturtle.note.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.eps.todoturtle.R
import com.eps.todoturtle.ui.theme.noteScreenButton

@Composable
fun AddNoteButton(
    onClick: () -> Unit,
) {
    FloatingActionButton(
        containerColor = MaterialTheme.colorScheme.noteScreenButton,
        modifier = Modifier.padding(0.dp),
        onClick = onClick,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.add_notes_big),
            modifier = Modifier.scale(0.8f),
            contentDescription = stringResource(id = R.string.note_add_note_button_desc),
        )
    }
}
