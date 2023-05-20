package com.eps.todoturtle

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.eps.todoturtle.network.logic.ConnectionChecker
import com.eps.todoturtle.network.logic.ConnectionCheckerImpl
import com.eps.todoturtle.network.logic.NetworkAvailability
import com.eps.todoturtle.network.ui.NetworkWarningDialog
import com.eps.todoturtle.profile.logic.UserAuth
import com.eps.todoturtle.shared.logic.extensions.dataStore
import com.eps.todoturtle.ui.theme.ToDoTurtleTheme
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class InitialActivity : AppCompatActivity() {
    private lateinit var connectionChecker: ConnectionChecker
    private lateinit var connectionAvailability: NetworkAvailability
    private val coroutineScope: CoroutineScope by lazy {
        lifecycleScope + CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectionChecker = ConnectionCheckerImpl(this)
        val connectionAvailabilityFlow = connectionChecker.networkAvailability

        coroutineScope.launch {
            connectionAvailabilityFlow.collectLatest { availability ->
                connectionAvailability = availability
            }
        }

        setContent {
            ToDoTurtleTheme(dataStore) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val connectionAvailability by connectionAvailabilityFlow.collectAsStateWithLifecycle(
                        initialValue = connectionAvailability
                    )
                    if (connectionAvailability == NetworkAvailability.AVAILABLE) {
                        startApp()
                    }
                    NetworkWarningDialog(
                        showDialog = connectionAvailability != NetworkAvailability.AVAILABLE,
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

    override fun onResume() {
        super.onResume()
        connectionChecker.updateFlows()
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
