package com.eps.todoturtle.ui.profile.picture

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eps.todoturtle.R

@Composable
fun ProfilePicture(
    profilePicture: Int,
) {
    val shouldShowDialog = rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .height(200.dp)
            .padding(top = 30.dp),
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp,
            ),
        ) {
            Image(
                painter = painterResource(id = profilePicture),
                contentDescription = stringResource(id = R.string.profile_picture_desc),
                modifier = Modifier.clickable { shouldShowDialog.value = true },
            )
            if (shouldShowDialog.value) ChangeProfilePictureDialog(shouldShowDialog, profilePicture)
        }
    }
}
