package com.eps.todoturtle.nfc.logic

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.nfc.FormatException
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.TagLostException
import android.nfc.tech.Ndef
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.IOException


class NfcWriteDevice private constructor(
    private val activity: Activity,
    private val message: NfcParcelable,
    private val nfcAdapter: NfcAdapter? = NfcAdapter.getDefaultAdapter(activity),
) : NfcAdapter.ReaderCallback, DefaultLifecycleObserver, BroadcastReceiver() {

    companion object Init {
        fun ComponentActivity.NfcWriteDevice(message: NfcParcelable): NfcWriteDevice {
            val device = NfcWriteDevice(this, message)
            lifecycle.addObserver(device)
            return device
        }
    }

    val operationResults =
        MutableStateFlow(initialStatus())

    private fun initialStatus(): WriteOperationStatus {
        return with(nfcAdapter) {
            when {
                this == null -> WriteOperationStatus.NFC_NOT_SUPPORTED
                !isEnabled -> WriteOperationStatus.NFC_NOT_ENABLED
                else -> WriteOperationStatus.PREPARED
            }
        }
    }

    override fun onTagDiscovered(tag: Tag?) {
        val resultOfWriteOperation = write(tag)
        operationResults.value = resultOfWriteOperation
    }

    private fun write(tag: Tag?): WriteOperationStatus {
        if (tag == null) return WriteOperationStatus.TAG_LOST
        return writeCorrectTag(tag)
    }

    private fun writeCorrectTag(tag: Tag): WriteOperationStatus {
        val ndef = Ndef.get(tag)
        if (!ndef.isWritable) return WriteOperationStatus.NOT_WRITABLE
        return writeWriteableTag(ndef)
    }

    private fun writeWriteableTag(ndefTag: Ndef): WriteOperationStatus {
        return try {
            ndefTag.write(message)
            WriteOperationStatus.SUCCESS
        } catch (_: FormatException) {
            WriteOperationStatus.MESSAGE_FORMAT_ERROR
        } catch (_: TagLostException) {
            WriteOperationStatus.TAG_LOST
        } catch (_: IOException) {
            WriteOperationStatus.UNKNOWN_ERROR
        }
    }

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
            options
        )
    }


    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        nfcAdapter?.disableReaderMode(activity)
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

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action
        if (action == NfcAdapter.ACTION_ADAPTER_STATE_CHANGED) {
            val state = intent.getIntExtra(
                NfcAdapter.EXTRA_ADAPTER_STATE,
                NfcAdapter.STATE_OFF
            )
            when (state) {
                NfcAdapter.STATE_OFF, NfcAdapter.STATE_TURNING_OFF -> {
                    operationResults.value = WriteOperationStatus.NFC_NOT_ENABLED
                }

                NfcAdapter.STATE_ON, NfcAdapter.STATE_TURNING_ON -> {
                    operationResults.value = WriteOperationStatus.PREPARED
                }
            }
        }
    }

}

fun Ndef.write(content: NfcParcelable) {
    connect()
    writeNdefMessage(content.toMessage())
    close()
}
