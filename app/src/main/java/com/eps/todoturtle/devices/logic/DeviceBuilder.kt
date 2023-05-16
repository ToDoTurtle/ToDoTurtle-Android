package com.eps.todoturtle.devices.logic

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class DeviceBuildResult {
    data class Success(val device: NFCDevice) : DeviceBuildResult()
    data class Failure(val errors: Collection<DeviceBuildError>) : DeviceBuildResult()
}

class DeviceBuilder(
    var identifier: String,
    var name: MutableState<String> = mutableStateOf(""),
    var description: MutableState<String> = mutableStateOf(""),
    var iconResId: Int? = null,
) {

    private var alreadyBuild: Boolean = false

    fun build(): DeviceBuildResult {
        if (alreadyBuild)
            throw Error("Programming Error: Building a Device 2 times with the same builder")
        val errors: MutableList<DeviceBuildError> = mutableListOf()
        if (name.value.isBlank() || name.value.isEmpty()) {
            errors.add(DeviceBuildError.NAME_EMPTY)
        }
        if (description.value.isBlank() || description.value.isEmpty()) {
            errors.add(DeviceBuildError.DESCRIPTION_EMPTY)
        }
        if (iconResId == null) {
            errors.add(DeviceBuildError.NON_ICON)
        }
        if (errors.isNotEmpty()) return DeviceBuildResult.Failure(errors)
        val device =
            NFCDevice(name.value, description.value, identifier, iconResId!!)
        alreadyBuild = true
        return DeviceBuildResult.Success(device)
    }

}