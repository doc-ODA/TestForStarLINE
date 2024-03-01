package com.oliferov.testforsl.mapFeature.store

import com.oliferov.testforsl.core.StateHolder
import com.oliferov.testforsl.core.StateUpdater
import com.oliferov.testforsl.devicesFeature.presentation.store.DevicesActions
import com.oliferov.testforsl.devicesFeature.presentation.store.DevicesEffects
import com.oliferov.testforsl.devicesFeature.presentation.store.DevicesEvents
import com.oliferov.testforsl.devicesFeature.presentation.store.DevicesState

typealias NewState = StateHolder<MapState, MapEffects, MapEvents>

class MapUpdater : StateUpdater<MapState, MapActions, MapEffects, MapEvents> {
    override fun update(
        action: MapActions,
        currentState: MapState
    ): StateHolder<MapState, MapEffects, MapEvents> {
        return when (action) {
            is MapActions.FetchMarker -> fetchMarker(action, currentState)
            is MapActions.FetchCentre -> fetchCentre(action, currentState)
            is MapActions.FetchZoom -> fetchZoom(action, currentState)
        }
    }

    private fun fetchMarker(
        action: MapActions.FetchMarker,
        currentState: MapState
    ): NewState =
        if (currentState.marker != Pair(action.lat, action.lon)) {
            StateHolder(
                state = currentState.copy(
                    center = null,
                    marker = Pair(action.lat, action.lon),
                    title = action.title
                )
            )
        } else {
            StateHolder(state = currentState)
        }

    private fun fetchCentre(
        action: MapActions.FetchCentre,
        currentState: MapState
    ): NewState =
        StateHolder(
            state = currentState.copy(
                center = action.centre,
            )
        )

    private fun fetchZoom(
        action: MapActions.FetchZoom,
        currentState: MapState
    ): NewState =
        StateHolder(
            state = currentState.copy(
                zoom = action.zoom,
            )
        )
}