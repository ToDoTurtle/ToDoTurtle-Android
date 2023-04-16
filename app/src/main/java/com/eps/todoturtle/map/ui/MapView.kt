package com.eps.todoturtle.map.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.views.MapView

@Composable
fun MapView(
    modifier: Modifier = Modifier,
    startLat: Double = 0.0,
    startLon: Double = 0.0,
    onLoad: ((map: MapView) -> Unit)? = null,
) {
    val mapViewState = rememberMapViewWithLifecycle(startLat, startLon)

    AndroidView({ mapViewState }, modifier = modifier) { mapView ->
        onLoad?.invoke(mapView)
    }
}