package com.oliferov.testforsl.devicesFeature.data.mapper

import com.oliferov.testforsl.devicesFeature.data.pojo.DeviceCloudResult
import com.oliferov.testforsl.devicesFeature.domain.model.CarDeviceDomain
import com.oliferov.testforsl.devicesFeature.domain.model.DeviceDomain
import com.oliferov.testforsl.devicesFeature.domain.model.GroupDeviceDomain

interface DeviceCloudResultToDeviceDomainMapper {

    fun mapList(devices: List<DeviceCloudResult>): List<GroupDeviceDomain>
    class Base() : DeviceCloudResultToDeviceDomainMapper {

        private fun map(device: DeviceCloudResult): DeviceDomain {
            return if (device.group != null) {
                GroupDeviceDomain(
                    parent = device.parent,
                    group = device.group,
                    title = device.title,
                    cars = mutableListOf()
                )
            } else {
                CarDeviceDomain(
                    parent = device.parent,
                    type = device.type ?: "",
                    title = device.title,
                    lat = device.lat ?: -1.0,
                    lon = device.lon ?: -1.0
                )
            }
        }

        override fun mapList(devices: List<DeviceCloudResult>): List<GroupDeviceDomain> {
            val groups = mutableListOf<GroupDeviceDomain>()
            val cars = mutableMapOf<String, MutableList<CarDeviceDomain>>()
            devices.forEach {
                val device = map(it)
                when (device) {
                    is GroupDeviceDomain -> {
                        groups.add(device)
                    }

                    is CarDeviceDomain -> {
                        if (cars.containsKey(device.parent)) {
                            cars[device.parent]?.add(device)
                        } else {
                            cars[device.parent] = mutableListOf(device)
                        }
                    }
                }
            }
            groups.map { group ->
                cars[group.group]?.let { list -> group.cars.addAll(list) }
            }
            return groups
        }
    }
}