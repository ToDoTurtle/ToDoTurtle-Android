package com.eps.todoturtle.map.logic

import android.content.Context
import android.preference.PreferenceManager
import com.eps.todoturtle.BuildConfig
import org.osmdroid.config.Configuration

class MapConfiguration {
    companion object MapConfiguration {
        fun setUpMapConfiguration(context: Context) {
//            loadConfiguration(context)
            setUserAgent()
        }

        private fun loadConfiguration(context: Context) {
            Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context))
        }

        private fun setUserAgent() {
            Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID
        }
    }
}
