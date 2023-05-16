package com.eps.todoturtle.devices.logic


const val DEVICE_CONFIGURATION_PARAM = "configuration_context"
const val DEVICE_CONFIGURATION_ID = "configuration_id"
const val DEVICE_CONFIGURATION = "device_configuration/{$DEVICE_CONFIGURATION_PARAM}/{$DEVICE_CONFIGURATION_ID}"

enum class DEVICE_CONFIGURATION_PARAMS(val value: String) {
    NEW("NEW"),
    EDIT("EDIT");

    companion object {
        fun fromString(value: String?): DEVICE_CONFIGURATION_PARAMS? = when (value) {
            NEW.value -> NEW
            EDIT.value -> EDIT
            else -> null
        }
    }

    fun getUri(id: String): String {
        return "device_configuration/$value/$id"
    }
}
