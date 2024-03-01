package com.oliferov.testforsl

import android.graphics.Bitmap
import android.graphics.Picture
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Icon
import android.graphics.drawable.VectorDrawable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.annotations.IconFactory
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style

@Composable
actual fun MapLibreView(
    marker: Pair<Double, Double>,
    centre: Pair<Double, Double>?,
    title: String,
    zoom: Double,
    zoomCallback: (Double) -> Unit,
    centreCallback: (Pair<Double, Double>) -> Unit
) {
    SideEffect {
        print(centre.toString())
    }
    val context = LocalContext.current
    Mapbox.getInstance(context)

    var mapRef: MapboxMap? by remember { mutableStateOf(null) }
    var styleRef: Style? by remember { mutableStateOf(null) }

    LaunchedEffect(mapRef) {
        val map = mapRef ?: return@LaunchedEffect
        map.setStyle(STYLE)
        map.getStyle { styleRef = it }
        map.addOnCameraMoveListener {
            val centerCamera = map.cameraPosition.target
            centerCamera?.let {
                centreCallback(Pair(it.latitude, it.longitude))
            }
            zoomCallback(map.cameraPosition.zoom)
        }
    }
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            MapView(context).also { view ->
                view.getMapAsync { map ->
                    mapRef = map
                    map.cameraPosition =
                        CameraPosition.Builder().target(
                            LatLng(
                                centre?.first ?: marker.first,
                                centre?.second ?: marker.second
                            )
                        ).zoom(zoom).build()
                    map.addMarker(
                        MarkerOptions()
                            .position(LatLng(marker.first, marker.second))
                            .title(title)
                            .icon(
                                IconFactory.getInstance(context)
                                    .fromBitmap(
                                        (context.resources.getDrawable(
                                            R.drawable.car_icon,
                                            null
                                        ) as VectorDrawable).toBitmap()
                                    )
                            )
                    )
                }
            }.apply { manageLifecycle() }
        },
        update = { _ -> },
    )
}

private fun MapView.manageLifecycle() {
    val observer = LifecycleEventObserver { _, event ->
        when (event) {
            Lifecycle.Event.ON_CREATE -> onCreate(null)
            Lifecycle.Event.ON_START -> onStart()
            Lifecycle.Event.ON_RESUME -> onResume()
            Lifecycle.Event.ON_PAUSE -> onPause()
            Lifecycle.Event.ON_STOP -> onStop()
            Lifecycle.Event.ON_DESTROY -> onDestroy()
            Lifecycle.Event.ON_ANY -> {
                /* No-op */
            }
        }
    }
}

private const val STYLE = "https://maps.starline.ru/mapstyles/default/style.json"