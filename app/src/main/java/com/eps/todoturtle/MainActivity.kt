package com.eps.todoturtle

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.eps.todoturtle.devices.infra.InMemoryDeviceRepository
import com.eps.todoturtle.devices.logic.DeviceIconActivity
import com.eps.todoturtle.devices.logic.DevicesViewModel.Companion.getDevicesViewModel
import com.eps.todoturtle.network.logic.ConnectionChecker
import com.eps.todoturtle.network.logic.ConnectionCheckerImpl
import com.eps.todoturtle.nfc.logic.NfcWriteViewModel.INIT.getNfcWriteModel
import com.eps.todoturtle.note.logic.NotesViewModel
import com.eps.todoturtle.note.logic.location.DefaultLocationClient
import com.eps.todoturtle.note.logic.location.LocationClient
import com.eps.todoturtle.note.logic.location.hasLocationPermision
import com.eps.todoturtle.permissions.logic.PermissionRequester
import com.eps.todoturtle.permissions.logic.providers.CameraPermissionProvider
import com.eps.todoturtle.permissions.logic.providers.CoarseLocationPermissionProvider
import com.eps.todoturtle.permissions.logic.providers.FineLocationPermissionProvider
import com.eps.todoturtle.profile.logic.ProfileViewModel
import com.eps.todoturtle.profile.logic.UserAuth
import com.eps.todoturtle.shared.logic.extensions.dataStore
import com.eps.todoturtle.shared.logic.extensions.hasCameraPermission
import com.eps.todoturtle.ui.App
import com.eps.todoturtle.ui.theme.ToDoTurtleTheme
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.maltaisn.icondialog.IconDialog
import com.maltaisn.icondialog.IconDialogSettings
import com.maltaisn.icondialog.data.Icon
import com.maltaisn.icondialog.pack.IconDrawableLoader
import com.maltaisn.icondialog.pack.IconPack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity(), IconDialog.Callback, DeviceIconActivity {
    private lateinit var cameraPermissionRequester: PermissionRequester
    private lateinit var locationPermissionRequester: PermissionRequester

    private val currentIcon: Channel<Int> = Channel(UNLIMITED)
    private lateinit var iconDialog: IconDialog

    private lateinit var auth: FirebaseAuth
    private lateinit var locationClient: LocationClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val connectionChecker = ConnectionCheckerImpl(this)
        val flow = connectionChecker.networkAvailability

        cameraPermissionRequester =
            PermissionRequester(this, listOf(CameraPermissionProvider(this)))
        locationPermissionRequester =
            PermissionRequester(
                this,
                listOf(
                    FineLocationPermissionProvider(this),
                    CoarseLocationPermissionProvider(this),
                ),
            )
        val noteScreenNoteViewModel: NotesViewModel by viewModels { NotesViewModel.NoteScreenFactory }
        val deviceScreenNoteViewModel: NotesViewModel by viewModels { NotesViewModel.DeviceScreenFactory }
        val profileViewModel = ProfileViewModel(this)

        iconDialog = supportFragmentManager.findFragmentByTag(ICON_DIALOG_TAG) as IconDialog?
            ?: IconDialog.newInstance(IconDialogSettings())
        theme.applyStyle(R.style.AppTheme, true)

        auth = Firebase.auth
        val userAuth = UserAuth(auth)

        locationClient = DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext),
        )

        setContent {
            ToDoTurtleTheme(dataStore) {
                App(
                    hasLocationPermision = { hasLocationPermision() },
                    locationClient = locationClient,
                    locationPermissionRequester = locationPermissionRequester,
                    cameraPermissionRequester = cameraPermissionRequester,
                    devicesViewModel = getDevicesViewModel(InMemoryDeviceRepository()),
                    noteScreenViewModel = noteScreenNoteViewModel,
                    deviceScreenNoteViewModel = deviceScreenNoteViewModel,
                    nfcWriteViewModel = getNfcWriteModel(),
                    profileViewModel = profileViewModel,
                    dataStore = dataStore,
                    hasCameraPermission = { hasCameraPermission() },
                    userAuth = userAuth,
                    connectionChecker = flow,
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
