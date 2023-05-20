package com.eps.todoturtle

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.eps.todoturtle.network.logic.ConnectionChecker
import com.eps.todoturtle.network.logic.ConnectionCheckerImpl
import com.eps.todoturtle.profile.logic.UserAuth
import com.eps.todoturtle.profile.ui.login.LoginUI
import com.eps.todoturtle.shared.infra.addDeviceToken
import com.eps.todoturtle.shared.logic.extensions.dataStore
import com.eps.todoturtle.ui.theme.ToDoTurtleTheme
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class LoginActivity : AppCompatActivity() {

    private lateinit var connectionChecker: ConnectionChecker
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val auth = Firebase.auth
        val userAuth = UserAuth(this@LoginActivity, auth)

        this.connectionChecker = ConnectionCheckerImpl(this)
        val connectionAvailability = connectionChecker.networkAvailability

        setContent {
            ToDoTurtleTheme(dataStore) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    LoginUI(
                        userAuth = userAuth,
                        connectionAvailability = connectionAvailability,
                        onGoToSettingsClick = { onGoToSettingsClick() },
                    ) {
                        FirebaseMessaging.getInstance().token.addOnCompleteListener(
                            OnCompleteListener { task ->
                                if (!task.isSuccessful) {
                                    Toast.makeText(
                                        this@LoginActivity,
                                        "Failed to get token",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    return@OnCompleteListener
                                }
                                val token = task.result
                                runBlocking(Dispatchers.IO) {
                                    addDeviceToken(token)
                                }
                            })
                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                }
            }
        }
    }

    private fun onGoToSettingsClick() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        connectionChecker.updateFlows()
    }
}
