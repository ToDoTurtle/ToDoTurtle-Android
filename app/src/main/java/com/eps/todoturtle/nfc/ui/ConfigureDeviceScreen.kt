package com.eps.todoturtle.nfc.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eps.todoturtle.shared.logic.extensions.dataStore
import com.eps.todoturtle.ui.theme.ToDoTurtleTheme

@Composable
fun DeviceConfigurationScreen(onSave: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        var deviceName by rememberSaveable { mutableStateOf("") }
        var description by rememberSaveable { mutableStateOf("") }
        DeviceNameChooser(deviceName) { deviceName = it }
        DescriptionChooser(description) { description = it }
        IconChooser { }
        SaveButton(onSave)
    }
}

@Composable
fun DeviceNameChooser(value: String, onChange: (String) -> Unit) {
    Text(
        text = "Device Name",
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier.padding(bottom = 8.dp),
    )
    TextField(
        value = value,
        onValueChange = onChange,
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
    )
}

@Composable
fun DescriptionChooser(value: String, onChange: (String) -> Unit) {
    Text(
        text = "Description",
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
    )
    TextField(
        value = value,
        onValueChange = onChange,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
fun IconChooser(onClick: () -> Unit) {
    Text(
        text = "Choose an Icon",
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
    )
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(text = "Choose Icon")
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
        Text(text = "Save")
    }
}

@Preview(showBackground = true)
@Composable
fun ConfigureDeviceScreenPreview() {
    ToDoTurtleTheme(storage = LocalContext.current.dataStore) {
    }
}
