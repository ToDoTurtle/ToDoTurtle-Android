package com.eps.todoturtle.profile.ui.login

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eps.todoturtle.R
import com.eps.todoturtle.profile.ui.shared.CenteredPicture
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
        // TODO: Change mock true to commented code
//        username.value == MockValues.USERNAME.getValue() && password.value == MockValues.PASSWORD.getValue()
        true
    }

    Spacer(modifier = Modifier.size(20.dp))
    CenteredPicture(
        bitmap = bitmapFrom(R.drawable.turtle, LocalContext.current),
        description = R.string.profile_login_picture_desc,
        modifier = Modifier
            .size(150.dp),
        innerImagePadding = 15.dp,
    )
    WelcomeMessage()
    LoginOutlinedText(
        text = username,
        label = R.string.profile_login_username,
        error = wrongLogin,
    )
    LoginOutlinedPassword(
        text = password,
        label = R.string.profile_login_password,
        error = wrongLogin,
    )
    Button(
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 5.dp,
            pressedElevation = 15.dp,
            disabledElevation = 0.dp,
        ),
        onClick = {
            if (loginMethod()) {
                wrongLogin.value = false
                onSignInClick()
            } else {
                wrongLogin.value = true
            }
        },
    ) {
        Text(text = stringResource(id = R.string.profile_sign_in))
    }
    Spacer(modifier = Modifier.size(5.dp))
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun WelcomeMessage() {
    Text(
        style = MaterialTheme.typography.headlineSmall.copy(
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.SemiBold,
        ),
        text = buildAnnotatedString {
            append(stringResource(id = R.string.welcome_message_part1))
            withStyle(
                MaterialTheme.typography.headlineLarge.copy(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                ).toSpanStyle(),
            ) {
                append("\n" + stringResource(id = R.string.spaced_app_name))
            }
        },
    )
}

@Preview
@Composable
fun LoginUIPreview() {
    LoginUI(onSignInClick = { })
}
