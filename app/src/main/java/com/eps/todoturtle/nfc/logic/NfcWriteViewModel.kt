package com.eps.todoturtle.nfc.logic

import android.content.Intent
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eps.todoturtle.nfc.logic.NfcWriteDevice.Init.NfcWriteDevice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import java.util.UUID

class NfcWriteViewModel private constructor(componentActivity: ComponentActivity) : ViewModel() {

    private val lifecycleScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var getNfcWriteDevice: () -> NfcWriteDevice = {
        componentActivity.NfcWriteDevice(DeviceInformation(UUID.randomUUID().toString()))
    }
    private var nfcWriteDevice = getNfcWriteDevice()
    private var nfcAction: () -> Unit = getNfcOpenAction(componentActivity)
    private var callback: (WriteOperationStatus) -> Unit = {}

    fun onNfcOperation(callback: (WriteOperationStatus) -> Unit) {
        this.callback = callback
        startCorroutine()
    }

    fun startCorroutine() {
        nfcWriteDevice.operationResults.onEach {
            runBlocking(Dispatchers.Main) {
                this@NfcWriteViewModel.callback(it)
            }
        }.launchIn(lifecycleScope)
    }

    fun goToNfcSettings() {
        nfcAction()
    }

    fun setActivity(componentActivity: ComponentActivity) {
        nfcAction = getNfcOpenAction(componentActivity)
        nfcWriteDevice = componentActivity.NfcWriteDevice(DeviceInformation(UUID.randomUUID().toString()))
        getNfcWriteDevice = { componentActivity.NfcWriteDevice(DeviceInformation(UUID.randomUUID().toString())) }
    }

    private fun getNfcOpenAction(componentActivity: ComponentActivity): () -> Unit = {
        startActivity(componentActivity, Intent(Settings.ACTION_NFC_SETTINGS), null)
    }

    fun finishNfc() {
        nfcWriteDevice = getNfcWriteDevice()
        startCorroutine()
    }

    object INIT {
        @Suppress("UNCHECKED_CAST")
        private class NfcWriteViewModelFactory(
            private val context: ComponentActivity,
        ) :
            ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return NfcWriteViewModel(context) as T
            }
        }

        fun ComponentActivity.getNfcWriteModel(): NfcWriteViewModel {
            val viewModel = ViewModelProvider(
                this,
                NfcWriteViewModelFactory(
                    this,
                ),
            )[NfcWriteViewModel::class.java]
            viewModel.setActivity(this)
            return viewModel
        }
    }
}
