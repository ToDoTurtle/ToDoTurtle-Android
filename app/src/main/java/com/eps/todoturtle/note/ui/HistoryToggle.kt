package com.eps.todoturtle.note.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eps.todoturtle.R

@Composable
fun HistoryToggle(
    inHistory: Boolean,
    onHistoryClick: () -> Unit,
) {
    Button(
        onClick = onHistoryClick,
        modifier = Modifier
            .scale(scale = 0.5f)
            .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onTertiary,
        ),
    ) {
        if (inHistory) GoBackButton() else GoHistoryButton()
    }
}

@Composable
fun GoHistoryButton() {
    Icon(
        imageVector = ImageVector.Companion.vectorResource(id = R.drawable.history),
        contentDescription = "History",
        tint = MaterialTheme.colorScheme.onTertiary,
        modifier = Modifier
            .padding(start = 4.dp)
    )
    Icon(
        imageVector = ImageVector.Companion.vectorResource(id = R.drawable.chevron_right),
        contentDescription = "Go history",
        tint = MaterialTheme.colorScheme.onTertiary,
        modifier = Modifier
            .padding(start = 4.dp)
            .scale(scale = 1.3f),
    )
}

@Composable
fun GoBackButton() {
    Icon(
        imageVector = ImageVector.Companion.vectorResource(id = R.drawable.chevron_left),
        contentDescription = "Go back",
        tint = MaterialTheme.colorScheme.onTertiary,
        modifier = Modifier
            .padding(start = 4.dp)
            .scale(scale = 1.3f),
    )
    Icon(
        imageVector = ImageVector.Companion.vectorResource(id = R.drawable.note),
        contentDescription = "Notes",
        tint = MaterialTheme.colorScheme.onTertiary,
        modifier = Modifier
            .padding(start = 4.dp)
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