package com.example.taskexecutor;

import android.util.Log;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;

public class DefaultTaskExecutor<T> implements TaskExecutor<T> {

    private static final String TAG = DefaultTaskExecutor.class.getSimpleName();

    private Task<T> task;
    private final Executor backgroundExecutor;
    private final Executor foregroundExecutor;

    private final Set<TaskExecutionListener<T>> pendingListeners = new HashSet<TaskExecutionListener<T>>();

    public DefaultTaskExecutor(Task<T> task, Executor backgroundExecutor, Executor foregroundExecutor) {
        this.task = task;
        this.backgroundExecutor = backgroundExecutor;
        this.foregroundExecutor = foregroundExecutor;
    }

    @Override
    public <L extends TaskExecutionListener<T>> L execute(final L executionListener) {
        T cachedResult = task.getCachedResultIfAvailable();
        if (cachedResult != null) {
            Log.d(TAG, "Result is cached in " + task + ", calling listener synchronously");
            executionListener.onPostExecute(cachedResult);
        } else {
            Log.d(TAG, "Result not available in " + task + ", calling execute in the background");
            executionListener.onPreAsynchronousExecute();
            pendingListeners.add(executionListener);
            backgroundExecutor.execute(new Runnable() {
                public void run() {

                    RuntimeException caughtException = null;
                    T result = null;
                    try {
                        result = task.execute();
                    } catch (RuntimeException e) {
                        Log.e(TAG, String.format("Caught exception while executing task %s", task), e);
                        caughtException = e;
                    }

                    final T finalResult = result;
                    final RuntimeException finalCaughtException = caughtException;
                    foregroundExecutor.execute(new Runnable() {
                        public void run() {
                            if (pendingListeners.remove(executionListener)) { // Notify listener only if it's still pending (wasn't cancelled)
                                if (finalCaughtException != null) {
                                    executionListener.onError(finalCaughtException);
                                } else {
                                    executionListener.onPostExecute(finalResult);
                                }
                            }
                        }
                    });

                }
            });
        }
        return executionListener;
    }

    @Override
    public void cancelNotificationsFor(TaskExecutionListener<T> executionListener) {
        pendingListeners.remove(executionListener);
    }
}
