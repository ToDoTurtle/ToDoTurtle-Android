package com.eps.todoturtle.devices.logic

import android.graphics.drawable.Drawable
import androidx.activity.ComponentActivity
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eps.todoturtle.action.logic.ActionRepository
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

class DevicesViewModel private constructor(
    repository: DeviceRepository,
    private val actionsRepository: ActionRepository,
) : ViewModel() {

    private lateinit var iconToDrawable: (id: Int) -> Drawable?
    private lateinit var iconSelection: () -> Unit
    private lateinit var iconChannel: Channel<Int>

    private val repository = DeviceStateRepository(repository)

    private val scope = SupervisorJob() + Dispatchers.IO
    var deviceBuilder = DeviceBuilder()

    val deviceErrors: MutableStateFlow<Collection<DeviceBuildError>> = MutableStateFlow(emptyList())
    private val deviceCreator: Channel<NFCDevice> = Channel()
    val deviceCreated: Flow<NFCDevice> = deviceCreator.receiveAsFlow()

    fun delete(device: NFCDevice) {
        runBlocking(Dispatchers.IO) {
            actionsRepository.getActionForDeviceWithId(device.identifier)?.let {
                actionsRepository.removeLinkForDevice(device.identifier)
            }
        }
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
        if (!iconChannel.isEmpty) {
            runBlocking { deviceBuilder.iconResId = iconChannel.receive() }
        } else {
            deviceBuilder.iconResId = null
        }
        when (val result = deviceBuilder.build()) {
            is DeviceBuildResult.Success -> {
                runBlocking(Dispatchers.IO) {
                    repository.add(result.device)
                    deviceCreator.send(result.device)
                }
                deviceBuilder.clear()
            }

            is DeviceBuildResult.Failure -> {
                deviceErrors.value = result.errors
            }
        }
    }

    fun setCurrentEditDevice(nfcDevice: NFCDevice) {
        deviceBuilder.identifier = nfcDevice.identifier
        deviceBuilder.name.value = nfcDevice.name
        deviceBuilder.description.value = nfcDevice.description
        deviceBuilder.iconResId = nfcDevice.iconResId
    }

    fun showIconSelection() = iconSelection()

    fun getDrawable(resourceId: Int): Drawable? = iconToDrawable(resourceId)

    companion object {
        @Suppress("UNCHECKED_CAST")
        private class NfcWriteViewModelFactory(
            val repository: DeviceRepository,
            val actionsRepository: ActionRepository,
        ) :
            ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DevicesViewModel(repository, actionsRepository) as T
            }
        }

        fun <T> T.getDevicesViewModel(
            repository: DeviceRepository,
            actionsRepository: ActionRepository,
        ): DevicesViewModel where T : DeviceIconActivity, T : ComponentActivity {
            val viewModel = ViewModelProvider(
                this,
                NfcWriteViewModelFactory(
                    repository,
                    actionsRepository,
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

    @OptIn(ExperimentalCoroutinesApi::class)
    fun updateDevice() {
        if (!iconChannel.isEmpty) {
            runBlocking { deviceBuilder.iconResId = iconChannel.receive() }
        }
        when (val result = deviceBuilder.build()) {
            is DeviceBuildResult.Success -> {
                runBlocking(Dispatchers.IO) {
                    repository.remove(result.device)
                    repository.add(result.device)
                    deviceCreator.send(result.device)
                }
                deviceBuilder.clear()
            }

            is DeviceBuildResult.Failure -> {
                deviceErrors.value = result.errors
            }
        }
    }
}
