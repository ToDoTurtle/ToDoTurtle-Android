package com.eps.todoturtle.nfc.logic.sensor

import android.nfc.FormatException
import android.nfc.Tag
import android.nfc.TagLostException
import android.nfc.tech.Ndef
import com.eps.todoturtle.nfc.logic.NfcParcelable
import com.eps.todoturtle.nfc.logic.WriteOperationStatus
import java.io.IOException


object NfcTagWriter {

    fun write(tag: Tag?, message: NfcParcelable): WriteOperationStatus {
        if (tag == null) return WriteOperationStatus.TAG_LOST
        return writeCorrectTag(tag, message)
    }

    private fun writeCorrectTag(tag: Tag, message: NfcParcelable): WriteOperationStatus {
        val ndef = Ndef.get(tag)
        if (!ndef.isWritable) return WriteOperationStatus.NOT_WRITABLE
        return writeWriteableTag(ndef, message)
    }

    private fun writeWriteableTag(ndefTag: Ndef, message: NfcParcelable): WriteOperationStatus {
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

}

fun Ndef.write(content: NfcParcelable) {
    connect()
    writeNdefMessage(content.toMessage())
    close()
}
