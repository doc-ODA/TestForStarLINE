package com.oliferov.testforsl.mapFeature.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import com.oliferov.testforsl.MapLibreView
import com.oliferov.testforsl.core.Actions
import com.oliferov.testforsl.devicesFeature.presentation.store.DevicesStore
import com.oliferov.testforsl.mapFeature.store.MapActions
import com.oliferov.testforsl.mapFeature.store.MapStore
import org.koin.compose.koinInject

class MapScreen(val title: String, val lat: Double, val lon: Double) : Screen {


    @Composable
    override fun Content() {
        val store: MapStore = koinInject()
        val uiState by store.observeState().collectAsState()
        store.handle(MapActions.FetchMarker(title, lat, lon))

        Box(modifier = Modifier.fillMaxSize()) {
            MapLibreView(
                marker = Pair(lat, lon),
                centre = uiState.center,
                title = title,
                zoom = uiState.zoom,
                zoomCallback = {
                    store.handle(MapActions.FetchZoom(it))
                },
                centreCallback = {
                    store.handle(MapActions.FetchCentre(it))
                }
            )
        }
    }
}

