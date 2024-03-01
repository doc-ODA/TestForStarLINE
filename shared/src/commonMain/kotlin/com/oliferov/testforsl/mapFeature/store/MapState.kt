package com.oliferov.testforsl.mapFeature.store

import com.oliferov.testforsl.core.State
import com.oliferov.testforsl.devicesFeature.domain.model.CarDeviceDomain
import com.oliferov.testforsl.devicesFeature.domain.model.GroupDeviceDomain

data class MapState(
    val center: Pair<Double,Double>? = null,
    val marker: Pair<Double,Double>? = null,
    val zoom: Double = 4.0,
    val title: String = ""
): State
