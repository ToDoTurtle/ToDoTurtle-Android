package com.eps.todoturtle.nfc

import android.content.Intent.FLAG_ACTIVITY_NO_ANIMATION
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.eps.todoturtle.R
import com.eps.todoturtle.nfc.logic.NfcWriteViewModel.INIT.getNfcWriteModel
import com.eps.todoturtle.nfc.ui.WriteDevice
import com.eps.todoturtle.shared.logic.extensions.dataStore
import com.eps.todoturtle.ui.theme.ToDoTurtleTheme

class WriteToDeviceActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = getNfcWriteModel()
        setContent {
            ToDoTurtleTheme(LocalContext.current.dataStore) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    WriteDevice(viewModel = viewModel,
                        onTagNotWriteable = ::restartActivity,
                        onTagLost = ::restartActivity,
                        unknownError = ::restartActivity,
                        onNfcNotSupported = {
                            Toast.makeText(this, getString(R.string.nfc_not_supported_solution), Toast.LENGTH_SHORT).show()
                            finish()
                        },
                        onWriteSuccessful = {
                            Toast.makeText(this, getString(R.string.nfc_write_success), Toast.LENGTH_SHORT)
                                .show()
                            finish()
                        })
                }
            }
        }
    }

    private fun restartActivity() {
        finish()
        startActivity(intent.apply {
            addFlags(FLAG_ACTIVITY_NO_ANIMATION)
        })
    }
}

