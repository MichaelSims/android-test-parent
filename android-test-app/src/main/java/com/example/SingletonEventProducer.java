package com.example;

import com.example.events.GeneralEvent;
import com.squareup.otto.Bus;

import java.util.concurrent.Executor;

public class SingletonEventProducer {
    private final static String TAG = SingletonEventProducer.class.getSimpleName();

    private final Bus bus;
    private final Executor backgroundExecutor;
    private final Executor foregroundExecutor;

    public SingletonEventProducer(Bus bus, Executor backgroundExecutor, Executor foregroundExecutor) {
        this.bus = bus;
        this.backgroundExecutor = backgroundExecutor;
        this.foregroundExecutor = foregroundExecutor;
        bus.register(this);
    }

    public void doSomeBackgroundWorkThenBroadcast() {
        backgroundExecutor.execute(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ignored) {
                }
                foregroundExecutor.execute(new Runnable() {
                    public void run() {
                        bus.post(new GeneralEvent(-1));
                    }
                });
            }
        });
    }
}
