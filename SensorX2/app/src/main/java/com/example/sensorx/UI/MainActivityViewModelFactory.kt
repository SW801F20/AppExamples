package com.example.sensorx.UI

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sensorx.Data.SensorValueRepository

class MainActivityViewModelFactory (private val sensorValueRepository: SensorValueRepository): ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainActivityViewModel(sensorValueRepository) as T
    }
}