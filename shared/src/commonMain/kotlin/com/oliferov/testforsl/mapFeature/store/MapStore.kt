package com.oliferov.testforsl.mapFeature.store

import com.oliferov.testforsl.core.BaseStore
import com.oliferov.testforsl.devicesFeature.domain.usecase.FetchDevicesUseCase
import org.koin.core.component.KoinComponent

class MapStore: BaseStore<MapState, MapActions, MapEffects, MapEvents>(
    initialState = MapState(),
    updater = MapUpdater(),
    initialEffects = setOf(),
    processor = MapProcessor()
), KoinComponent