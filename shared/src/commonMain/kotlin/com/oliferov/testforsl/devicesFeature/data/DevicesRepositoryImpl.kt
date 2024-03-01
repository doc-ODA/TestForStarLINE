package com.oliferov.testforsl.devicesFeature.data


import com.oliferov.testforsl.devicesFeature.data.mapper.DeviceCloudResultToDeviceDomainMapper
import com.oliferov.testforsl.devicesFeature.data.pojo.DevicesResponseCloudResult
import com.oliferov.testforsl.devicesFeature.domain.DevicesRepository
import com.oliferov.testforsl.devicesFeature.domain.model.GroupDeviceDomain
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json


class DevicesRepositoryImpl(
    val mapper: DeviceCloudResultToDeviceDomainMapper
): DevicesRepository {
    override suspend fun fetchDevices(): List<GroupDeviceDomain> {
        //симулируем загрузку данных
        delay(3000)
        val jsonString = DEVICE_LIST.trimIndent()
        val devices = Json.decodeFromString<DevicesResponseCloudResult>(jsonString).devices
        return mapper.mapList(devices.list)
    }

    companion object {
        private const val DEVICE_LIST = """{
  "devices": {
    "list": [
      {
        "parent": "root",
        "group": "private",
        "title": "Личные авто"
      },
      {
        "parent": "private",
        "type": "S96",
        "title": "VW Teramont",
        "lat": 59.944265,
        "lon": 30.307159
      },
      {
        "parent": "private",
        "type": "A93",
        "title": "Kia Rio",
        "lat": 49.869913,
        "lon": 142.788620
      },
      {
        "parent": "root",
        "group": "shared",
        "title": "Общие авто"
      },
      {
        "parent": "shared",
        "type": "A96",
        "title": "VW Caddy",
        "lat": 59.941842,
        "lon": 30.608865
      },
      {
        "parent": "shared",
        "type": "M36",
        "title": "Tesla Model X",
        "lat": 37.418964,
        "lon": -122.089884
      },
      {
        "parent": "shared",
        "type": "M17",
        "title": "Мотоцикл",
        "lat": 60.025627,
        "lon": 30.332308
      }
    ]
  }
}"""
    }
}