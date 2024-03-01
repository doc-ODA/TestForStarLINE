package com.oliferov.testforsl.devicesFeature.presentation.store

import com.oliferov.testforsl.core.Effects

sealed class DevicesEffects: Effects{
    data object FetchDevices: DevicesEffects()
}