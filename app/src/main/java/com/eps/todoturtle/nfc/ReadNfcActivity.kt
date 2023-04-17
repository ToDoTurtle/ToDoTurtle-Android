package com.eps.todoturtle.nfc

import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.eps.todoturtle.ui.theme.ToDoTurtleTheme

class ReadNfcActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val message = getMessage()
        // Here we should handle the message (uuid) properly, for now, we're printing it
        setContent {
            ToDoTurtleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting2(message)
                }
            }
        }
    }

    private fun getMessage(): String {
        if (intent.action == NfcAdapter.ACTION_NDEF_DISCOVERED) {
            return getMessage(intent)
        }
        return "Error getting NFC"
    }

    private fun getMessage(intent: Intent): String {
        val parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
        with(parcelables) {
            val inNdefMessage = this?.get(0) as NdefMessage
            val inNdefRecord = inNdefMessage.records
            return String(inNdefRecord[0].payload)
        }
    }

}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ToDoTurtleTheme {
        Greeting2("Android")
    }
}
