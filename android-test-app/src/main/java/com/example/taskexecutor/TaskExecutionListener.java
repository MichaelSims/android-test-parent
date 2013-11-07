package com.example.taskexecutor;

public interface TaskExecutionListener<T> {

    /**
     * Called when result is not cached and is about to be produced asynchronously.
     *
     * If the result is cached, this method will not be called at all.
     */
    void onPreAsynchronousExecute();

    /**
     * Optionally called when a stale version of the task result is available.
     *
     * If this method is called, either {@link #onPostExecute(Object)}
     * or {@link #onError(RuntimeException)}
     * will be subsequently called.
     */
    void onStaleResultAvailable(T result);

    void onPostExecute(T result);

    void onError(RuntimeException exception);

}
