package com.example.taskexecutor.sticky;

import android.util.Log;
import com.example.taskexecutor.TaskExecutionListener;
import com.example.taskexecutor.TaskExecutor;

public class StickyTaskExecutor<T> {

    private static final String TAG = StickyTaskExecutor.class.getSimpleName();

    private final TaskExecutor<T> taskExecutor;

    private final TaskExecutionListener<T> sourceListener = new ProxyListener();
    private TaskExecutionListener<T> targetListener;

    private T lastResult;
    private RuntimeException lastException;

    public StickyTaskExecutor(TaskExecutor<T> taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public <L extends TaskExecutionListener<T>> void register(L executionListener) {
        Log.e(TAG, executionListener + " is registered");
        targetListener = executionListener;
        takeLastResultsIfApplicable(executionListener);
    }

    public void execute() {
        Log.e(TAG, "Calling delegate executor");
        taskExecutor.execute(sourceListener);
    }

    public void cancelPendingExecution() {
        Log.e(TAG, "Cancelling delegate executor");
        taskExecutor.cancelNotificationsFor(sourceListener);
    }

    public void unregister() {
        Log.e(TAG, "Unregistering ");
        targetListener = null;
    }

    private <L extends TaskExecutionListener<T>> void takeLastResultsIfApplicable(L executionListener) {
        if (lastResult != null) {
            Log.e(TAG, "Calling onPostExecute with sticky result");
            executionListener.onPostExecute(lastResult);
        } else if (lastException != null) {
            Log.e(TAG, "Calling onPostExecute with sticky error");
            executionListener.onError(lastException);
        }
        lastResult = null;
        lastException = null;
    }

    private class ProxyListener implements TaskExecutionListener<T> {
        @Override
        public void onPreAsynchronousExecute() {
            if (targetListener != null) {
                targetListener.onPreAsynchronousExecute();
            }
        }

        @Override
        public void onStaleResultAvailable(T result) {
            // Delivering stale results not yet implemented
        }

        @Override
        public void onPostExecute(T result) {
            if (targetListener != null) {
                Log.e(TAG, "Delivering result to target");
                targetListener.onPostExecute(result);
            } else {
                Log.e(TAG, "No target attached, saving up to date result");
                lastResult = result;
            }
        }

        @Override
        public void onError(RuntimeException exception) {
            if (targetListener != null) {
                Log.e(TAG, "Delivering error to target");
                targetListener.onError(exception);
            } else {
                Log.e(TAG, "No target attached, saving exception");
                lastException = exception;
            }
        }
    }
}
