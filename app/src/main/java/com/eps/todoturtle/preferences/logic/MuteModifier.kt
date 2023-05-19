package com.eps.todoturtle.preferences.logic

import android.app.NotificationManager
import android.content.Context
import android.media.AudioManager
import android.os.Build
import android.widget.Toast

class MuteModifier(private val context: Context) {
    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun updateMute(mute: Boolean) {
        if (mute) setSystemMute() else setSystemUnMute()
    }

    private fun setSystemMute() {
        audioManager.adjustStreamVolume(
            AudioManager.STREAM_NOTIFICATION,
            AudioManager.ADJUST_MUTE,
            0,
        )
        audioManager.adjustStreamVolume(AudioManager.STREAM_ALARM, AudioManager.ADJUST_MUTE, 0)
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, 0)
        audioManager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_MUTE, 0)
        audioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_MUTE, 0)
    }

    private fun setSystemUnMute() {
        audioManager.adjustStreamVolume(
            AudioManager.STREAM_NOTIFICATION,
            AudioManager.ADJUST_UNMUTE,
            0,
        )
        audioManager.adjustStreamVolume(AudioManager.STREAM_ALARM, AudioManager.ADJUST_UNMUTE, 0)
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE, 0)
        audioManager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_UNMUTE, 0)
        audioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_UNMUTE, 0)
    }

    fun notifyIfSystemInDNDMode() {
        Toast.makeText(
            context,
            "Please turn off DND mode to receive notifications",
            Toast.LENGTH_LONG,
        ).show()
    }

    fun systemInDNDMode(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return (notificationManager.currentInterruptionFilter != NotificationManager.INTERRUPTION_FILTER_ALL)
        }

        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        return audioManager.ringerMode == AudioManager.RINGER_MODE_SILENT &&
            notificationManager.currentInterruptionFilter != NotificationManager.INTERRUPTION_FILTER_ALL
    }
}
