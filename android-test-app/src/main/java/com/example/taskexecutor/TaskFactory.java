package com.example.taskexecutor;

public interface TaskFactory<K, V> {
    public Task<V> create(K key);
}
