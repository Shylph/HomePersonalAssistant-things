package com.blogspot.myks790.alassistant.things;

import android.app.Activity;
import android.os.Bundle;

import com.blogspot.myks790.alassistant.things.Sensor.BME280Sensor;
import com.blogspot.myks790.alassistant.things.Sensor.SensorCallback;

/**
 * Skeleton of an Android Things activity.
 * <p>
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 * <p>
 * <pre>{@code
 * PeripheralManagerService service = new PeripheralManagerService();
 * mLedGpio = service.openGpio("BCM6");
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
 * mLedGpio.setValue(true);
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 */
public class MainActivity extends Activity {

    private BME280Sensor bme280Sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bme280Sensor = new BME280Sensor(this, "I2C1");
        bme280Sensor.register(new SensorCallback() {
            @Override
            public void changeTemperature(float temperature) {
                System.out.println("t:"+bme280Sensor.getTemperature());

            }

            @Override
            public void changePressure(float pressure) {

            }

            @Override
            public void changeHumidity(float humidity) {
                System.out.println("h:"+bme280Sensor.getHumidity());
            }
        },25);

    }

    @Override
    protected void onDestroy() {
        bme280Sensor.unregister();
        super.onDestroy();
    }
}
