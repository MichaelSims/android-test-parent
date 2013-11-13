package com.example.taskexecutor;

import java.util.concurrent.Executor;

public class TaskExecutorFactory {

    private final ExecutorFactory backgroundExecutorFactory;
    private final Executor foregroundExecutor;

    public TaskExecutorFactory(ExecutorFactory backgroundExecutorFactory, Executor foregroundExecutor) {
        this.backgroundExecutorFactory = backgroundExecutorFactory;
        this.foregroundExecutor = foregroundExecutor;
    }

    public <T> TaskExecutor<T> create(Task<T> c) {
        Executor backgroundExecutor = backgroundExecutorFactory.create();
        return new DefaultTaskExecutor<T>(c, backgroundExecutor, foregroundExecutor);
    }
}