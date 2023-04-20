package com.example.versioapp1;


import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.preference.PreferenceManager;


import java.util.ArrayList;
import java.util.List;

public class SensorManagerHelper implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor heartRateSensor, stepDetector, stepCounter;
    private int stepCount = 0;
    Context mContext;
    //initialising lists
    private List<SensorEventListener> heartRateListeners = new ArrayList<>();
    private List<SensorEventListener> stepCounterListeners = new ArrayList<>();
    private List<SensorEventListener> stepDetectorListeners = new ArrayList<>();

    public SensorManagerHelper(Context context) {
        //initialising sensors
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        heartRateSensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        stepDetector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        mContext = context;


    }

    public void registerHeartRateListener(SensorEventListener listener) {
        if (!heartRateListeners.contains(listener)) {
            heartRateListeners.add(listener);
            sensorManager.registerListener(this, heartRateSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void unregisterHeartRateListener(SensorEventListener listener) {
        if (heartRateListeners.contains(listener)) {
            heartRateListeners.remove(listener);
            sensorManager.unregisterListener(this, heartRateSensor);
        }
    }

    public void registerStepCounterListener(SensorEventListener listener) {
        if (!stepCounterListeners.contains(listener)) {
            stepCounterListeners.add(listener);
            sensorManager.registerListener(this, stepCounter, SensorManager.SENSOR_DELAY_NORMAL);
        }

    }

    public void unregisterStepCounterListener(SensorEventListener listener) {
        if (stepCounterListeners.contains(listener)) {
            stepCounterListeners.remove(listener);
            sensorManager.unregisterListener(this, stepCounter);
        }
    }

    public void registerStepDetectorListener(SensorEventListener listener) {
        if (!stepDetectorListeners.contains(listener)) {
            stepDetectorListeners.add(listener);
            sensorManager.registerListener(this, stepDetector, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void unregisterStepDetectorListener(SensorEventListener listener) {
        if (stepDetectorListeners.contains(listener)) {
            stepDetectorListeners.remove(listener);
            sensorManager.unregisterListener(this, stepDetector);
        }
    }

    public void updateStepCountInPrefs(int stepCount) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("step_count", stepCount);
        editor.apply();


    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
            for (SensorEventListener listener : heartRateListeners) {
                listener.onSensorChanged(event);
            }
        }
        if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            for (SensorEventListener listener : stepDetectorListeners) {
                listener.onSensorChanged(event);
            }
        }
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            for (SensorEventListener listener : stepCounterListeners) {
                listener.onSensorChanged(event);
            }
            stepCount = (int) event.values[0];
            updateStepCountInPrefs(stepCount);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
