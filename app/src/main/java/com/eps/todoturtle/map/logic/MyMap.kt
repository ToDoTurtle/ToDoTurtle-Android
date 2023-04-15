package com.eps.todoturtle.map.logic

import android.content.Context
import com.eps.todoturtle.R
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView

class MyMap {
    companion object MyMap {
        fun getMap(context: Context): MapView {
            return MapView(context).apply {
                setId()
                setOutlineClip()
                setTileSource()
                setZoom()
                setStartPoint()
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

        private fun MapView.setStartPoint() {
            TODO()
//            val eps = GeoPoint(EPS.latitude, EPS.longitude)
//            controller.setCenter(eps)
        }
    }
}
