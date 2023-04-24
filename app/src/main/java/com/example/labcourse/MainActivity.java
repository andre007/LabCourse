package com.example.labcourse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.labcourse.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    // Used to load the 'labcourse' library on application startup.
    static {
        System.loadLibrary("labcourse");
    }

    private ActivityMainBinding binding;
    private SensorManager sensorManager;
    private Sensor accelerometer; // changed sensor name to accelerometer
    private TriggerEventListener triggerEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Example of a call to a native method
        TextView tv = binding.sampleText;
        tv.setText(stringFromJNI());

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); // changed accelerometer sensor type to TYPE_ACCELEROMETER
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL); // registering listener to accelerometer

        triggerEventListener = new TriggerEventListener() {
            @Override
            public void onTrigger(TriggerEvent event) {
                //TODO: achieve observability
            }
        };

        sensorManager.requestTriggerSensor(triggerEventListener, accelerometer);
    }

    /**
     * A native method that is implemented by the 'labcourse' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    // implementing methods from SensorEventListener
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //if sensors type that changed is equals TYPE_ACCELEROMETER, logging x, y, z values of it
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            Log.d("Accelerometer", "X: " + x + ", Y: " + y + ", Z: " + z);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}