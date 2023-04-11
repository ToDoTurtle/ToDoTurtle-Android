@file:OptIn(ExperimentalMaterial3Api::class)

package com.eps.todoturtle.ui.profile.login

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.eps.todoturtle.R
import com.eps.todoturtle.extensions.bitmapFrom
import com.eps.todoturtle.ui.profile.shared.CenteredPicture
import com.eps.todoturtle.ui.profile.shared.DialogTitle

@Composable
fun WrongLoginDialog(
    shouldShowDialog: MutableState<Boolean>,
) {
    val context = LocalContext.current

    Dialog(onDismissRequest = { shouldShowDialog.value = false }) {
        Card(
            modifier = Modifier
                .width(300.dp)
                .height(300.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CenteredPicture(
                    bitmap = bitmapFrom(R.drawable.wrong_login, context),
                    description = R.string.wrong_login_desc,
                    size = 150,
                    paddingTop = 20
                )
                DialogTitle(textId = R.string.wrong_login)
                DismissDialog(shouldShowDialog)
            }
        }
    }
}

@Composable
private fun DismissDialog(shouldShowDialog: MutableState<Boolean>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp)
            .background(color = MaterialTheme.colorScheme.onPrimary),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        TextButton(onClick = { shouldShowDialog.value = false }) {
            Text(
                stringResource(id = R.string.dismiss),
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
            )
        }
    }
}