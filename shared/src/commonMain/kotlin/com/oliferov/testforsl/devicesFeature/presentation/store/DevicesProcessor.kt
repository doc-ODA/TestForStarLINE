package com.oliferov.testforsl.devicesFeature.presentation.store

import com.oliferov.testforsl.core.Processor
import com.oliferov.testforsl.devicesFeature.domain.usecase.FetchDevicesUseCase

class DevicesProcessor(
    private val fetchDevicesUseCase: FetchDevicesUseCase
) : Processor<DevicesActions, DevicesEffects> {
    override suspend fun process(effect: DevicesEffects) = when (effect) {
        is DevicesEffects.FetchDevices -> fetchDevices()
    }

    private suspend fun fetchDevices(): DevicesActions {
        val devices = fetchDevicesUseCase()
        return DevicesActions.DevicesChanged(devices)
    }
}