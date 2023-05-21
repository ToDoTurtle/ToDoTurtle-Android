package com.eps.todoturtle.soundpool

import android.content.Context
import android.media.SoundPool
import com.eps.todoturtle.R
import kotlin.properties.Delegates

class TaskCompletedPlayer(context: Context) {
    private var soundPool = SoundPool.Builder().setMaxStreams(1).build()
    private var isLoaded = false
    private var soundId by Delegates.notNull<Int>()

    init {
        soundPool.setOnLoadCompleteListener { _, _, _ -> isLoaded = true }
        soundId = soundPool.load(context, R.raw.task_completed, 1)
    }

    fun play() {
        if (isLoaded) {
            soundPool.play(soundId, 0.6f, 0.6f, 0, 0, 1f)
        }
    }

    fun destroy() = soundPool.release()
}
