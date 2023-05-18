package com.eps.todoturtle.nfc

import android.content.Intent.FLAG_ACTIVITY_NO_ANIMATION
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.eps.todoturtle.IconApp
import com.eps.todoturtle.shared.logic.extensions.dataStore
import com.eps.todoturtle.ui.theme.ToDoTurtleTheme
import com.maltaisn.icondialog.IconDialog
import com.maltaisn.icondialog.data.Icon
import com.maltaisn.icondialog.pack.IconPack
import kotlinx.coroutines.flow.MutableStateFlow

class WriteToDeviceActivity : AppCompatActivity(), IconDialog.Callback {

    private val callBackIcons: MutableStateFlow<Int?> = MutableStateFlow(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val iconDialog = supportFragmentManager.findFragmentByTag(ICON_DIALOG_TAG) as IconDialog?
//            ?: IconDialog.newInstance(IconDialogSettings())
//        val viewModel = getNfcWriteModel()
        setContent {
            ToDoTurtleTheme(LocalContext.current.dataStore) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
//                    WriteDevice(
//                        viewModel = viewModel,
//                        onTagNotWriteable = ::restartActivity,
//                        onTagLost = ::restartActivity,
//                        unknownError = ::restartActivity,
//                        onNfcNotSupported = {
//                            Toast.makeText(
//                                this,
//                                getString(R.string.nfc_not_supported_solution),
//                                Toast.LENGTH_SHORT,
//                            ).show()
//                            finish()
//                        },
//                        onWriteSuccessful = {
//                            Toast.makeText(
//                                this,
//                                getString(R.string.nfc_write_success),
//                                Toast.LENGTH_SHORT,
//                            )
//                                .show()
//                            finish()
//                        },
//                    )
                }
            }
        }
    }

    private fun restartActivity() {
        finish()
        startActivity(
            intent.apply {
                addFlags(FLAG_ACTIVITY_NO_ANIMATION)
            },
        )
    }

    override val iconDialogIconPack: IconPack?
        get() = (application as IconApp).iconPack

    override fun onIconDialogIconsSelected(dialog: IconDialog, icons: List<Icon>) {
        this.callBackIcons.value = icons.last().id
        Toast.makeText(this, "Icon selected ${icons.last().pathData}", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val ICON_DIALOG_TAG = "icon-dialog"
    }
}
