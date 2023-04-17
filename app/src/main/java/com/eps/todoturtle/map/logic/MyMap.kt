package com.eps.todoturtle.map.logic

import android.content.Context
import com.eps.todoturtle.R
import com.eps.todoturtle.map.logic.markers.MarkerFactory
import com.eps.todoturtle.map.logic.markers.MarkerFactory.setCurrentMarker
import com.eps.todoturtle.map.logic.markers.MarkerType
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay

object MyMap {
    fun getMap(context: Context, startLat: Double = 0.0, startLon: Double = 0.0, onMapClick: () -> Unit): MapView {
        return MapView(context).apply {
            setId()
            setOutlineClip()
            setTileSource()
            setZoom()
            setStartPoint(startLat, startLon)
            setMarkers(this, startLat, startLon)
            setListener(onMapClick)
        }
    }

    private fun MapView.setId() {
        id = R.id.map
    }

    private fun MapView.setOutlineClip() {
        clipToOutline = true
    }

    private fun MapView.setTileSource() {
        setTileSource(TileSourceFactory.MAPNIK)
    }

    private fun MapView.setZoom() {
        zoomController.setVisibility(CustomZoomButtonsController.Visibility.ALWAYS)
        controller.setZoom(20.00)
    }

    private fun MapView.setMarkers(map: MapView, startLat: Double, startLon: Double) {
        setCurrentPositionMarker(map, startLat, startLon)
        setCurrentMarker(map)
    }

    private fun MapView.setCurrentPositionMarker(map: MapView, startLat: Double, startLon: Double) {
        val marker = MarkerFactory.getMarker(map, MarkerType.CAR, startLat, startLon)
        overlays.add(marker)
    }

    private fun MapView.setStartPoint(startLat: Double, startLon: Double) {
        val eps = GeoPoint(startLat, startLon)
        controller.setCenter(eps)
    }

    private fun MapView.setListener(onMapClick: () -> Unit) {
        overlays.add(
            MapEventsOverlay(
                object : MapEventsReceiver {
                    override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
                        onMapClick()
                        return true
                    }

                    override fun longPressHelper(p: GeoPoint?): Boolean {
                        return false
                    }
                }
            )
        )
    }
}
