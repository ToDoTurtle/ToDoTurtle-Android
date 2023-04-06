package com.eps.todoturtle.ui.profile.picture

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eps.todoturtle.R

@Composable
fun DialogPicture(
    bitmap: Bitmap,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .padding(top = 20.dp)
            .height(150.dp),
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp,
            ),
        ) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                modifier = Modifier.size(400.dp),
                contentDescription = stringResource(id = R.string.profile_picture_desc),
            )
        }
    }
}