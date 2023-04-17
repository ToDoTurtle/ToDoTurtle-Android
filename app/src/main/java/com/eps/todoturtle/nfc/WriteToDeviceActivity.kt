package com.eps.todoturtle.nfc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.eps.todoturtle.nfc.logic.NfcWriteViewModel.INIT.getNfcWriteModel
import com.eps.todoturtle.nfc.ui.WriteDevice
import com.eps.todoturtle.ui.theme.ToDoTurtleTheme

class WriteToDeviceActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = getNfcWriteModel()
        setContent {
            ToDoTurtleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    WriteDevice(viewModel = viewModel)
                }
            }
        }
    }
}
