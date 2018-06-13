package com.blogspot.myks790.alassistant.things.Sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.google.android.things.contrib.driver.bmx280.Bmx280SensorDriver;

import java.io.IOException;

public class BME280Sensor {
    private static BME280Sensor bme280Sensor;
    private static boolean registerFlg;
    private static final String TAG = BME280Sensor.class.getSimpleName();
    private Bmx280SensorDriver bmx280SensorDriver;
    private SensorManager sensorManager;
    private float temperature;
    private float pressure;
    private float humidity;

    private BME280Sensor() {
    }

    private SensorEventListener temperatureSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            temperature = event.values[0];
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            Log.i(TAG, "temperature sensor accuracy changed: " + accuracy);
        }
    };
    private SensorEventListener pressureSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            pressure = event.values[0];
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            Log.i(TAG, "pressure sensor accuracy changed: " + accuracy);
        }
    };
    private SensorEventListener humiditySensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            humidity = event.values[0];
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            Log.i(TAG, "humidity sensor accuracy changed: " + accuracy);
        }
    };
    private SensorManager.DynamicSensorCallback dynamicSensorCallback = new SensorManager.DynamicSensorCallback() {
        @Override
        public void onDynamicSensorConnected(Sensor sensor) {
            if (sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                Log.i(TAG, "TYPE_AMBIENT_TEMPERATURE connected");
                System.out.println(sensor.toString());
                sensorManager.registerListener(temperatureSensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            } else if (sensor.getType() == Sensor.TYPE_PRESSURE) {
                Log.i(TAG, "TYPE_PRESSURE connected");
                System.out.println(sensor.toString());
                sensorManager.registerListener(pressureSensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            } else if (sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY) {
                Log.i(TAG, "TYPE_RELATIVE_HUMIDITY connected");
                System.out.println(sensor.toString());
                sensorManager.registerListener(humiditySensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
    };

    public void register(Context context, String pin) {
        registerFlg = true;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            sensorManager.registerDynamicSensorCallback(dynamicSensorCallback);
        }

        try {
            bmx280SensorDriver = new Bmx280SensorDriver(pin);
            bmx280SensorDriver.registerTemperatureSensor();
            bmx280SensorDriver.registerPressureSensor();
            bmx280SensorDriver.registerHumiditySensor();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unregister() {
        registerFlg = false;
        sensorManager.unregisterDynamicSensorCallback(dynamicSensorCallback);
        sensorManager.unregisterListener(pressureSensorEventListener);
        sensorManager.unregisterListener(temperatureSensorEventListener);
        sensorManager.unregisterListener(humiditySensorEventListener);
        if (bmx280SensorDriver != null) {
            bmx280SensorDriver.unregisterTemperatureSensor();
            bmx280SensorDriver.unregisterPressureSensor();
            bmx280SensorDriver.registerHumiditySensor();
            try {
                bmx280SensorDriver.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                bmx280SensorDriver = null;
            }
        }
    }

    public float getTemperature() {
        return temperature;
    }

    public float getPressure() {
        return pressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public static BME280Sensor getInstance() {
        if (bme280Sensor == null)
            return bme280Sensor = new BME280Sensor();
        else{
            Log.d("info","bme280 sensor register : "+ registerFlg);
            return bme280Sensor;
        }
    }
}
