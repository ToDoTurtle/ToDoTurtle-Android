package com.eps.todoturtle.nfc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.eps.todoturtle.nfc.logic.NfcWriteViewModel
import com.eps.todoturtle.nfc.logic.NfcWriteViewModelFactory
import com.eps.todoturtle.nfc.ui.DevicesScreen
import com.eps.todoturtle.ui.theme.ToDoTurtleTheme

class WriteToDeviceActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = getDevicesViewModel()
        setContent {
            ToDoTurtleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DevicesScreen(viewModel = viewModel)
                }
            }
        }
    }
}

fun ComponentActivity.getDevicesViewModel(): NfcWriteViewModel {
    return ViewModelProvider(
        this, NfcWriteViewModelFactory(
            this,
        )
    )[NfcWriteViewModel::class.java]
}
