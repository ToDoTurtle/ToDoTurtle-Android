package com.eps.todoturtle.permissions.logic

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.lifecycle.ViewModelProvider
import com.eps.todoturtle.permissions.logic.providers.PermissionProvider
import com.eps.todoturtle.permissions.ui.PermissionDialog

class PermissionRequester(
    private val parentActivity: ComponentActivity,
    private val permissionProviders: List<PermissionProvider>,
) {
    private val viewModel = ViewModelProvider(parentActivity)[PermissionsViewModel::class.java]
    private val launcher = parentActivity.registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
    ) { results ->
        permissionProviders.toArray().forEach { permission ->
            viewModel.onPermissionResult(
                permission = permission,
                isGranted = results[permission] ?: false,
            )
        }
    }

    @Composable
    internal fun SetUp() {
        viewModel.declinedPermissionQueue.reversed().forEach { permission ->
            val provider = permissionProviders.get(permission) ?: return@forEach
            PermissionDialog(
                permissionTextProvider = provider.textProvider,
                isPermanentlyDeclined = !shouldShowRequestPermissionRationale(
                    parentActivity,
                    permission,
                ),
                onDismiss = viewModel::onDismissDialog,
                onOkClick = {
                    viewModel.onDismissDialog()
                    launcher.launch(permissionProviders.toArray())
                },
                onGoToAppSettingsClick = parentActivity::openAppSettings,
            )
        }
    }

    fun requestPermissions() = launcher.launch(permissionProviders.toArray())

    private fun List<PermissionProvider>.get(permission: String): PermissionProvider? {
        return this.find { it.permission == permission }
    }

    private fun List<PermissionProvider>.toArray() = this.map { it.permission }.toTypedArray()
}

internal fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null),
    ).also(::startActivity)
}

@Composable
fun RequestPermissionContext(
    permissionRequester: PermissionRequester,
    content: @Composable PermissionRequester.() -> Unit,
) {
    permissionRequester.SetUp()
    permissionRequester.content()
}
