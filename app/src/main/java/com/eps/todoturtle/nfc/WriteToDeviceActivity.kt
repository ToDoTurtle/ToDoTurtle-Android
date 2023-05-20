package com.eps.todoturtle.nfc

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.eps.todoturtle.IconApp
import com.eps.todoturtle.R
import com.eps.todoturtle.action.infra.FirebaseActionRepository
import com.eps.todoturtle.devices.infra.FirebaseDeviceRepository
import com.eps.todoturtle.devices.logic.DeviceConfigurationParams
import com.eps.todoturtle.devices.logic.DeviceIconActivity
import com.eps.todoturtle.devices.logic.DevicesViewModel.Companion.getDevicesViewModel
import com.eps.todoturtle.devices.ui.DeviceConfigurationScreen
import com.eps.todoturtle.nfc.logic.NfcWriteViewModel.INIT.getNfcWriteModel
import com.eps.todoturtle.nfc.ui.WriteDevice
import com.eps.todoturtle.shared.logic.extensions.dataStore
import com.eps.todoturtle.ui.theme.ToDoTurtleTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.maltaisn.icondialog.IconDialog
import com.maltaisn.icondialog.IconDialogSettings
import com.maltaisn.icondialog.data.Icon
import com.maltaisn.icondialog.pack.IconDrawableLoader
import com.maltaisn.icondialog.pack.IconPack
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.runBlocking

class WriteToDeviceActivity : AppCompatActivity(), IconDialog.Callback, DeviceIconActivity {

    private val currentIcon: Channel<Int> = Channel(UNLIMITED)
    private lateinit var iconDialog: IconDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        iconDialog = supportFragmentManager.findFragmentByTag(ICON_DIALOG_TAG) as IconDialog?
            ?: IconDialog.newInstance(IconDialogSettings())
        theme.applyStyle(R.style.AppTheme, true)

        if (Firebase.auth.currentUser == null) {
            Toast.makeText(
                this,
                resources.getString(R.string.please_login_to_add_device),
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
        val nfcWriteViewModel = getNfcWriteModel()
        val devicesViewModel = getDevicesViewModel(
            repository = FirebaseDeviceRepository(),
            actionsRepository = FirebaseActionRepository()
        )
        setContent {
            ToDoTurtleTheme(LocalContext.current.dataStore) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    var showDevices by rememberSaveable { mutableStateOf(false) }
                    if (!showDevices) {
                        WriteDevice(
                            viewModel = nfcWriteViewModel,
                            onNfcNotSupported = {
                                Toast.makeText(this, resources.getString(R.string.nfc_not_supported), Toast.LENGTH_SHORT).show()
                                finish()
                            },
                            onTagLost = {
                                Toast.makeText(
                                    this,
                                    resources.getString(R.string.move_too_fast_solution),
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                finish()
                            },
                            onTagNotWriteable = {
                                Toast.makeText(
                                    this,
                                    resources.getString(R.string.nfc_tag_not_supported_solution),
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            },
                            unknownError = {
                                Toast.makeText(
                                    this,
                                    resources.getString(R.string.unknown_error_nfc_solution),
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            },
                            onWriteSuccessful = { id ->
                                nfcWriteViewModel.finishWriteNfc()
                                devicesViewModel.deviceBuilder.identifier = id
                                showDevices = true
                            },
                        )
                    } else {
                        DeviceConfigurationScreen(devicesViewModel, DeviceConfigurationParams.NEW) {
                            Toast.makeText(
                                this,
                                resources.getString(R.string.device_added_successfully, it.name),
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        }
                    }
                }
            }
        }
    }

    override val iconDialogIconPack: IconPack?
        get() = (application as IconApp).iconPack

    override fun onIconDialogIconsSelected(dialog: IconDialog, icons: List<Icon>) {
        runBlocking(Dispatchers.IO) {
            val icon = icons.last().id
            currentIcon.send(icon)
        }
    }

    companion object {
        private const val ICON_DIALOG_TAG = "icon-dialog"
    }

    override fun startIconSelectionLambda(): () -> Unit = {
        iconDialog.show(supportFragmentManager, ICON_DIALOG_TAG)
    }

    override fun getIconChannel(): Channel<Int> = currentIcon

    override fun getIconDrawable(): (Int) -> Drawable? = ::getIconD

    private fun getIconD(id: Int): Drawable? {
        val iconDrawableLoader = IconDrawableLoader(this)
        return iconDialogIconPack?.getIconDrawable(id, iconDrawableLoader)
    }

}
