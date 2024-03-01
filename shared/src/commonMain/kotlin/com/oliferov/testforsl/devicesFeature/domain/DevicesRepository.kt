package com.oliferov.testforsl.devicesFeature.domain

import com.oliferov.testforsl.devicesFeature.domain.model.DeviceDomain
import com.oliferov.testforsl.devicesFeature.domain.model.GroupDeviceDomain

interface DevicesRepository {
    suspend fun fetchDevices(): List<GroupDeviceDomain>
}