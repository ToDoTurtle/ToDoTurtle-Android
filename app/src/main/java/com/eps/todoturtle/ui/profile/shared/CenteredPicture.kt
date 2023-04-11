package com.eps.todoturtle.ui.profile.shared

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun CenteredPicture(
    bitmap: Bitmap,
    description: Int,
    size: Int,
    paddingTop: Int = 0,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(fraction = 0.5f)
            .padding(top = paddingTop.dp)
            .height(size.dp),
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp,
            ),
        ) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = stringResource(id = description),
                modifier = Modifier.size(400.dp),
                contentScale = ContentScale.Crop,
            )
        }
    }
}
