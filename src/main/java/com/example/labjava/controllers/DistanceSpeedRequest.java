package com.example.labjava.controllers;

import java.util.List;

public class DistanceSpeedRequest {
    private List<Double> distances;
    private List<Double> speeds;

    public List<Double> getDistances() {
        return distances;
    }

    public void setDistances(List<Double> distances) {
        this.distances = distances;
    }

    public List<Double> getSpeeds() {
        return speeds;
    }

    public void setSpeeds(List<Double> speeds) {
        this.speeds = speeds;
    }
}