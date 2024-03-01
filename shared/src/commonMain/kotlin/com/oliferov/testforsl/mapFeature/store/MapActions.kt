package com.oliferov.testforsl.mapFeature.store

import com.oliferov.testforsl.core.Actions
import com.oliferov.testforsl.devicesFeature.domain.model.CarDeviceDomain
import com.oliferov.testforsl.devicesFeature.domain.model.GroupDeviceDomain

sealed class MapActions : Actions{
    data class FetchMarker(val title: String, val lat: Double, val lon: Double): MapActions()
    data class FetchCentre(val centre: Pair<Double, Double>?): MapActions()
    data class FetchZoom(val zoom: Double): MapActions()

}