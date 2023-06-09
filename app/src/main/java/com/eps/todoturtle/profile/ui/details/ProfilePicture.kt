package com.eps.todoturtle.profile.ui.details

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eps.todoturtle.R

@Composable
fun ProfilePicture(
    hasPermissions: () -> Boolean,
    requestPermissions: () -> Unit,
    profilePicture: Bitmap,
    onChange: (Bitmap) -> Unit,
) {
    var shouldShowDialog by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxWidth(fraction = 0.5f)
            .height(200.dp)
            .padding(top = 20.dp),
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp,
            ),
        ) {
            Image(
                bitmap = profilePicture.asImageBitmap(),
                contentDescription = stringResource(id = R.string.profile_profile_picture_desc),
                modifier = Modifier
                    .clickable { shouldShowDialog = true }
                    .size(400.dp),
                contentScale = ContentScale.Crop,
            )
            if (shouldShowDialog) {
                ChangeProfilePictureDialog(
                    hasPermissions = hasPermissions,
                    requestPermissions = requestPermissions,
                    profilePicture = profilePicture,
                ) {
                    onChange(it)
                    shouldShowDialog = false
                }
            }
        }
    }
}
