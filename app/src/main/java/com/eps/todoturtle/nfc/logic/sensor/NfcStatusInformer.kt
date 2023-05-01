package com.eps.todoturtle.nfc.logic.sensor

import android.nfc.NfcAdapter
import androidx.activity.ComponentActivity
import com.eps.todoturtle.nfc.logic.NfcStatus
import com.eps.todoturtle.nfc.logic.sensor.NfcChangesDetector.Companion.NfcBroadcastReceivr
import kotlinx.coroutines.flow.MutableStateFlow

class NfcStatusInformer(
    activity: ComponentActivity,
    nfcAdapter: NfcAdapter? = NfcAdapter.getDefaultAdapter(activity),
) {

    init {
        activity.NfcBroadcastReceivr(
            onDisabled = { nfcStatus.value = NfcStatus.NFC_NOT_ENABLED },
            onEnabled = { nfcStatus.value = NfcStatus.NFC_WORKING },
        )
    }

    val nfcStatus: MutableStateFlow<NfcStatus> = MutableStateFlow(nfcAdapter.status)

}

val NfcAdapter?.status: NfcStatus
    get() = with(this) {
        when {
            this == null -> NfcStatus.NFC_NOT_SUPPORTED
            !isEnabled -> NfcStatus.NFC_NOT_ENABLED
            else -> NfcStatus.NFC_WORKING
        }
    }
