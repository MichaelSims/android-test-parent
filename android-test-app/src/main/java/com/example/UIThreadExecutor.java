package com.example;

import android.os.Handler;

import java.util.concurrent.Executor;

public class UIThreadExecutor implements Executor {
    private final Handler handler;

    public UIThreadExecutor(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void execute(Runnable runnable) {
        handler.post(runnable);
    }
}
