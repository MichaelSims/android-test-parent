package com.example.events;

public class ResultEvent {
    private final Object requestId;

    public ResultEvent(Object requestId) {
        this.requestId = requestId;
    }

    public Object getRequestId() {
        return requestId;
    }
}
