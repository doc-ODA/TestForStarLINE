package com.oliferov.testforsl.mapFeature.store

import com.oliferov.testforsl.core.Processor
import com.oliferov.testforsl.devicesFeature.domain.usecase.FetchDevicesUseCase

class MapProcessor: Processor<MapActions, MapEffects> {
    override suspend fun process(effect: MapEffects): MapActions {
        TODO("Not yet implemented")
    }
}