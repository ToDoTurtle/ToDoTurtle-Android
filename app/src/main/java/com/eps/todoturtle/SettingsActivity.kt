package com.eps.todoturtle

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.eps.todoturtle.network.logic.ConnectionCheckerImpl
import com.eps.todoturtle.preferences.ui.PreferenceUIWithoutConnection
import com.eps.todoturtle.shared.logic.extensions.dataStore
import com.eps.todoturtle.ui.theme.ToDoTurtleTheme
import kotlinx.coroutines.flow.emptyFlow

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val connectionChecker = ConnectionCheckerImpl(this)
//        val connectionAvailability = connectionChecker.networkAvailability

        setContent {
            ToDoTurtleTheme(dataStore) {
                val scope = rememberCoroutineScope()
                val snackbarHostState = remember { SnackbarHostState() }
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(hostState = snackbarHostState)
                    },
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background,
                    ) {
                        PreferenceUIWithoutConnection(
                            dataStore = dataStore,
                            networkAvailability = emptyFlow()
                        )
                    }
                }
            }
        }
    }
}
