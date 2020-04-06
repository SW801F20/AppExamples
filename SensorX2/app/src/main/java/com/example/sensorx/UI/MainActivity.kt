package com.example.sensorx.UI

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.SensorManager.SENSOR_DELAY_NORMAL
import android.os.Bundle
import android.os.SystemClock
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
    private var mStepDetector: Sensor? = null
    lateinit var factory: MainActivityViewModelFactory
    lateinit var viewModel : MainActivityViewModel

    private fun initializeUi(){
        //display all sensors under the verbose category in the Log with SensorName tag
        lateinit var sensorManager: SensorManager
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val deviceSensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        for (sensor in deviceSensors){
            Log.v("SensorName", sensor.name)
        }
        Log.v("TheDevice", android.os.Build.MODEL);
        Log.v("TheManufacturer", android.os.Build.MANUFACTURER);

        factory = InjectorUtils.provideMainActivityViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(MainActivityViewModel::class.java)
        viewModel.getSensorValues().observe(this, Observer { sensorValues ->
            val stringBuilder = StringBuilder()
            sensorValues?.forEach { sensorValue ->
                stringBuilder.append("$sensorValue\n\n")
            }
            theTextView.text = stringBuilder.toString()
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeUi()
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
//        Define the sensor
        mStepDetector = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) //mSensors here determines which sensors are registered listeners
    }

    override fun onSensorChanged(p0: SensorEvent) {
        var sensorValue = SensorValue(p0.sensor.name, p0.values[0].toString())
        viewModel.addSensorValue(sensorValue)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onResume(){
        super.onResume()
        val sampling = 10000000
        mSensorManager.registerListener(this, mStepDetector, SENSOR_DELAY_NORMAL);
    }

    override fun onPause(){
        super.onPause()
        mSensorManager.unregisterListener(this)
    }
}
