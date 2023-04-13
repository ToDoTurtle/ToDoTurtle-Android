package com.eps.todoturtle.profile.ui.details

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.eps.todoturtle.R
import com.eps.todoturtle.profile.ui.shared.CenteredPicture
import com.eps.todoturtle.profile.ui.shared.DialogTitle

@Composable
fun ChangeProfilePictureDialog(
    hasPermissions: () -> Boolean,
    requestPermissions: () -> Unit,
    shouldShowDialog: MutableState<Boolean>,
    profilePicture: MutableState<Bitmap>,
) {
    val tempChosenImage = remember { mutableStateOf(profilePicture.value) }

    Dialog(
        onDismissRequest = {
            shouldShowDialog.value = false
            profilePicture.value = tempChosenImage.value
        },
    ) {
        Card(
            modifier = Modifier
                .width(300.dp)
                .height(300.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CenteredPicture(
                    bitmap = tempChosenImage.value,
                    description = R.string.profile_picture_desc,
                    size = 150,
                    paddingTop = 20,
                )
                DialogTitle(R.string.chosen_image)
                DialogOptions(
                    hasPermissions = hasPermissions,
                    requestPermissions = requestPermissions,
                    tempChosenImage = tempChosenImage
                )
            }
        }
    }
}

@Composable
private fun DialogOptions(
    tempChosenImage: MutableState<Bitmap>,
    hasPermissions: () -> Boolean,
    requestPermissions: () -> Unit,
) {
    val context = LocalContext.current

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            it?.let { tempChosenImage.value = it }
        }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        it?.let {
            val bitmap: Bitmap?
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
                @Suppress("DEPRECATION")
                // It was deprecated in API 29, the alternative was introduced in API 28. The check is already done.
                bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                tempChosenImage.value = bitmap
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                bitmap = ImageDecoder.decodeBitmap(source)
            }
            bitmap?.let { tempChosenImage.value = bitmap }
        }
    }

    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 30.dp)
            .background(color = MaterialTheme.colorScheme.onPrimary),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        DialogTextButton(R.string.camera) {
            if (hasPermissions()) cameraLauncher.launch() else requestPermissions()
        }
        DialogTextButton(R.string.gallery) {
            galleryLauncher.launch("image/*")
        }
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
