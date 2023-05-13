package com.eps.todoturtle.devices.logic

import android.graphics.drawable.Drawable
import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eps.todoturtle.devices.infra.DeviceStateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking
import java.util.UUID

class DevicesViewModel private constructor(repository: DeviceRepository) : ViewModel() {

    private lateinit var iconToDrawable: (id: Int) -> Drawable?
    private lateinit var iconSelection: () -> Unit
    private lateinit var iconChannel: Channel<Int>

    private val repository = DeviceStateRepository(repository)

    private val scope = SupervisorJob() + Dispatchers.IO
    var deviceBuilder = DeviceBuilder(UUID.randomUUID().toString())

    val deviceErrors: MutableStateFlow<Collection<DeviceBuildError>> = MutableStateFlow(emptyList())
    private val deviceCreator: Channel<NFCDevice> = Channel()
    val deviceCreated: Flow<NFCDevice> = deviceCreator.receiveAsFlow()

    fun delete(device: NFCDevice) {
        repository.remove(device)
    }

    fun getDevices(): SnapshotStateList<NFCDevice> = repository.getAll()

    private fun setIconSelection(iconSelection: () -> Unit) {
        this.iconSelection = iconSelection
    }

    private fun setIconChannel(iconChannel: Channel<Int>) {
        if (scope.isActive) scope.cancel()
        this.iconChannel = iconChannel
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun saveDevice() {
        if (!iconChannel.isEmpty)
            runBlocking { deviceBuilder.iconResId = iconChannel.receive() }
        else
            deviceBuilder.iconResId = null
        when (val result = deviceBuilder.build()) {
            is DeviceBuildResult.Success -> {
                runBlocking(Dispatchers.IO) {
                    repository.add(result.device)
                    deviceCreator.send(result.device)
                }
                deviceBuilder = DeviceBuilder(UUID.randomUUID().toString())
            }
            is DeviceBuildResult.Failure -> {
                deviceErrors.value = result.errors
            }
        }
    }

    fun setCurrentEditDevice(nfcDevice: NFCDevice) {
        deviceBuilder = DeviceBuilder(
            identifier = nfcDevice.identifier,
            name = mutableStateOf( nfcDevice.name),
            description = mutableStateOf(nfcDevice.description),
            iconResId = nfcDevice.iconResId,
            configured = nfcDevice.configured
        )
    }

    fun showIconSelection() = iconSelection()

    fun getDrawable(resourceId: Int): Drawable? = iconToDrawable(resourceId)

    companion object {
        @Suppress("UNCHECKED_CAST")
        private class NfcWriteViewModelFactory(
            val repository: DeviceRepository
        ) :
            ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DevicesViewModel(repository) as T
            }
        }

        fun <T> T.getDevicesViewModel(
            repository: DeviceRepository
        ): DevicesViewModel where T : DeviceIconActivity, T : ComponentActivity {
            val viewModel = ViewModelProvider(
                this,
                NfcWriteViewModelFactory(
                    repository
                ),
            )[DevicesViewModel::class.java]
            viewModel.setIconSelection(this.startIconSelectionLambda())
            viewModel.setIconChannel(this.getIconChannel())
            viewModel.setIdToDrawableConverter(this.getIconDrawable())
            return viewModel
        }

    }

    private fun setIdToDrawableConverter(iconDrawable: (id: Int) -> Drawable?) {
        this.iconToDrawable = iconDrawable
    }

    fun updateDevice() {
        when (val result = deviceBuilder.build()) {
            is DeviceBuildResult.Success -> {
                runBlocking(Dispatchers.IO) {
                    repository.remove(result.device)
                    repository.add(result.device)
                    deviceCreator.send(result.device)
                }
                deviceBuilder = DeviceBuilder(UUID.randomUUID().toString())
            }
            is DeviceBuildResult.Failure -> {
                deviceErrors.value = result.errors
            }
        }
    }

}
