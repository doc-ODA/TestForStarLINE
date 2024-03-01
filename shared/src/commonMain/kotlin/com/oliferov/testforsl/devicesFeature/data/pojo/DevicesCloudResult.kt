package com.oliferov.testforsl.devicesFeature.data.pojo

import kotlinx.serialization.Serializable

@Serializable
data class DevicesCloudResult(
    val list: List<DeviceCloudResult>
)