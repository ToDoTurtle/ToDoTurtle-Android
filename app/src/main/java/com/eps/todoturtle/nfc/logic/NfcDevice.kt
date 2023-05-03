package com.eps.todoturtle.nfc.logic

import androidx.activity.ComponentActivity
import com.eps.todoturtle.devices.logic.DeviceInformation
import com.eps.todoturtle.nfc.logic.state.NfcStatus
import com.eps.todoturtle.nfc.logic.state.NfcStatusInformer
import com.eps.todoturtle.nfc.logic.write.NfcWriterInformer
import com.eps.todoturtle.nfc.logic.write.WriteOperation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NfcDevice(
    val activity: ComponentActivity,
    information: DeviceInformation,
) {
    private val nfcStatusInformer: NfcStatusInformer = NfcStatusInformer(activity)
    private val writer = NfcWriterInformer(activity, information)
    private val writeResult: MutableStateFlow<WriteOperation?> = writer.writeResults
    val status: StateFlow<NfcStatus> = nfcStatusInformer.nfcStatus.asStateFlow()
    val writeResults: StateFlow<WriteOperation?> = writeResult.asStateFlow()

    fun resetWriteNfc(newInformationToWrite: DeviceInformation) {
        writeResult.value = null
        writer.message = newInformationToWrite
    }
}
