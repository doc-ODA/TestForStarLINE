package com.oliferov.testforsl.devicesFeature.domain.model

data class GroupDeviceDomain(
    val parent: String,
    val group: String,
    val title: String,
    val cars: MutableList<CarDeviceDomain>
): DeviceDomain
