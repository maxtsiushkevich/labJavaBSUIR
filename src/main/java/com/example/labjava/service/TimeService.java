package com.example.labjava.service;

import com.example.labjava.cache.Cache;
import com.example.labjava.controller.TimeController;
import com.example.labjava.exception.BadArgumentsException;
import com.example.labjava.exception.DivideException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class TimeService {
    private final Cache<List<Double>, Double> cache;

    public TimeService(Cache<List<Double>, Double> cache) {
        this.cache = cache;
    }

    public double calculate(double distance, double speed) throws DivideException {
        double time = distance / speed;
        if (Double.isInfinite(time) || Double.isNaN(time)) {
            throw new DivideException("Division by zero");
        }
        return time;
    }

    public void validate(double distance, double speed) throws BadArgumentsException {
        if (distance <= 0 || speed < 0) {
            throw new BadArgumentsException("Wrong parameters");
        }
    }

    public double findMin(List<Double> listOfTimes) {
        double min = 0;

        if (!listOfTimes.isEmpty()) {
            min = listOfTimes.stream().min(Double::compareTo).get();
        }
        return min;
    }

    public double findMax(List<Double> listOfTimes) {
        double max = 0;

        if (!listOfTimes.isEmpty()) {
            max = listOfTimes.stream().max(Double::compareTo).get();
        }
        return max;
    }

    public double findAverage(List<Double> listOfTimes) {
        double result = 0;

        if (!listOfTimes.isEmpty()) {
            result = listOfTimes.stream().mapToDouble(Double::doubleValue).average().getAsDouble();
        }
        return result;
    }
}