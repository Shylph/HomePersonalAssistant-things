package com.blogspot.myks790.alassistant.things;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.blogspot.myks790.alassistant.things.Sensor.BME280Sensor;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BME280Sensor bme280Sensor = BME280Sensor.getInstance();
        bme280Sensor.register(this, "I2C1");

        Intent i = new Intent(this, APIServerService.class);
        i.setAction(APIServerService.START_API_SERVER);
        startService(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BME280Sensor.getInstance().unregister();
        Intent i = new Intent(this, APIServerService.class);
        i.setAction(APIServerService.STOP_API_SERVER);
        startService(i);
    }
}
