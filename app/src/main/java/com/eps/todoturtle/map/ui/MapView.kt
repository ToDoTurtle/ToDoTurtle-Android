package com.eps.todoturtle.map.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.eps.todoturtle.map.ui.rememberMapViewWithLifecycle
import org.osmdroid.views.MapView

@Composable
fun MapView(
    modifier: Modifier = Modifier,
    onLoad: ((map: MapView) -> Unit)? = null,
) {
    val mapViewState = rememberMapViewWithLifecycle()

    AndroidView({ mapViewState }, modifier = modifier) { mapView ->
        onLoad?.invoke(mapView)
    }
}
