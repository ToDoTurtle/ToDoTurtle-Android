package com.eps.todoturtle.map.logic

import com.eps.todoturtle.BuildConfig
import org.osmdroid.config.Configuration

object MapConfiguration {
    fun setUpMapConfiguration() {
        setUserAgent()
    }

    private fun setUserAgent() {
        Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID
    }
}
