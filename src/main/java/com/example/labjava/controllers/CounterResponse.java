package com.example.labjava.controllers;

public class CounterResponse {
    private int counter;

    public CounterResponse(int counter) {
        this.counter = counter;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}