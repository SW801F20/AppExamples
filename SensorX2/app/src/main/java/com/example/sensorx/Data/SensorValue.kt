package com.example.sensorx.Data

data class SensorValue(val Sensor: String, val measurement: String) {

    override fun toString(): String {
        return "$Sensor - $measurement"
    }
}