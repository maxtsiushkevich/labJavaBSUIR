package com.example.labjava;

import com.example.labjava.exception.BadArgumentsException;
import com.example.labjava.exception.DivideException;
import com.example.labjava.service.TimeService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class TimeModelControllerTest {

    @Test
    void testCalculateTime() throws DivideException {
        TimeService timeService = new TimeService();
        double distance = 100;
        double speed = 50;
        double result = timeService.calculate(distance, speed);
        assertEquals(2, result);
    }

    @Test
    void testCalculateTimeInvalidInput() {
        TimeService timeService = new TimeService();
        assertThrows(BadArgumentsException.class, () -> {
            timeService.validate(100, -1);
        });
    }

    @Test
    void testCalculateTimeDivisionByZero() {
        TimeService timeService = new TimeService();
        assertThrows(DivideException.class, () -> {
            timeService.calculate(100, 0);
        });
    }
}
