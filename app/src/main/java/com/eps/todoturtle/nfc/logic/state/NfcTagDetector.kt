package com.eps.todoturtle.nfc.logic.state

import android.app.Activity
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/*
* This class is used to detect NFC tags once they are in the NFC sensor range.
* The callback will be called when the tag is detected.
 */
class NfcTagDetector private constructor(
    private val activity: Activity,
    private val nfcAdapter: NfcAdapter? = NfcAdapter.getDefaultAdapter(activity),
    private val callBack: (Tag?) -> Unit
) : NfcAdapter.ReaderCallback, DefaultLifecycleObserver {

    companion object {
        fun ComponentActivity.NfcTagDetector(callBack: (Tag?) -> Unit): NfcTagDetector {
            val reader = NfcTagDetector(this, callBack = callBack)
            lifecycle.addObserver(reader)
            return reader
        }
    }

    override fun onTagDiscovered(tag: Tag?) = callBack(tag)

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        val options = Bundle().apply {
            putInt(NfcAdapter.EXTRA_READER_PRESENCE_CHECK_DELAY, 250)
        }
        nfcAdapter?.enableReaderMode(
            activity,
            this,
            NfcAdapter.FLAG_READER_NFC_A or
                    NfcAdapter.FLAG_READER_NFC_B or
                    NfcAdapter.FLAG_READER_NFC_F or
                    NfcAdapter.FLAG_READER_NFC_V or
                    NfcAdapter.FLAG_READER_NFC_BARCODE or
                    NfcAdapter.FLAG_READER_NO_PLATFORM_SOUNDS,
            options,
        )
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        nfcAdapter?.disableReaderMode(activity)
    }

}