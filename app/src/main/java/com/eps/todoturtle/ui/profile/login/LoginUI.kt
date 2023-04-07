package com.eps.todoturtle.ui.profile.login

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eps.todoturtle.R
import com.eps.todoturtle.extensions.bitmapFrom
import com.eps.todoturtle.mock.MockValues
import com.eps.todoturtle.ui.profile.shared.CenteredPicture
import com.eps.todoturtle.ui.profile.shared.OutlinedText
import com.eps.todoturtle.ui.profile.shared.ProfileUI

@Composable
fun LoginUI() {
    ProfileUI {
        LoginContent()
    }
}

@Composable
fun LoginContent(
    login: (() -> Boolean)? = null
) {
    val context = LocalContext.current
    val username = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val loginMethod = login ?: {
        if (username.value == MockValues.USERNAME.getValue() && password.value == MockValues.PASSWORD.getValue()) {
            // TODO: Redirect to home page
            Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
            true
        } else {
            // TODO: Show wrong dialog
            Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
            false
        }
    }

    CenteredPicture(
        bitmap = bitmapFrom(R.drawable.cool_turtle, LocalContext.current),
        description = R.string.login_picture_desc,
        paddingTop = 12,
        size = 260)
    OutlinedText(text = username, label = R.string.login_username, topPadding = 12)
    OutlinedText(text = password, label = R.string.login_password, topPadding = 12, isPassword = true)
    Button(onClick = { loginMethod() },
        modifier = Modifier.padding(top = 12.dp)) {
        Text(text = stringResource(id = R.string.sign_in))
    }
}

@Preview
@Composable
fun LoginUIPreview() {
    LoginUI()
}