package com.example.labjava.response;

import java.util.List;

public class StatResponse {
    private double minDist = 0.0, maxDist = 0.0, avrDist = 0.0;
    private double minSpeed = 0.0, maxSpeed = 0.0, avrSpeed = 0.0;

    public StatResponse(List<Double> distances, List<Double> speeds) {
        minDist = distances.stream().mapToDouble(Double::doubleValue).min().orElse(0.0);
        maxDist = distances.stream().mapToDouble(Double::doubleValue).max().orElse(0.0);
        avrDist = distances.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

        minSpeed = speeds.stream().mapToDouble(Double::doubleValue).min().orElse(0.0);
        maxSpeed = speeds.stream().mapToDouble(Double::doubleValue).max().orElse(0.0);
        avrSpeed = speeds.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    public double getMinDist() {
        return minDist;
    }

    public double getMaxDist() {
        return maxDist;
    }

    public double getAvrDist() {
        return avrDist;
    }

    public double getMinSpeed() {
        return minSpeed;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public double getAvrSpeed() {
        return avrSpeed;
    }

    public void setMinDist(double minDist) {
        this.minDist = minDist;
    }

    public void setMaxDist(double maxDist) {
        this.maxDist = maxDist;
    }

    public void setAvrDist(double avrDist) {
        this.avrDist = avrDist;
    }

    public void setMinSpeed(double minSpeed) {
        this.minSpeed = minSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setAvrSpeed(double avrSpeed) {
        this.avrSpeed = avrSpeed;
    }

}
