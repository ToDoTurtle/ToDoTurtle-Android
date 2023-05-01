package com.eps.todoturtle.nfc.logic.sensor

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import androidx.activity.ComponentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/*
* This class is responsible for receiving the NFC sensor changes.
* If the NFC sensor is turned on or off, the handler will be activated.
 */
class NfcChangesDetector private constructor(
    private val activity: Activity,
    private val onDisabled: () -> Unit = {},
    private val onEnabled: () -> Unit = {}
) : DefaultLifecycleObserver, BroadcastReceiver() {

    companion object {
        fun ComponentActivity.NfcBroadcastReceivr(
            onDisabled: () -> Unit = {},
            onEnabled: () -> Unit = {}
        ): NfcChangesDetector {
            val receiver = NfcChangesDetector(this, onDisabled, onEnabled)
            lifecycle.addObserver(receiver)
            return receiver
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action
        if (action == NfcAdapter.ACTION_ADAPTER_STATE_CHANGED) {
            val state = intent.getIntExtra(
                NfcAdapter.EXTRA_ADAPTER_STATE,
                NfcAdapter.STATE_OFF,
            )
            when (state) {
                NfcAdapter.STATE_OFF, NfcAdapter.STATE_TURNING_OFF -> onDisabled()
                NfcAdapter.STATE_ON, NfcAdapter.STATE_TURNING_ON -> onEnabled()
            }
        }
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        val filter = IntentFilter(NfcAdapter.ACTION_ADAPTER_STATE_CHANGED)
        activity.registerReceiver(this, filter)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        activity.unregisterReceiver(this)
    }

}