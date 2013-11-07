package com.example.taskexecutor;

public interface TaskExecutor<T> {
    <L extends TaskExecutionListener<T>> L execute(L executionListener);
    void cancelNotificationsFor(TaskExecutionListener<T> executionListener);
}
