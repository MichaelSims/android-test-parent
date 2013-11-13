package com.example.taskexecutor;

import java.util.concurrent.Executor;

public interface ExecutorFactory {
    public Executor create();
}
