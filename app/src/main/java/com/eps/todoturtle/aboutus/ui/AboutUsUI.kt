package com.eps.todoturtle.aboutus.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.tooling.preview.Preview
import com.eps.todoturtle.aboutus.logic.client.APIClient
import com.eps.todoturtle.profile.ui.shared.ProfileUI

@Composable
fun AboutUsUI() {
    val client = APIClient()
    val text by produceState(initialValue = "") { value = client.getText() }
    val imageUrl by produceState(initialValue = "") { value = client.getImageUrl() }
    ProfileUI {
        AboutUsImage(imageUrl)
        AboutUsText(text)
    }
}

@Preview
@Composable
fun PreferenceUIPreview() {
    AboutUsUI()
}
