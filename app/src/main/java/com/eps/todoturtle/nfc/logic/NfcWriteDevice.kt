package com.eps.todoturtle.nfc.logic

import android.app.Activity
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


class NfcWriteDevice private constructor (
    private val activity: Activity,
    private val message: NfcParcelable,
    private val nfcAdapter: NfcAdapter? = NfcAdapter.getDefaultAdapter(activity),
) : NfcAdapter.ReaderCallback, DefaultLifecycleObserver {

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
        if (nfcAdapter == null) return WriteOperationStatus.NFC_NOT_SUPPORTED
        if (!nfcAdapter.isEnabled) return WriteOperationStatus.NFC_NOT_ENABLED
        return WriteOperationStatus.PREPARED
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
        val options = Bundle().apply { putInt(NfcAdapter.EXTRA_READER_PRESENCE_CHECK_DELAY, 250) }
        nfcAdapter?.enableReaderMode(
            activity,
            this,
            NfcAdapter.FLAG_READER_NFC_A or NfcAdapter.FLAG_READER_NFC_B or NfcAdapter.FLAG_READER_NFC_F or NfcAdapter.FLAG_READER_NFC_V or NfcAdapter.FLAG_READER_NFC_BARCODE or NfcAdapter.FLAG_READER_NO_PLATFORM_SOUNDS,
            options
        )
    }


    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        nfcAdapter?.disableReaderMode(activity)
    }

}


fun Ndef.write(content: NfcParcelable) {
    connect()
    writeNdefMessage(content.toMessage())
    close()
}