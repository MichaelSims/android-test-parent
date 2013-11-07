package com.example.taskexecutor;

/** Version of {@link TaskExecutionListener} that provides empty implementations for non-critical callback methods. */
public abstract class DefaultTaskExecutionListener<T> implements TaskExecutionListener<T> {
    @Override
    public void onPreAsynchronousExecute() {}

    @Override
    public void onStaleResultAvailable(T result) {}
}
