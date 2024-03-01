package com.oliferov.testforsl.devicesFeature.data.pojo

import kotlinx.serialization.Serializable

@Serializable
data class DevicesResponseCloudResult(
    val devices: DevicesCloudResult
)