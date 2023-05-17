package com.eps.todoturtle.aboutus.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.eps.todoturtle.aboutus.logic.client.APIClient
import com.eps.todoturtle.shared.logic.extensions.RobotoThin

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun AboutUsUI() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val client = APIClient()
            val text by produceState(initialValue = "") { value = client.getText() }
            val imageUrl by produceState(initialValue = "") { value = client.getImageUrl() }
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = text,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontFamily = FontFamily.RobotoThin,
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = imageUrl,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontFamily = FontFamily.RobotoThin,
            )
        }
    }
}

@Preview
@Composable
fun PreferenceUIPreview() {
    AboutUsUI()
}
