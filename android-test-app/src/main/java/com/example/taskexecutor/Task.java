package com.example.taskexecutor;

/** Interface for tasks that optionally return a result */
public interface Task<T> {

    /**
     * Returns an up-to-date task result if synchronously available (safe to call on UI thread),
     * null otherwise.
     */
    public T getCachedResultIfAvailable();

    /**
     * Returns an up-to-date task result. Implementations SHOULD return the value in the most efficient manner
     * (i.e. returning cached data if available), however, this method should not be called synchronously from
     * the UI thread.
     */
    public T execute();

}
