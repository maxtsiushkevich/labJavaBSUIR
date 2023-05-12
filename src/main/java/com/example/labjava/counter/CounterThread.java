package com.example.labjava.counter;

import org.springframework.stereotype.Component;

@Component
public class CounterThread extends Thread {
    private static int counter = 0;

    public CounterThread() {
        super();
    }

    @Override
    public synchronized void start() {
        counter++;
    }

    public static synchronized int getCounter() {
        return counter;
    }
}
