package com.eps.todoturtle.nfc.logic

import android.content.Intent
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import com.eps.todoturtle.nfc.logic.NfcWriteDevice.Init.NfcWriteDevice
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import java.util.UUID

class NfcWriteViewModel(componentActivity: ComponentActivity): ViewModel() {

    private val lifecycleScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val nfcWriteDevice = componentActivity.NfcWriteDevice(DeviceInformation(UUID.randomUUID().toString()))
    private val nfcAction: () -> Unit = {
        startActivity(componentActivity, Intent(Settings.ACTION_NFC_SETTINGS), null)
    }

    fun onNfcOperation(callback: (WriteOperationStatus) -> Unit) {
        nfcWriteDevice.operationResults.onEach {
            runBlocking(Dispatchers.Main) {
                callback(it)
            }
        }.launchIn(lifecycleScope)
    }

    fun goToNfcSettings() { nfcAction() }

}

@Suppress("UNCHECKED_CAST")
class NfcWriteViewModelFactory(
    private val context: ComponentActivity
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NfcWriteViewModel(context) as T
    }
}

