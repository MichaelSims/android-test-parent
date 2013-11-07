package com.example.taskexecutor.sticky;

public interface StickyTaskExecutorFactory<T> {
    StickyTaskExecutor<T> create(Object key);
}
