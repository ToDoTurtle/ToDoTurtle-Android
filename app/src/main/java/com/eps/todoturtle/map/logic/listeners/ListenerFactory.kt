package com.eps.todoturtle.map.logic.listeners

import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.MapEventsOverlay

object ListenerFactory {
    fun getListenerOverlay(onClick: () -> Unit) = MapEventsOverlay(getSingleClickListener(onClick))

    private fun getSingleClickListener(onClick: () -> Unit) = object : MapEventsReceiver {
        override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
            onClick()
            return true
        }

        override fun longPressHelper(p: GeoPoint?): Boolean {
            return false
        }
    }
}
