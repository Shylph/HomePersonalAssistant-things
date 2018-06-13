package com.blogspot.myks790.alassistant.things;

import com.blogspot.myks790.alassistant.things.Sensor.BME280Sensor;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SensorResource extends ServerResource {

    @Get("json")
    public Representation getTemperature() {

        JSONObject result = new JSONObject();
        BME280Sensor bme280Sensor = BME280Sensor.getInstance();
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:00.SSSZ", Locale.KOREA);
        float temperature = bme280Sensor.getTemperature();
        float humidity = bme280Sensor.getHumidity();
        try {
            result.put("timestamp", simpleDateFormat.format(date));
            result.put("temperature", temperature);
            result.put("humidity", humidity);

        } catch (JSONException ignored) {
        }
        return new StringRepresentation(result.toString(), MediaType.APPLICATION_ALL_JSON);
    }
}