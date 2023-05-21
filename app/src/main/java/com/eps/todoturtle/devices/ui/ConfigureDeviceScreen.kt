package com.eps.todoturtle.devices.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.eps.todoturtle.R
import com.eps.todoturtle.devices.logic.DeviceBuildError
import com.eps.todoturtle.devices.logic.DeviceConfigurationParams
import com.eps.todoturtle.devices.logic.DevicesViewModel
import com.eps.todoturtle.devices.logic.NFCDevice
import com.eps.todoturtle.shared.logic.extensions.dataStore
import com.eps.todoturtle.ui.theme.ToDoTurtleTheme

@Composable
fun DeviceConfigurationScreen(
    devicesViewModel: DevicesViewModel,
    configuration: DeviceConfigurationParams,
    onDeviceSaved: (NFCDevice) -> Unit,
) {
    val deviceSaved by devicesViewModel.deviceCreated.collectAsStateWithLifecycle(initialValue = null)
    DeviceForm(devicesViewModel = devicesViewModel, configuration)
    deviceSaved?.let { onDeviceSaved(it) }
}

@Composable
fun DeviceForm(devicesViewModel: DevicesViewModel, configuration: DeviceConfigurationParams) {
    val errors by devicesViewModel.deviceErrors.collectAsStateWithLifecycle()
    val iconError: Boolean = errors.contains(DeviceBuildError.NON_ICON)
    val nameError: Boolean = errors.contains(DeviceBuildError.NAME_EMPTY)
    val descriptionError: Boolean = errors.contains(DeviceBuildError.DESCRIPTION_EMPTY)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        var deviceName by rememberSaveable { devicesViewModel.deviceBuilder.name }
        var description by rememberSaveable { devicesViewModel.deviceBuilder.description }
        DeviceNameChooser(deviceName, nameError) { deviceName = it }
        DescriptionChooser(description, descriptionError) { description = it }
        IconChooser(iconError) { devicesViewModel.showIconSelection() }
        SaveButton {
            when (configuration) {
                DeviceConfigurationParams.NEW -> devicesViewModel.saveDevice()
                DeviceConfigurationParams.EDIT -> devicesViewModel.updateDevice()
            }
        }
    }
}

@Composable
fun DeviceNameChooser(value: String, isError: Boolean, onChange: (String) -> Unit) {
    Text(
        text = "Device Name",
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier.padding(bottom = 8.dp),
    )
    TextField(
        value = value,
        onValueChange = onChange,
        isError = isError,
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        supportingText = {
            if (isError) {
                Text(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    text = stringResource(id = R.string.device_name_not_valid),
                    color = MaterialTheme.colorScheme.error,
                )
            }
        },
    )
}

@Composable
fun DescriptionChooser(value: String, isError: Boolean, onChange: (String) -> Unit) {
    val focusManager = LocalFocusManager.current
    Text(
        text = "Description",
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
    )
    TextField(
        value = value,
        isError = isError,
        onValueChange = onChange,
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        maxLines = 4,
        modifier = Modifier.fillMaxWidth(),
        supportingText = {
            if (isError) {
                Text(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    text = stringResource(id = R.string.device_description_not_valid),
                    color = MaterialTheme.colorScheme.error,
                )
            }
        },
    )
}

@Composable
fun IconChooser(isError: Boolean, onClick: () -> Unit) {
    Text(
        text = stringResource(id = R.string.icon_chooser_description),
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
    )
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(text = stringResource(id = R.string.icon_chooser_button))
    }
    if (isError) {
        Text(
            text = stringResource(id = R.string.not_icon_selected_error),
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
fun SaveButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(top = 32.dp)
            .fillMaxWidth()
            .height(50.dp),
    ) {
        Text(text = stringResource(id = R.string.save_device))
    }
}

@Preview(showBackground = true)
@Composable
fun ConfigureDeviceScreenPreview() {
    ToDoTurtleTheme(storage = LocalContext.current.dataStore) {
    }
}
