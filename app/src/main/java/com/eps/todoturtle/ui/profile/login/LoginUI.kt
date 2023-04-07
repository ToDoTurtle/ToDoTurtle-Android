package com.eps.todoturtle.ui.profile.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.eps.todoturtle.R
import com.eps.todoturtle.extensions.bitmapFrom
import com.eps.todoturtle.ui.profile.shared.CenteredPicture
import com.eps.todoturtle.ui.profile.shared.ProfileUI

@Composable
fun LoginUI() {
    ProfileUI {
        LoginContent()
    }
}

@Composable
fun LoginContent() {
    CenteredPicture(
        bitmap = bitmapFrom(R.drawable.cool_turtle, LocalContext.current),
        description = R.string.login_picture_desc,
        paddingTop = 15,
        size = 260)
}

@Preview
@Composable
fun LoginUIPreview() {
    LoginUI()
}