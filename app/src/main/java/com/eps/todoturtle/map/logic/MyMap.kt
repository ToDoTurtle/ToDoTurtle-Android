package com.eps.todoturtle.map.logic

import android.content.Context
import com.eps.todoturtle.R
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class MyMap {
    companion object MyMap {
        fun getMap(context: Context, startLat: Double = 0.0, startLon: Double = 0.0): MapView {
            return MapView(context).apply {
                setId()
                setOutlineClip()
                setTileSource()
                setZoom()
                setStartPoint(startLat, startLon)
                setMarkers(this)
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

        private fun setMarkers(map: MapView) {
            setCurrentMarker(map)
        }

        private fun setCurrentMarker(map: MapView) {
            val overlay = MyLocationNewOverlay(map).apply {
                enableMyLocation()
                enableFollowLocation()
            }
            map.overlays.add(overlay)
        }


        private fun MapView.setStartPoint(startLat: Double, startLon: Double) {
            val eps = GeoPoint(startLat, startLon)
            controller.setCenter(eps)
        }
    }
}
