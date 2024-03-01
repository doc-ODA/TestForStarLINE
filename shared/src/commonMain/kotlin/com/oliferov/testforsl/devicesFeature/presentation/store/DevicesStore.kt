package com.oliferov.testforsl.devicesFeature.presentation.store

import com.oliferov.testforsl.core.BaseStore
import com.oliferov.testforsl.devicesFeature.domain.usecase.FetchDevicesUseCase
import org.koin.core.component.KoinComponent

class DevicesStore(
    fetchDevicesUseCase: FetchDevicesUseCase
) : BaseStore<DevicesState, DevicesActions, DevicesEffects, DevicesEvents>(
    initialState = DevicesState(),
    updater = DevicesUpdater(),
    initialEffects = setOf(DevicesEffects.FetchDevices),
    processor = DevicesProcessor(fetchDevicesUseCase = fetchDevicesUseCase)
), KoinComponent