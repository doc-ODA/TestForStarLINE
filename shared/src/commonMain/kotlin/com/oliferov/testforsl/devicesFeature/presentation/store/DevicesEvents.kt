package com.oliferov.testforsl.devicesFeature.presentation.store

import com.oliferov.testforsl.core.Events
import com.oliferov.testforsl.devicesFeature.domain.model.CarDeviceDomain

sealed class DevicesEvents: Events{
    data class SelectedDevice(val device: CarDeviceDomain):DevicesEvents()
}