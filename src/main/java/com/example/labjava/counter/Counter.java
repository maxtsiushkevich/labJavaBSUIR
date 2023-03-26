package com.example.labjava.counter;

import org.springframework.stereotype.Component;

@Component
public class Counter {
    private static int counter = 0;

    synchronized public static void inc() {
        counter++;
    }

    synchronized public static int getCounter() {
        return counter;
    }

}
