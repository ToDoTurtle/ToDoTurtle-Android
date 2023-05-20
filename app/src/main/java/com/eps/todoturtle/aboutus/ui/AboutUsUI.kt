package com.eps.todoturtle.aboutus.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import com.eps.todoturtle.aboutus.logic.client.APIClient
import com.eps.todoturtle.profile.ui.shared.ProfileUI

@Composable
fun AboutUsUI(context: Context) {
    val client = APIClient(context)
    val text by produceState(initialValue = "") { value = client.getText() }
    val imageUrl by produceState(initialValue = "") { value = client.getImageUrl() }
    ProfileUI {
        AboutUsImage(imageUrl)
        AboutUsText(text)
    }
}
