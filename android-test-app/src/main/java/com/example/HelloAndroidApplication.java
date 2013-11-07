package com.example;

import android.app.Application;
import android.util.Log;
import android.util.Printer;

public class HelloAndroidApplication extends Application {
    private final static String TAG = HelloAndroidApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        getMainLooper().setMessageLogging(new Printer() {
            @Override
            public void println(final String x) {
                Log.e(TAG, ">>> " + x);
            }
        });
        DIContainer.initialize(this);
    }
}
