package com.eps.todoturtle

import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.eps.todoturtle.network.ui.NetworkWarningDialog
import com.eps.todoturtle.profile.logic.UserAuth
import com.eps.todoturtle.shared.logic.extensions.dataStore
import com.eps.todoturtle.ui.theme.ToDoTurtleTheme
import com.google.firebase.auth.FirebaseAuth

class InitialActivity : AppCompatActivity() {
    private lateinit var connectivityManager: ConnectivityManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities =
            connectivityManager.activeNetwork.let { connectivityManager.getNetworkCapabilities(it) }
        val hasConnection: Boolean =
            networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
        setContent {
            ToDoTurtleTheme(dataStore) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    if (hasConnection) startApp()
                    NetworkWarningDialog(
                        showDialog = !hasConnection,
                        reason = R.string.app_requires_internet,
                        onSettingsClick = ::onGoToSettingsClick,
                        onSecondaryButtonClick = ::onCloseAppClick,
                        secondaryButtonText = R.string.close_app,
                        onDismiss = { startApp() },
                    )
                }
            }
        }
    }

    private fun onCloseAppClick() {
        finish()
    }

    private fun onGoToSettingsClick() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun startApp() {
        val auth = FirebaseAuth.getInstance()
        val userAuth = UserAuth(this@InitialActivity, auth)

        intent = Intent(
            this,
            if (userAuth.isLoggedIn()) MainActivity::class.java else LoginActivity::class.java,
        )
        startActivity(intent)
    }
}
