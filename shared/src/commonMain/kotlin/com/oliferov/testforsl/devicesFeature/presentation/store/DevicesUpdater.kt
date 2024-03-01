package com.oliferov.testforsl.devicesFeature.presentation.store

import com.oliferov.testforsl.core.StateHolder
import com.oliferov.testforsl.core.StateUpdater

typealias NewState = StateHolder<DevicesState, DevicesEffects, DevicesEvents>
class DevicesUpdater : StateUpdater<DevicesState, DevicesActions, DevicesEffects, DevicesEvents> {
    override fun update(
        action: DevicesActions,
        currentState: DevicesState
    ): StateHolder<DevicesState, DevicesEffects, DevicesEvents> {
        return when (action) {
            is DevicesActions.SelectedDevice -> selectedDevice(action, currentState)
            is DevicesActions.LoadDevices -> fetchDevices(action, currentState)
            is DevicesActions.DevicesChanged -> devicesChanged(action, currentState)
        }
    }

    private fun selectedDevice(
        action: DevicesActions.SelectedDevice,
        currentState: DevicesState
    ): NewState = StateHolder(
        state = currentState.copy(isLoading = false, selectedDevice = action.devaice),
        events = setOf(DevicesEvents.SelectedDevice(action.devaice))
    )

    private fun fetchDevices(
        action: DevicesActions.LoadDevices,
        currentState: DevicesState
    ): NewState = StateHolder(
        currentState.copy(isLoading = true, devaces = emptyList()),
        effects = setOf(DevicesEffects.FetchDevices)
    )

    private fun devicesChanged(
        action: DevicesActions.DevicesChanged,
        currentState: DevicesState
    ): NewState = StateHolder(
        currentState.copy(isLoading = false, devaces = action.devices)
    )
}