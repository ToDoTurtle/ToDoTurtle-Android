package com.eps.todoturtle.nfc

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.eps.todoturtle.nfc.logic.NfcWriteViewModel
import com.eps.todoturtle.nfc.logic.NfcWriteViewModelFactory
import com.eps.todoturtle.nfc.logic.WriteOperationStatus
import com.eps.todoturtle.ui.theme.ToDoTurtleTheme

class WriteToDeviceActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = getDevicesViewModel()
        setContent {
            ToDoTurtleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    viewModel.onNfcOperation(::sayHi)
//                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

fun Context.sayHi(operation: WriteOperationStatus) {
    when (operation) {
        WriteOperationStatus.SUCCESS -> toast("Written to NFC Tag")
        WriteOperationStatus.PREPARED -> {}
        WriteOperationStatus.NOT_WRITABLE -> toast("NFC isn't writable")
        WriteOperationStatus.TAG_LOST -> toast("You moved the NFC tag dummy")
        WriteOperationStatus.MESSAGE_FORMAT_ERROR -> toast("Error saving the NFC message because of the developer fault")
        WriteOperationStatus.UNKNOWN_ERROR -> toast("Unknown error during NFC")
        WriteOperationStatus.NFC_NOT_SUPPORTED -> toast("NFC isn't supported on this device")
        WriteOperationStatus.NFC_NOT_ENABLED -> toast("NFC isn't enabled on this device")
    }
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun ComponentActivity.getDevicesViewModel(): NfcWriteViewModel {
    return ViewModelProvider(
        this, NfcWriteViewModelFactory(
            this,
        )
    ).get(NfcWriteViewModel::class.java)
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    ToDoTurtleTheme {
//        Greeting("Android")
//    }
//}