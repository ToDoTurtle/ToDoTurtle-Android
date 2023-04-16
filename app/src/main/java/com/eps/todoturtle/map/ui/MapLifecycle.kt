package com.eps.todoturtle.map.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.eps.todoturtle.map.logic.MapConfiguration
import com.eps.todoturtle.map.logic.MyMap
import org.osmdroid.views.MapView

@Composable
fun rememberMapViewWithLifecycle(
    startLat: Double = 0.0,
    startLon: Double = 0.0,
): MapView {
    val context = LocalContext.current
    MapConfiguration.setUpMapConfiguration(context)
    val mapView = remember {
        MyMap.getMap(context, startLat, startLon)
    }

    val lifecycleObserver = rememberMapLifecycleObserver(mapView)
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle) {
        lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }
    return mapView
}

@Composable
fun rememberMapLifecycleObserver(mapView: MapView): LifecycleEventObserver {
    return remember(mapView) {
        LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                else -> Unit
            }
        }
    }
}
