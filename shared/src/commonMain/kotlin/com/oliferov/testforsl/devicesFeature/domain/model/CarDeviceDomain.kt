package com.oliferov.testforsl.devicesFeature.domain.model

data class CarDeviceDomain(
    val parent: String,
    val type: String,
    val title: String,
    val lat: Double,
    val lon: Double
): DeviceDomain
