package com.eps.todoturtle

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.eps.todoturtle.action.infra.InMemoryActionRepository
import com.eps.todoturtle.action.logic.ActionViewModel.Companion.getActionViewModel
import com.eps.todoturtle.devices.infra.FirebaseDeviceRepository
import com.eps.todoturtle.devices.infra.InMemoryDeviceRepository
import com.eps.todoturtle.devices.logic.DeviceIconActivity
import com.eps.todoturtle.devices.logic.DevicesViewModel.Companion.getDevicesViewModel
import com.eps.todoturtle.nfc.logic.NfcWriteViewModel.INIT.getNfcWriteModel
import com.eps.todoturtle.note.logic.NotesViewModel
import com.eps.todoturtle.permissions.logic.PermissionRequester
import com.eps.todoturtle.permissions.logic.providers.CameraPermissionProvider
import com.eps.todoturtle.profile.logic.ProfileViewModel
import com.eps.todoturtle.profile.logic.UserAuth
import com.eps.todoturtle.shared.logic.extensions.dataStore
import com.eps.todoturtle.shared.logic.extensions.hasCameraPermission
import com.eps.todoturtle.ui.App
import com.eps.todoturtle.ui.theme.ToDoTurtleTheme
import com.google.firebase.auth.FirebaseAuth
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

class MainActivity : AppCompatActivity(), IconDialog.Callback, DeviceIconActivity {
    private val permissionsToRequest = listOf(CameraPermissionProvider(this))
    private lateinit var permissionRequester: PermissionRequester

    private val currentIcon: Channel<Int> = Channel(UNLIMITED)
    private lateinit var iconDialog: IconDialog

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionRequester = PermissionRequester(this, permissionsToRequest)
        val noteScreenNoteViewModel: NotesViewModel by viewModels { NotesViewModel.NoteScreenFactory }
        val actionsViewModel = getActionViewModel(InMemoryActionRepository())
        val profileViewModel = ProfileViewModel(this)

        iconDialog = supportFragmentManager.findFragmentByTag(ICON_DIALOG_TAG) as IconDialog?
            ?: IconDialog.newInstance(IconDialogSettings())
        theme.applyStyle(R.style.AppTheme, true)

        auth = Firebase.auth
        val userAuth = UserAuth(auth)

        setContent {
            ToDoTurtleTheme(dataStore) {
                App(
                    permissionRequester = permissionRequester,
                    devicesViewModel = getDevicesViewModel(FirebaseDeviceRepository()),
                    noteScreenViewModel = noteScreenNoteViewModel,
                    actionsViewModel = actionsViewModel,
                    nfcWriteViewModel = getNfcWriteModel(),
                    profileViewModel = profileViewModel,
                    dataStore = dataStore,
                    hasCameraPermission = { hasCameraPermission() },
                    userAuth = userAuth,
                )
            }
        }
    }

    override val iconDialogIconPack: IconPack?
        get() = (application as App).iconPack

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
