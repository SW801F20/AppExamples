package com.example.sensorx.Data

class SensorValueRepository private constructor(private val sensorValueDAO: FakeSensorValueDAO) {

    fun addSensorValue(sensorValue: SensorValue){
        sensorValueDAO.addSensorValue(sensorValue)
    }

    fun getSensorValues() = sensorValueDAO.getSensorValues()

    companion object {
        @Volatile private var instance: SensorValueRepository?= null
        fun getInstance(sensorValueDAO: FakeSensorValueDAO) =
            instance ?: synchronized(this){
                instance ?: SensorValueRepository(sensorValueDAO).also { instance = it}
            }
    }

}