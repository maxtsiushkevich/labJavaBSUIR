package com.example.labjava.services;

import com.example.labjava.exceptions.BadArgumentsException;
import com.example.labjava.exceptions.DivideException;
import org.springframework.stereotype.Component;

@Component
public class TimeService {
    public double calculate(double distance, double speed) throws DivideException {
        double time = distance / speed;
        if (Double.isInfinite(time) || Double.isNaN(time))
            throw new DivideException("Division by zero");
        return time;
    }

    public void validate(double distance, double speed) throws BadArgumentsException {
        if (distance <= 0 || speed < 0) {
            throw new BadArgumentsException("Wrong parameters");
        }
    }

}