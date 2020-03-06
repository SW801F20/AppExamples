package com.example.sensorx.UI

import androidx.lifecycle.ViewModel
import com.example.sensorx.Data.SensorValue
import com.example.sensorx.Data.SensorValueRepository


class MainActivityViewModel(private val sensorValueRepository: SensorValueRepository): ViewModel() {
    fun getSensorValues() = sensorValueRepository.getSensorValues()

    fun addSensorValue(sensorValue : SensorValue) = sensorValueRepository.addSensorValue(sensorValue)
}