package com.eps.todoturtle.note.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.eps.todoturtle.note.logic.location.rememberMapViewWithLifecycle
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker


@Composable
fun LocationPickerMapView(
    modifier: Modifier = Modifier,
    onLoad: ((map: MapView) -> Unit)? = null
) {
    val mapViewState = rememberMapViewWithLifecycle()

    AndroidView(
        { mapViewState },
        modifier
    ) { mapView -> onLoad?.invoke(mapView) }
}

@Composable
fun LocationPicker(
    modifier: Modifier = Modifier,
    source: GeoPoint?,
) {
    LocationPickerMapView(
        modifier = modifier
            .padding(16.dp)
            .shadow(
                elevation = 16.dp,
                shape = MaterialTheme.shapes.medium,
            ),
    ) {
        it.controller.apply {
            setZoom(15.0)
            animateTo(source, 18.0, 1000L)
        }
        if (source != null) it.addMarker(source)
    }
}

fun MapView.addMarker(geoPoint: GeoPoint) {
    val marker = Marker(this)
    marker.position = geoPoint
    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
    this.overlays.add(marker)
}
