package com.eps.todoturtle.nfc.logic.write

import androidx.activity.ComponentActivity
import com.eps.todoturtle.nfc.logic.NfcParcelable
import com.eps.todoturtle.nfc.logic.state.NfcTagDetector.Companion.NfcTagDetector
import kotlinx.coroutines.flow.MutableStateFlow

class NfcWriterInformer(
    activity: ComponentActivity,
    var message: NfcParcelable,
) {

    val writeResults: MutableStateFlow<WriteOperation?> = MutableStateFlow(null)

    init {
        activity.NfcTagDetector { tag ->
            writeResults.value = NfcTagWriter.write(tag, message)
        }
    }
}
