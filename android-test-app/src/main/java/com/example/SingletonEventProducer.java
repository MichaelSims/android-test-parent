package com.example;

import android.util.Log;
import com.example.events.GeneralEvent;
import com.squareup.otto.Bus;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

public class SingletonEventProducer {
    private final static String TAG = SingletonEventProducer.class.getSimpleName();

    private final Bus bus;
    private final Executor backgroundExecutor;
    private final Executor foregroundExecutor;
    private final Map<Class<? extends GeneralEvent>, GeneralEvent> eventMap = new HashMap<Class<? extends GeneralEvent>, GeneralEvent>();

    public SingletonEventProducer(Bus bus, Executor backgroundExecutor, Executor foregroundExecutor) {
        this.bus = bus;
        this.backgroundExecutor = backgroundExecutor;
        this.foregroundExecutor = foregroundExecutor;
        bus.register(this);
    }

    public void doSomeBackgroundWorkThenBroadcast(final GeneralEvent eventToBroadcast) {
        Log.e(TAG, "Asked to eventually broadcast " + eventToBroadcast);
        backgroundExecutor.execute(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ignored) {
                }
                foregroundExecutor.execute(new Runnable() {
                    public void run() {
                        Log.e(TAG, "Broadcasting " + eventToBroadcast);
                        eventMap.put(eventToBroadcast.getClass(), eventToBroadcast);
                        bus.post(eventToBroadcast);
                    }
                });
            }
        });
    }
}
