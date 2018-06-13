package com.blogspot.myks790.alassistant.things.Sensor;

/**
 * Created by myks7 on 2018-03-30.
 */

public interface SensorCallback {
    void changeTemperature(float temperature);
    void changePressure(float pressure);
    void changeHumidity(float humidity);

}
