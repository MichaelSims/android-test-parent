package com.example;

public class IdGenerator {
    private int nextId = 0;
    private final Object lock = new Object();

    public int getUniqueId() {
        synchronized (lock) {
            int id = nextId;
            nextId++;
            return id;
        }
    }
}
