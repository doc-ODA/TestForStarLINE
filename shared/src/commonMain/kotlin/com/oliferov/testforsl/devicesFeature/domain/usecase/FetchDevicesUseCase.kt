package com.oliferov.testforsl.devicesFeature.domain.usecase

import com.oliferov.testforsl.devicesFeature.domain.DevicesRepository
import com.oliferov.testforsl.devicesFeature.domain.model.GroupDeviceDomain

interface FetchDevicesUseCase {
    suspend operator fun invoke(): List<GroupDeviceDomain>
    class Base(val repository: DevicesRepository) : FetchDevicesUseCase {
        override suspend fun invoke() = repository.fetchDevices()
    }
}