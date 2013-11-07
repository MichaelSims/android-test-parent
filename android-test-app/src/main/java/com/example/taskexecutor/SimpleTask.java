package com.example.taskexecutor;

public abstract class SimpleTask<T> implements Task<T> {
    @Override
    public T getCachedResultIfAvailable() {
        return null;
    }

    @Override
    public abstract T execute();
}
