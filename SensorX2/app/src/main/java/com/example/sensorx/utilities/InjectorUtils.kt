package com.example.sensorx.utilities

import com.example.sensorx.Data.FakeDatabase
import com.example.sensorx.Data.SensorValueRepository
import com.example.sensorx.UI.MainActivityViewModelFactory

object InjectorUtils {

    fun provideMainActivityViewModelFactory(): MainActivityViewModelFactory {
        val sensorValueRepository = SensorValueRepository.getInstance(FakeDatabase.getInstance().sensorValueDAO)
        return MainActivityViewModelFactory(sensorValueRepository)
    }
}