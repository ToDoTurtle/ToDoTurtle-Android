package com.eps.todoturtle.profile.ui.login

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
import com.eps.todoturtle.mock.MockValues
import com.eps.todoturtle.profile.ui.shared.CenteredPicture
import com.eps.todoturtle.profile.ui.shared.OutlinedText
import com.eps.todoturtle.profile.ui.shared.ProfileUI
import com.eps.todoturtle.shared.logic.extensions.bitmapFrom

@Composable
fun LoginUI(
    modifier: Modifier = Modifier,
    onSignInClick: () -> Unit,
) {
    ProfileUI(modifier = modifier) {
        LoginContent(onSignInClick)
    }
}

@Composable
fun LoginContent(
    onSignInClick: () -> Unit,
    login: (() -> Boolean)? = null,
) {
    val wrongLogin = rememberSaveable { mutableStateOf(false) }
    val username = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val loginMethod = login ?: {
        username.value == MockValues.USERNAME.getValue() && password.value == MockValues.PASSWORD.getValue()
    }

    CenteredPicture(
        bitmap = bitmapFrom(R.drawable.cool_turtle, LocalContext.current),
        description = R.string.login_picture_desc,
        paddingTop = 8,
        size = 260,
    )
    OutlinedText(
        text = username,
        label = R.string.login_username,
        topPadding = 8,
        error = wrongLogin,
    )
    OutlinedText(
        text = password,
        label = R.string.login_password,
        topPadding = 8,
        isPassword = true,
        error = wrongLogin,
    )
    Button(
        onClick = {
            if (loginMethod()) {
                wrongLogin.value = false
                onSignInClick()
            } else {
                wrongLogin.value = true
            }
        },
        modifier = Modifier.padding(top = 8.dp),
    ) {
        Text(text = stringResource(id = R.string.sign_in))
    }
}

@Preview
@Composable
fun LoginUIPreview() {
    LoginUI(onSignInClick = { })
}
