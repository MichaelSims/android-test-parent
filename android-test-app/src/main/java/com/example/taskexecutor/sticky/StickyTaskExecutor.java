package com.example.taskexecutor.sticky;

import com.example.taskexecutor.TaskExecutionListener;

/**
 * Executes tasks, providing the result to a registered {@link TaskExecutionListener}. If no listener is registered
 * when the result is available, it will be saved to be delivered to the next listener to be registered (and
 * subsequently cleared).
 * <p/>
 * Note that this class assumes that only one listener will be registered at a time. Registering a second listener
 * without first unregistering the first one is not supported and will produce surprising results.
 * <p/>
 * In an Android context, fragments or activities will typically register with this class in onStart(), and
 * unregister in onStop(). Additionally, {@link #cancel()}} should be called from onStop() if isRemoving() or
 * isFinishing() is true. Otherwise a stale result produced from one fragment or activity could be delivered
 * incorrectly to a different one. (Note that this scenario is still possible in the edge case of requesting a result
 * in one fragment or activity, then placing more fragments or activities on the stack before the result is return,
 * when one of the additional fragments or activities also requests a result from the same executor.)
 * <p/>
 *
 * TODO
 * Scope calls to all methods by an Object id (Class is a good candidate) to avoid the potential problems above.
 *
 * @param <T> The type of result to be delivered.
 */
public interface StickyTaskExecutor<T> {
    /**
     * Registers a listener to receive results requested by {@link #execute()}. Any sticky (undelivered) results
     * from previous calls to {@link #execute()} will be delivered immediately.
     */
    <L extends TaskExecutionListener<T>> void register(L executionListener);

    /**
     * Execute underlying task and deliver result to the currently registered listener. Any calls to this method
     * which are made while a previous call has not been delivered will be effectively ignored.
     */
    void execute();

    /**
     * Cancels a previous call to {@link #execute()}. The result will not be delivered to the current registered
     * listener nor any subsequently registered listener.
     */
    void cancel();

    /** Unregisters the current listener. */
    void unregisterLastListener();
}
