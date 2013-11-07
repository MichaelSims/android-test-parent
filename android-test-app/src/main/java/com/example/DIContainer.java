package com.example;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import com.example.taskexecutor.DefaultTaskExecutor;
import com.example.taskexecutor.TaskExecutor;
import com.example.taskexecutor.sticky.DefaultStickyTaskExecutor;
import com.example.taskexecutor.sticky.StickyTaskExecutor;

import java.util.concurrent.Executors;

public class DIContainer {
    private static DIContainer instance;
    private final Application application;
    private UIThreadExecutor uiThreadExecutor;
    private IdGenerator idGenerator;
    private TaskExecutor<Object> objectTaskExecutor;
    private StickyTaskExecutor<Object> objectStickyTaskExecutor;

    public DIContainer(Application application) {
        this.application = application;
    }

    public synchronized static DIContainer getInstance() {
        return instance;
    }

    public synchronized static void initialize(Application application) {
        instance = new DIContainer(application);
    }

    public synchronized Application getApplication() {
        return application;
    }

    public synchronized UIThreadExecutor getUiThreadExecutor() {
        if (uiThreadExecutor == null) {
            uiThreadExecutor = new UIThreadExecutor(new Handler(Looper.getMainLooper()));
        }
        return uiThreadExecutor;
    }

    public synchronized IdGenerator getIdGenerator() {
        if (idGenerator == null) {
            idGenerator = new IdGenerator();
        }
        return idGenerator;
    }

    public synchronized TaskExecutor<Object> getObjectTaskExecutor() {
        if (objectTaskExecutor == null) {
            objectTaskExecutor = new DefaultTaskExecutor<Object>(new ObjectTask(), Executors.newSingleThreadExecutor(), getUiThreadExecutor());
        }
        return objectTaskExecutor;
    }

    public synchronized StickyTaskExecutor<Object> getObjectStickyTaskExecutor() {
        if (objectStickyTaskExecutor == null) {
            objectStickyTaskExecutor = new DefaultStickyTaskExecutor<Object>(getObjectTaskExecutor());
        }
        return objectStickyTaskExecutor;
    }
}
