package com.eps.todoturtle

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.eps.todoturtle.action.infra.FirebaseActionRepository
import com.eps.todoturtle.action.logic.ActionViewModel.Companion.getActionViewModel
import com.eps.todoturtle.devices.infra.FirebaseDeviceRepository
import com.eps.todoturtle.devices.logic.DeviceIconActivity
import com.eps.todoturtle.devices.logic.DevicesViewModel.Companion.getDevicesViewModel
import com.eps.todoturtle.network.logic.ConnectionChecker
import com.eps.todoturtle.network.logic.ConnectionCheckerImpl
import com.eps.todoturtle.network.logic.NetworkAvailability
import com.eps.todoturtle.network.ui.NetworkWarningDialog
import com.eps.todoturtle.nfc.logic.NfcWriteViewModel.INIT.getNfcWriteModel
import com.eps.todoturtle.note.logic.NotesViewModel
import com.eps.todoturtle.note.logic.NotesViewModel.Companion.getNoteScreenViewModel
import com.eps.todoturtle.note.logic.location.DefaultLocationClient
import com.eps.todoturtle.note.logic.location.LocationClient
import com.eps.todoturtle.note.logic.location.hasLocationPermission
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity(), IconDialog.Callback, DeviceIconActivity {
    private lateinit var cameraPermissionRequester: PermissionRequester
    private lateinit var locationPermissionRequester: PermissionRequester

    private val currentIcon: Channel<Int> = Channel(UNLIMITED)
    private lateinit var iconDialog: IconDialog

    private lateinit var auth: FirebaseAuth
    private lateinit var locationClient: LocationClient

    private lateinit var connectionChecker: ConnectionChecker
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        connectionChecker = ConnectionCheckerImpl(this)
        val connectionAvailability = connectionChecker.networkAvailability

        auth = Firebase.auth
        val userAuth = UserAuth(this@MainActivity, auth)

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
        val notesViewModel = getNoteScreenViewModel(onNotificationError = {
            runOnUiThread {
                Toast.makeText(
                    this,
                    resources.getString(R.string.error_invalid_notification_time),
                    Toast.LENGTH_LONG,
                ).show()
            }
        }, onDeadlineError = {
            runOnUiThread {
                Toast.makeText(
                    this,
                    resources.getString(R.string.error_invalid_deadline_time),
                    Toast.LENGTH_LONG,
                ).show()
            }
        })
        val actionsRepository = FirebaseActionRepository()
        val actionsViewModel = getActionViewModel(actionsRepository)
        val profileViewModel = ProfileViewModel(this, userAuth.getUid())
        val devicesViewModel = getDevicesViewModel(FirebaseDeviceRepository(), actionsRepository)

        iconDialog = supportFragmentManager.findFragmentByTag(ICON_DIALOG_TAG) as IconDialog?
            ?: IconDialog.newInstance(IconDialogSettings())
        theme.applyStyle(R.style.AppTheme, true)

        locationClient = DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext),
        )

        setContent {
            ToDoTurtleTheme(dataStore) {
                var shouldShowNetworkDialog by rememberSaveable { mutableStateOf(false) }
                val networkAvailability by connectionAvailability.collectAsStateWithLifecycle(
                    NetworkAvailability.AVAILABLE)
                shouldShowNetworkDialog = networkAvailability != NetworkAvailability.AVAILABLE

                NetworkWarningDialog(
                    showDialog = shouldShowNetworkDialog,
                    reason = R.string.app_requires_internet,
                    onSettingsClick = ::onGoToSettingsClick,
                    onSecondaryButtonClick = ::onCloseAppClick,
                    secondaryButtonText = R.string.close_app,
                    onDismiss = {},
                )
                App(
                    devicesViewModel = devicesViewModel,
                    hasLocationPermision = { hasLocationPermission() },
                    locationClient = locationClient,
                    locationPermissionRequester = locationPermissionRequester,
                    cameraPermissionRequester = cameraPermissionRequester,
                    notesViewModel = notesViewModel,
                    actionsViewModel = actionsViewModel,
                    nfcWriteViewModel = getNfcWriteModel(),
                    profileViewModel = profileViewModel,
                    dataStore = dataStore,
                    hasCameraPermission = { hasCameraPermission() },
                    userAuth = userAuth,
                    reloadActivity = ::reloadActivity,
                    context = applicationContext,
                )
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

    override fun onResume() {
        super.onResume()
        connectionChecker.updateFlows()
    }

    private fun onGoToSettingsClick() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun onCloseAppClick() {
        finish()
    }

    private fun reloadActivity() {
        val intent = Intent(this, MainActivity::class.java)
        finish()
        startActivity(intent)
    }
}
