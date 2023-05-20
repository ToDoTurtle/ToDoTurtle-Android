package com.eps.todoturtle

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.eps.todoturtle.preferences.ui.PreferenceUIWithoutConnection
import com.eps.todoturtle.shared.logic.extensions.dataStore
import com.eps.todoturtle.ui.theme.ToDoTurtleTheme

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ToDoTurtleTheme(dataStore) {
                Scaffold(
                    floatingActionButton = {
                        FloatingActionButton(onClick = { onGoHomeClick() }) {
                            Icon(Icons.Filled.Home, contentDescription = stringResource(R.string.go_home))
                        }
                    },
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background,
                    ) {
                        PreferenceUIWithoutConnection(
                            dataStore = dataStore,
                        )
                    }
                }
            }
        }
    }

    private fun onGoHomeClick() {
        finish()
    }
}
