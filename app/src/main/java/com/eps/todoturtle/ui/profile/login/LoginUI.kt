package com.eps.todoturtle.ui.profile.login

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.eps.todoturtle.R
import com.eps.todoturtle.extensions.bitmapFrom
import com.eps.todoturtle.mock.MockValues
import com.eps.todoturtle.ui.profile.shared.CenteredPicture
import com.eps.todoturtle.ui.profile.shared.OutlinedText
import com.eps.todoturtle.ui.profile.shared.ProfileUI

@Composable
fun LoginUI(
    navController: NavController,
) {
    ProfileUI {
        LoginContent(navController)
    }
}

@Composable
fun LoginContent(
    navController: NavController,
    login: (() -> Boolean)? = null
) {
    val shouldShowWrongLogin = rememberSaveable { mutableStateOf(false)}
    val username = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val loginMethod = login ?: {
        username.value == MockValues.USERNAME.getValue() && password.value == MockValues.PASSWORD.getValue()
    }

    CenteredPicture(
        bitmap = bitmapFrom(R.drawable.cool_turtle, LocalContext.current),
        description = R.string.login_picture_desc,
        paddingTop = 12,
        size = 260)
    OutlinedText(text = username, label = R.string.login_username, topPadding = 12)
    OutlinedText(text = password, label = R.string.login_password, topPadding = 12, isPassword = true)
    Button(onClick = {
        if (loginMethod()) {
            // TODO: Redirect to home page, not profile details
            navController.navigate("profile") {
                launchSingleTop = true
            }
        } else {
            shouldShowWrongLogin.value = true
        }
    },
        modifier = Modifier.padding(top = 12.dp)) {
        Text(text = stringResource(id = R.string.sign_in))
    }
    if (shouldShowWrongLogin.value) WrongLoginDialog(shouldShowWrongLogin)
}

@Preview
@Composable
fun LoginUIPreview() {
    LoginUI(rememberNavController())
}