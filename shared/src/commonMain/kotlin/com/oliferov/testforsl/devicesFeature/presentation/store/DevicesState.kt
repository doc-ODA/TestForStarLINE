package com.oliferov.testforsl.devicesFeature.presentation.store

import com.oliferov.testforsl.core.State
import com.oliferov.testforsl.devicesFeature.domain.model.CarDeviceDomain
import com.oliferov.testforsl.devicesFeature.domain.model.GroupDeviceDomain

data class DevicesState(
    val isLoading: Boolean = true,
    val devaces: List<GroupDeviceDomain> = emptyList(),
    val selectedDevice: CarDeviceDomain? = null
): State
