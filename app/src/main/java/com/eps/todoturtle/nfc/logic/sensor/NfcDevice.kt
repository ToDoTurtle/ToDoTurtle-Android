package com.eps.todoturtle.nfc.logic.sensor

import androidx.activity.ComponentActivity
import com.eps.todoturtle.nfc.logic.DeviceInformation
import com.eps.todoturtle.nfc.logic.NfcStatus
import com.eps.todoturtle.nfc.logic.WriteOperationStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NfcDevice(
    val activity: ComponentActivity,
    information: DeviceInformation
) {
    private val nfcStatusInformer: NfcStatusInformer = NfcStatusInformer(activity)
    private val writer = NfcWriterInformer(activity, information)
    private val writeResult: MutableStateFlow<WriteOperationStatus?> = writer.writeResults
    val status: StateFlow<NfcStatus> = nfcStatusInformer.nfcStatus.asStateFlow()
    val writeResults: StateFlow<WriteOperationStatus?> = writeResult.asStateFlow()

    fun resetWriteNfc(newInformationToWrite: DeviceInformation) {
        writeResult.value = null
        writer.message = newInformationToWrite
    }

}