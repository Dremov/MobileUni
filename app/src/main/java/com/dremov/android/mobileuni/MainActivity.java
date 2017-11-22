package com.dremov.android.mobileuni;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private TextView xValue, yValue, zValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);

        xValue = (TextView) findViewById(R.id.x_component_val);
        yValue = (TextView) findViewById(R.id.y_component_val);
        zValue = (TextView) findViewById(R.id.z_component_val);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // In this example, alpha is calculated as t / (t + dT),
        // where t is the low-pass filter's time-constant and
        // dT is the event delivery rate.

        String value = "";
        float[] gravity = new float[3];
        final float alpha = 0.8f;
        float x, y, z;
        // Isolate the force of gravity with the low-pass filter.
        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];
        // Remove the gravity contribution with the high-pass filter.
        x = event.values[0] - gravity[0];
        y = event.values[1] - gravity[1];
        z = event.values[2] - gravity[2];
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar_x);
        progressBar.setProgress((int)(x * 10));
        value = "" + (int)(x*10);
        xValue.setText(value);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_y);
        progressBar.setProgress((int)(y * 10));
        value = "" + (int)(y*10);
        yValue.setText(value);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_z);
        progressBar.setProgress((int)(z * 10));
        value = "" + (int)(z*10);
        zValue.setText(value);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
