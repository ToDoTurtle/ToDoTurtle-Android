package com.eps.todoturtle

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.eps.todoturtle.devices.logic.DeviceIconActivity
import com.eps.todoturtle.devices.logic.DevicesViewModel.Companion.getDevicesViewModel
import com.eps.todoturtle.nfc.logic.NfcWriteViewModel.INIT.getNfcWriteModel
import com.eps.todoturtle.note.logic.NoteScreenViewModel
import com.eps.todoturtle.permissions.logic.PermissionRequester
import com.eps.todoturtle.permissions.logic.providers.CameraPermissionProvider
import com.eps.todoturtle.profile.logic.ProfileViewModel
import com.eps.todoturtle.shared.logic.extensions.dataStore
import com.eps.todoturtle.shared.logic.extensions.hasCameraPermission
import com.eps.todoturtle.ui.App
import com.eps.todoturtle.ui.theme.ToDoTurtleTheme
import com.maltaisn.icondialog.IconDialog
import com.maltaisn.icondialog.IconDialogSettings
import com.maltaisn.icondialog.data.Icon
import com.maltaisn.icondialog.pack.IconPack
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity(), IconDialog.Callback, DeviceIconActivity {
    private val permissionsToRequest = listOf(CameraPermissionProvider(this))
    private lateinit var permissionRequester: PermissionRequester

    private val currentIcon: Channel<Int> = Channel(UNLIMITED)
    private lateinit var iconDialog: IconDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionRequester = PermissionRequester(this, permissionsToRequest)
        val noteScreenViewModel = NoteScreenViewModel(this)
        val profileViewModel = ProfileViewModel(this)

        iconDialog = supportFragmentManager.findFragmentByTag(ICON_DIALOG_TAG) as IconDialog?
            ?: IconDialog.newInstance(IconDialogSettings())
        theme.applyStyle(R.style.AppTheme, true)

        setContent {
            ToDoTurtleTheme(dataStore) {
                App(
                    permissionRequester = permissionRequester,
                    noteScreenViewModel = noteScreenViewModel,
                    devicesViewModel = getDevicesViewModel(),
                    nfcWriteViewModel = getNfcWriteModel(),
                    profileViewModel = profileViewModel,
                    dataStore = dataStore,
                    hasCameraPermission = { hasCameraPermission() },
                )
            }
        }
    }

    override val iconDialogIconPack: IconPack?
        get() = (application as App).iconPack

    override fun onIconDialogIconsSelected(dialog: IconDialog, icons: List<Icon>) {
        runBlocking(Dispatchers.IO) {
            currentIcon.send(icons.last().id)
        }
    }

    companion object {
        private const val ICON_DIALOG_TAG = "icon-dialog"
    }

    override fun startIconSelectionLambda(): () -> Unit = {
        iconDialog.show(supportFragmentManager, ICON_DIALOG_TAG)
    }

    override fun getIconChannel(): Channel<Int> = currentIcon

}
