package com.example.sensorx.UI

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sensorx.Data.SensorValue
import com.example.sensorx.R
import com.example.sensorx.utilities.InjectorUtils
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var mSensorManager: SensorManager
    private var mSensors: Sensor? = null
    private var number = 0
    lateinit var factory: MainActivityViewModelFactory
    lateinit var viewModel : MainActivityViewModel

    private fun initializeUi(){

        factory = InjectorUtils.provideMainActivityViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(MainActivityViewModel::class.java)
        viewModel.getSensorValues().observe(this, Observer { sensorValues ->
            val stringBuilder = StringBuilder()
            sensorValues?.forEach { sensorValue ->
                stringBuilder.append("$sensorValue\n\n")
            }
            fredag.text = stringBuilder.toString()
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeUi()
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
//        Define the sensor
        mSensors = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)


    }

    override fun onSensorChanged(p0: SensorEvent) {

        var sensorValue = SensorValue(p0.sensor.toString(), p0.values[0].toString())
        viewModel.addSensorValue(sensorValue)
        var millibarsOfPressure = p0!!.values[0]
        if (p0.sensor.type == Sensor.TYPE_LIGHT)
            Toast.makeText(this, "" + millibarsOfPressure + " lx", Toast.LENGTH_SHORT).show()

        if (p0.sensor.type == Sensor.TYPE_ROTATION_VECTOR)
            Log.v("SensorChanged", ""+ p0.sensor.toString())

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onResume(){
        super.onResume()
        mSensorManager.registerListener(this, mSensors, SensorManager.SENSOR_DELAY_NORMAL)

    }

    override fun onPause(){
        super.onPause()
        mSensorManager.unregisterListener(this)
    }
}
