package com.blogspot.myks790.alassistant.things;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;


public class APIServerService extends IntentService {
    private String LOG_NAME = getClass().getName();
    private Component restComponent;
    private static final int PORT = 8090;
    public static final String START_API_SERVER = "com.survivingwithandroid.api.start";
    public static final String STOP_API_SERVER = "com.survivingwithandroid.api.stop";

    public APIServerService() {
        super("APiServerService");
        // start the Rest server
        restComponent = new Component();
        restComponent.getServers().add(Protocol.HTTP, PORT); // listen on 8090
        // Router to dispatch Request
        Router router = new Router();
        router.attach("/resource", SensorResource.class);
        restComponent.getDefaultHost().attach("/sensor", router);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            try {
                if (action.equals(START_API_SERVER)) {
                    Log.d(LOG_NAME, "Starting API Server");
                    restComponent.start();
                } else if (action.equals(STOP_API_SERVER)) {
                    Log.d(LOG_NAME, "Stopping API Server");
                    restComponent.stop();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}