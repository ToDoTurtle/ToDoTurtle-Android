package com.eps.todoturtle.nfc.logic.sensor

import androidx.activity.ComponentActivity
import com.eps.todoturtle.nfc.logic.NfcParcelable
import com.eps.todoturtle.nfc.logic.WriteOperationStatus
import com.eps.todoturtle.nfc.logic.sensor.NfcTagDetector.Companion.NfcTagDetector
import kotlinx.coroutines.flow.MutableStateFlow

class NfcWriterInformer(
    activity: ComponentActivity,
    var message: NfcParcelable,
) {

    val writeResults: MutableStateFlow<WriteOperationStatus?> = MutableStateFlow(null)

    init {
        activity.NfcTagDetector { tag ->
            writeResults.value = NfcTagWriter.write(tag, message)
        }
    }

}