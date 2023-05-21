package com.eps.todoturtle.profile.ui.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.eps.todoturtle.R
import com.eps.todoturtle.network.logic.NetworkAvailability
import com.eps.todoturtle.network.ui.NetworkWarningDialog
import com.eps.todoturtle.profile.logic.UserAuth
import com.eps.todoturtle.profile.ui.register.RegisterDialog
import com.eps.todoturtle.profile.ui.shared.CenteredPicture
import com.eps.todoturtle.profile.ui.shared.PasswordTextField
import com.eps.todoturtle.profile.ui.shared.ProfileUI
import com.eps.todoturtle.profile.ui.shared.UsernameTextField
import com.eps.todoturtle.shared.logic.extensions.bitmapFrom
import com.eps.todoturtle.ui.theme.inactiveOnSecondaryContainer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun LoginUI(
    modifier: Modifier = Modifier,
    userAuth: UserAuth,
    connectionAvailability: Flow<NetworkAvailability>,
    onGoToSettingsClick: () -> Unit,
    onSignInClick: () -> Unit,
) {
    ProfileUI(modifier = modifier) {
        LoginContent(userAuth, connectionAvailability, onGoToSettingsClick) { onSignInClick() }
    }
}

@Composable
fun LoginContent(
    userAuth: UserAuth,
    connectionAvailability: Flow<NetworkAvailability>,
    onGoToSettingsClick: () -> Unit,
    onSignInClick: () -> Unit,
) {
    var wrongLogin by rememberSaveable { mutableStateOf(false) }
    var errorMessage by rememberSaveable { mutableStateOf("") }
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var shouldShowSignUp by rememberSaveable { mutableStateOf(false) }
    var shouldShowNetworkDialog by rememberSaveable { mutableStateOf(false) }
    val networkAvailability by connectionAvailability.collectAsStateWithLifecycle(
        NetworkAvailability.AVAILABLE
    )

    Spacer(modifier = Modifier.size(20.dp))
    CenteredPicture(
        bitmap = bitmapFrom(R.drawable.turtle, LocalContext.current),
        description = R.string.profile_login_picture_desc,
        modifier = Modifier
            .size(150.dp),
        innerImagePadding = 15.dp,
    )
    WelcomeMessage()
    UsernameTextField(
        label = R.string.profile_login_username,
        errorMessage = errorMessage,
        username = username,
        error = wrongLogin,
    ) {
        username = it
        wrongLogin = false
    }
    PasswordTextField(
        label = R.string.profile_login_password,
        password = password,
    ) {
        password = it
        wrongLogin = false
    }
    SignUpText(
        questionText = stringResource(id = R.string.profile_login_sign_up_question),
        buttonText = stringResource(id = R.string.sign_up_button),
    ) {
        shouldShowSignUp = true
    }
    if (shouldShowSignUp) {
        RegisterDialog(
            userAuth,
            { shouldShowSignUp = false },
        ) {
            onSignInClick()
        }
    }
    val scope = rememberCoroutineScope()
    Button(
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 5.dp,
            pressedElevation = 15.dp,
            disabledElevation = 0.dp,
        ),
        onClick = {
            if (networkAvailability != NetworkAvailability.AVAILABLE) {
                shouldShowNetworkDialog = true
            } else {
                scope.launch {
                    val loginResult = userAuth.login(username, password)
                    if (!loginResult.first) {
                        wrongLogin = true
                        errorMessage = loginResult.second
                    } else {
                        onSignInClick()
                    }
                }
            }
        },
    ) {
        Text(text = stringResource(id = R.string.profile_sign_in))
    }
    Spacer(modifier = Modifier.size(5.dp))

    NetworkWarningDialog(
        showDialog = shouldShowNetworkDialog,
        reason = R.string.action_requires_internet,
        onSettingsClick = onGoToSettingsClick,
        onSecondaryButtonClick = { shouldShowNetworkDialog = false },
        secondaryButtonText = R.string.retry,
        onDismiss = { shouldShowNetworkDialog = false },
    )
}

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

@Composable
fun SignUpText(
    questionText: String,
    buttonText: String,
    onSignInClick: () -> Unit,
) {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = questionText,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = buttonText,
            modifier = Modifier
                .padding(start = 4.dp)
                .clickable { onSignInClick() },
        )
    }
}
