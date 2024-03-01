package com.oliferov.testforsl.devicesFeature.data.pojo

import kotlinx.serialization.Serializable

@Serializable
data class DeviceCloudResult (
    val parent: String,
    val title: String,
    val group: String? = null,
    val type: String? = null,
    val lat: Double? = null,
    val lon: Double? = null
)



