package com.eps.todoturtle.map.logic.markers

import android.content.Context
import android.util.Log
import com.eps.todoturtle.R
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

object MarkerFactory {
    fun getMarker(map: MapView, markerType: MarkerType, latitude: Double, longitude: Double): Marker {
        return MyMarker.getMarkerFrom(map,
            when (markerType) {
                MarkerType.CAR -> getCarDetails(map.context, latitude, longitude)
            })
    }

    fun setCurrentMarker(map: MapView) {
        val overlay = MyLocationNewOverlay(map).apply {
            enableMyLocation()
            enableFollowLocation()
        }
        map.overlays.add(overlay)
    }


    private fun getCarDetails(context: Context, latitude: Double, longitude: Double): MarkerDetails {
        return MarkerDetails(
            context,
            latitude,
            longitude,
            R.string.marker_car_title,
            R.string.marker_car_snippet,
            R.drawable.car,
        )
    }
}