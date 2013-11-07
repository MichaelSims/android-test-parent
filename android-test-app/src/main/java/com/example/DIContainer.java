package com.example;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import com.squareup.otto.Bus;

import java.util.concurrent.Executors;

public class DIContainer {
    private static DIContainer instance;
    private final Application application;
    private Bus bus;
    private SingletonEventProducer eventProducer;
    private UIThreadExecutor uiThreadExecutor;

    public DIContainer(Application application) {
        this.application = application;
    }

    public synchronized static DIContainer getInstance() {
        return instance;
    }

    public synchronized static void initialize(Application application) {
        instance = new DIContainer(application);
    }

    public synchronized Bus getBus() {
        if (bus == null) {
            bus = new Bus();
        }
        return bus;
    }

    public synchronized Application getApplication() {
        return application;
    }

    public synchronized SingletonEventProducer getEventProducer() {
        if (eventProducer == null) {
            eventProducer = new SingletonEventProducer(getBus(), Executors.newSingleThreadExecutor(), getUiThreadExecutor());
        }
        return eventProducer;
    }

    public synchronized UIThreadExecutor getUiThreadExecutor() {
        if (uiThreadExecutor == null) {
            uiThreadExecutor = new UIThreadExecutor(new Handler(Looper.getMainLooper()));
        }
        return uiThreadExecutor;
    }
}
