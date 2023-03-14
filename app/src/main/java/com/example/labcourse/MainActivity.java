package com.example.labcourse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.labcourse.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'labcourse' library on application startup.
    static {
        System.loadLibrary("labcourse");
    }

    private ActivityMainBinding binding;
    private SensorManager sensorManager;
    private Sensor sensor;
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
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION);

        triggerEventListener = new TriggerEventListener() {
            @Override
            public void onTrigger(TriggerEvent event) {
                //TODO: achieve observability
            }
        };

        sensorManager.requestTriggerSensor(triggerEventListener, sensor);
    }

    /**
     * A native method that is implemented by the 'labcourse' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}