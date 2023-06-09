package com.eps.todoturtle.profile.ui.register.providers.github

import android.widget.Toast
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eps.todoturtle.R
import com.eps.todoturtle.profile.logic.UserAuth
import kotlinx.coroutines.launch

@Composable
fun GithubButton(
    userAuth: UserAuth,
    onSuccessfulRegister: () -> Unit,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Button(
        onClick = {
            scope.launch {
                val loginResult = userAuth.loginWithGithub()
                if (loginResult.first) {
                    onSuccessfulRegister()
                } else {
                    Toast.makeText(
                        context,
                        loginResult.second,
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
        },
    ) {
        Icon(
            painter = painterResource(id = R.drawable.github),
            contentDescription = stringResource(
                id = R.string.github_desc,
            ),
            modifier = Modifier.size(24.dp),
        )
    }
}
