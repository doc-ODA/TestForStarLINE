package com.oliferov.testforsl.devicesFeature.presentation.store

import com.oliferov.testforsl.core.Actions
import com.oliferov.testforsl.devicesFeature.domain.model.CarDeviceDomain
import com.oliferov.testforsl.devicesFeature.domain.model.GroupDeviceDomain

sealed class DevicesActions : Actions {
    data class SelectedDevice(val devaice: CarDeviceDomain) : DevicesActions()

    data object LoadDevices : DevicesActions()
    data class DevicesChanged(val devices: List<GroupDeviceDomain>) : DevicesActions()

}