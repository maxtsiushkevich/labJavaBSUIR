package com.example.labjava.counter;

import org.springframework.stereotype.Component;

@Component
public class CounterThread extends Thread {
    private static int counter = 0;

    public CounterThread() {
        super();
    }

    @Override
    synchronized public void start() {
        counter++;
    }

    synchronized public static int getCounter() {
        return counter;
    }
}
