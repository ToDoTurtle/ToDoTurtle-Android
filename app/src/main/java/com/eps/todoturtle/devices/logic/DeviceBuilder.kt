package com.eps.todoturtle.devices.logic

import androidx.compose.runtime.mutableStateOf

sealed class DeviceBuildResult {
    data class Success(val device: NFCDevice) : DeviceBuildResult()
    data class Failure(val errors: Collection<DeviceBuildError>) : DeviceBuildResult()
}

class DeviceBuilder {
    var identifier: String = ""
    var name = mutableStateOf("")
    var description = mutableStateOf("")
    var iconResId: Int? = null
    private val configured: Boolean = false

    fun build(): DeviceBuildResult {
        val errors: MutableList<DeviceBuildError> = mutableListOf()
        if (name.value.isBlank() || name.value.isEmpty())
            errors.add(DeviceBuildError.NAME_EMPTY)
        if (description.value.isBlank() || description.value.isEmpty())
            errors.add(DeviceBuildError.DESCRIPTION_EMPTY)
        if (iconResId == null)
            errors.add(DeviceBuildError.NON_ICON)
        if (errors.isNotEmpty()) return DeviceBuildResult.Failure(errors)
        val device =
            NFCDevice(name.value, description.value, identifier, iconResId!!, configured)
        return DeviceBuildResult.Success(device)
    }

    fun clean() {
        identifier = ""
        name = mutableStateOf("")
        description = mutableStateOf("")
        iconResId = null
    }

}