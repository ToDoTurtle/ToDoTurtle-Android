package com.eps.todoturtle.nfc.logic.write

import android.nfc.FormatException
import android.nfc.Tag
import android.nfc.TagLostException
import android.nfc.tech.Ndef
import com.eps.todoturtle.nfc.logic.NfcParcelable
import java.io.IOException

object NfcTagWriter {

    fun write(tag: Tag?, message: NfcParcelable): WriteOperation {
        if (tag == null) return WriteOperation.TagLost
        return writeCorrectTag(tag, message)
    }

    private fun writeCorrectTag(tag: Tag, message: NfcParcelable): WriteOperation {
        val ndef = Ndef.get(tag)
        if (!ndef.isWritable) return WriteOperation.NotWritable
        return writeWriteableTag(ndef, message)
    }

    private fun writeWriteableTag(ndefTag: Ndef, message: NfcParcelable): WriteOperation {
        return try {
            ndefTag.write(message)
            WriteOperation.Success(message)
        } catch (_: FormatException) {
            WriteOperation.MessageFormatError
        } catch (_: TagLostException) {
            WriteOperation.TagLost
        } catch (_: IOException) {
            WriteOperation.UnknownError
        }
    }
}

fun Ndef.write(content: NfcParcelable) {
    connect()
    writeNdefMessage(content.toMessage())
    close()
}
