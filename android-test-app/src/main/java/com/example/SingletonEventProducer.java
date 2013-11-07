package com.example;

import android.util.Log;
import com.example.events.ResultEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.DeadEvent;
import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;

import java.util.concurrent.Executor;

public class SingletonEventProducer {
    private final static String TAG = SingletonEventProducer.class.getSimpleName();

    private final Bus bus;
    private final Executor backgroundExecutor;
    private final Executor foregroundExecutor;
    private ResultEvent lastResult;

    public SingletonEventProducer(Bus bus, Executor backgroundExecutor, Executor foregroundExecutor) {
        this.bus = bus;
        this.backgroundExecutor = backgroundExecutor;
        this.foregroundExecutor = foregroundExecutor;
        bus.register(this);
    }

    public void doSomeBackgroundWorkThenBroadcast(final Object requestId) {
        Log.e(TAG, "Got request for work with id " + requestId);
        backgroundExecutor.execute(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ignored) {
                }
                foregroundExecutor.execute(new Runnable() {
                    public void run() {
                        bus.post(new ResultEvent(requestId));
                    }
                });
            }
        });
    }

    @Subscribe
    public void enqueueDeadEvent(DeadEvent event) {
        Log.e(TAG, "Got a dead event");
        if (event.event instanceof ResultEvent) {
            lastResult = (ResultEvent) event.event;
        }
    }

    @Produce
    public ResultEvent takeLastResult() {
        try {
            ResultEvent result = lastResult;
            Log.e(TAG, "Returning result");
            return result;
        } finally {
            lastResult = null;
        }
    }
}
