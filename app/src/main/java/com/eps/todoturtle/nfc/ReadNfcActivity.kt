package com.eps.todoturtle.nfc

import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.eps.todoturtle.action.infra.FirebaseActionRepository
import com.eps.todoturtle.action.logic.ActionViewModel.Companion.getActionViewModel
import com.eps.todoturtle.action.logic.NoteAction
import com.eps.todoturtle.nfc.ui.AnimatedDisappearingText
import com.eps.todoturtle.shared.logic.extensions.dataStore
import com.eps.todoturtle.ui.theme.ToDoTurtleTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ReadNfcActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = getId()
        if (Firebase.auth.currentUser == null) {
            Toast.makeText(this, "Please login to continue", Toast.LENGTH_SHORT).show()
            finish()
        }
        if (id == null) {
            Toast.makeText(this, "Error reading id!", Toast.LENGTH_SHORT).show()
            finish()
        }
        val actionRepository = FirebaseActionRepository()
        val viewModel = getActionViewModel(actionRepository)
        val action = viewModel.actions.get(id)
        if (action == null) {
            Toast.makeText(this, "Device doesn't have any action, please configure it for use them", Toast.LENGTH_SHORT).show()
            finish()
        }
        setContent {
            ToDoTurtleTheme(dataStore) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    action?.let {
                        ShowAction(action)
                    }
                }
            }
        }
    }

    private fun getId(): String? {
        if (intent.action == NfcAdapter.ACTION_NDEF_DISCOVERED) {
            return getId(intent)
        }
        return null
    }

    private fun getId(intent: Intent): String {
        val parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
        with(parcelables) {
            val inNdefMessage = this?.get(0) as NdefMessage
            val inNdefRecord = inNdefMessage.records
            return String(inNdefRecord[0].payload)
        }
    }
}

@Composable
fun ShowAction(action: NoteAction, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize(),
    ) {
        Text(text = "Note Action Info:")
        Text(text = "Title: ${action.title}")
        Text(text = "Description: ${action.description}")
        Text(text = "Get Location: ${action.getLocation}")
        Text(text = "Deadline: ${action.deadline}")
        Text(text = "Notification: ${action.notification}")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ToDoTurtleTheme(LocalContext.current.dataStore) {
        ShowAction(NoteAction(
            title = "Dummy Title",
            description = "Dummy Description",
            getLocation = false,
            deadline = null,
            notification = null,
        ))
    }
}
