package com.example.labjava.controllers;

public class TimeResponse {
    private int result;
    private double time;

    public TimeResponse(int result, double time) {
        this.result = result;
        this.time = time;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }
}