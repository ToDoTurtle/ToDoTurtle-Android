package com.eps.todoturtle.map.logic.markers

import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

object MyMarker {
    fun getMarkerFrom(
        map: MapView,
        markerDetails: MarkerDetails,
    ) = Marker(map).apply {
        position = GeoPoint(markerDetails.latitude, markerDetails.longitude)
        title = markerDetails.title
        snippet = markerDetails.snippet
        icon = markerDetails.icon
    }
}
