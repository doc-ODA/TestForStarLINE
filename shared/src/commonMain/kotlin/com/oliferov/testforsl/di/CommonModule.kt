package com.oliferov.testforsl.di

import com.oliferov.testforsl.devicesFeature.data.DevicesRepositoryImpl
import com.oliferov.testforsl.devicesFeature.data.mapper.DeviceCloudResultToDeviceDomainMapper
import com.oliferov.testforsl.devicesFeature.domain.DevicesRepository
import com.oliferov.testforsl.devicesFeature.domain.usecase.FetchDevicesUseCase
import com.oliferov.testforsl.devicesFeature.presentation.store.DevicesStore
import com.oliferov.testforsl.mapFeature.store.MapStore
import org.koin.core.module.Module
import org.koin.core.module._singleInstanceFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ext.getFullName

internal fun commonModule() = listOf<Module>(devicesModule)

private val devicesModule = module {
    single<DeviceCloudResultToDeviceDomainMapper> {
        DeviceCloudResultToDeviceDomainMapper.Base()
    }
    single<DevicesRepository> {
        DevicesRepositoryImpl(mapper = get())
    }
    single<FetchDevicesUseCase> {
        FetchDevicesUseCase.Base(repository = get())
    }
    single<DevicesStore> {
        DevicesStore(fetchDevicesUseCase = get())
    }
    single<MapStore> {
        MapStore()
    }
}
