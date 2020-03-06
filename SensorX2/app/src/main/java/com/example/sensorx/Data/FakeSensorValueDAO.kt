package com.example.sensorx.Data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FakeSensorValueDAO {
    private val sensorValueList = mutableListOf<SensorValue>()
    private val sensorValues = MutableLiveData<List<SensorValue>>()

    //Init blocks bliver kaldt når klassen bliver initialiseret, man ka have flere,
    //så bliver de kaldt i den rækkefølge de står i, i koden.
    init {
        sensorValues.value = sensorValueList
    }

    fun addSensorValue(sensorValue: SensorValue){
        sensorValueList.add(sensorValue)

        sensorValues.value = sensorValueList
    }

    fun getSensorValues() = sensorValues as LiveData<List<SensorValue>>
}
