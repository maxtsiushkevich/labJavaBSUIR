package com.example.labjava.services;

import com.example.labjava.exceptions.BadArgumentsException;
import com.example.labjava.exceptions.DivideException;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class TimeService {
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

    public void validate(List<Double> distances, List<Double> speeds) throws BadArgumentsException {
        if (distances == null || speeds == null) {
            throw new BadArgumentsException("Lists cannot be null");
        }

        if (distances.size() != speeds.size()) {
            throw new BadArgumentsException("Lists sizes do not match");
        }

        for (int i = 0; i < distances.size(); i++) {
            if (distances.get(i) <= 0 || speeds.get(i) < 0) {
                throw new BadArgumentsException("Wrong parameters at index " + i);
            }
        }
    }

}