package com.example.events;

public class GeneralEvent {
    private final int instanceId;

    public GeneralEvent(int instanceId) {
        this.instanceId = instanceId;
    }

    public int getInstanceId() {
        return instanceId;
    }
}
