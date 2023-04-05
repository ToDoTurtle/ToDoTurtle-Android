package com.eps.todoturtle.ui.profile.picture

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.eps.todoturtle.R

@Composable
fun ChangeProfilePictureDialog(
    shouldShowDialog: MutableState<Boolean>,
    profilePicture: Int,
) {
    Dialog(onDismissRequest = { shouldShowDialog.value = false }) {
        Card(
            modifier = Modifier
                .width(300.dp)
                .height(300.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Picture(profilePicture)
                DialogTitle()
                DialogOptions()
            }
        }
    }
}

@Composable
private fun DialogTitle() {
    Text(
        text = stringResource(id = R.string.chosen_image),
        fontWeight = FontWeight.ExtraBold,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        style = MaterialTheme.typography.headlineMedium,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
private fun DialogOptions() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 30.dp)
            .background(color = MaterialTheme.colorScheme.onPrimary),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        DialogTextButton(R.string.camera) {}
        DialogTextButton(R.string.gallery) { }
    }
}

@Composable
private fun DialogTextButton(
    textId: Int,
    onClick: () -> Unit,
) {
    TextButton(onClick = onClick) {
        Text(
            stringResource(id = textId),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                top = 5.dp,
                bottom = 5.dp,
            ),
        )
    }
}