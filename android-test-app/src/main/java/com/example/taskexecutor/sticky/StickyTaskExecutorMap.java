package com.example.taskexecutor.sticky;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.ForwardingLoadingCache;
import com.google.common.cache.LoadingCache;

public class StickyTaskExecutorMap<T> extends ForwardingLoadingCache<Object, StickyTaskExecutor<T>> {
    private final LoadingCache<Object, StickyTaskExecutor<T>> delegateCache;

    public StickyTaskExecutorMap(final StickyTaskExecutorFactory<T> executorFactory, int maxSize) {
        final CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
        cacheBuilder.maximumSize(maxSize);
        delegateCache = cacheBuilder.build(new CacheLoader<Object, StickyTaskExecutor<T>>() {
            @Override
            public StickyTaskExecutor<T> load(Object key) throws Exception {
                return executorFactory.create(key);
            }
        });
    }

    @Override
    protected LoadingCache<Object, StickyTaskExecutor<T>> delegate() {
        return delegateCache;
    }
}
