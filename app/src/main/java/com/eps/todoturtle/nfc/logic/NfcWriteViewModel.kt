package com.eps.todoturtle.nfc.logic

import android.content.Intent
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import com.eps.todoturtle.devices.logic.DeviceInformation
import com.eps.todoturtle.nfc.logic.state.NfcStatus
import com.eps.todoturtle.nfc.logic.write.WriteOperation
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

class NfcWriteViewModel private constructor() : ViewModel() {

    private var deviceInformation = DeviceInformation(UUID.randomUUID().toString())

    private lateinit var nfcDevice: NfcDevice
    private lateinit var openNfcSettings: () -> Unit
    lateinit var writeResults: StateFlow<WriteOperation?>
        private set
    lateinit var status: StateFlow<NfcStatus>
        private set

    fun showNfcSettings() = openNfcSettings()

    private fun setActivity(componentActivity: ComponentActivity) {
        with(componentActivity) {
            openNfcSettings = getNfcOpenAction()
            nfcDevice = NfcDevice(this, deviceInformation)
            writeResults = nfcDevice.writeResults
            status = nfcDevice.status
        }
    }

    fun finishWriteNfc() {
        deviceInformation = DeviceInformation(UUID.randomUUID().toString())
        nfcDevice.resetWriteNfc(deviceInformation)
    }

    object INIT {
        fun ComponentActivity.getNfcWriteModel(): NfcWriteViewModel {
            val viewModel = NfcWriteViewModel()
            viewModel.setActivity(this)
            return viewModel
        }
    }

    private fun ComponentActivity.getNfcOpenAction(): () -> Unit = {
        startActivity(this, Intent(Settings.ACTION_NFC_SETTINGS), null)
    }
}
