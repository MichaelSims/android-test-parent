package com.example;

import com.example.taskexecutor.Task;

public class ObjectTask implements Task<Object> {
    private final Object result = new Object();

    @Override
    public Object getCachedResultIfAvailable() {
        return null;
    }

    @Override
    public Object execute() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignored) {
        }
        return result;
    }
}
