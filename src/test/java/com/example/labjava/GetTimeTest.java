package com.example.labjava;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;


class GetTimeTest {

    @Test
    void testCalculateTime() {
        GetTime getTime = new GetTime();
        double distance = 100;
        double speed = 50;
        ResponseEntity<Double> responseEntity = getTime.calculateTime(distance, speed);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, responseEntity.getBody());
    }

    @Test
    void testCalculateTimeInvalidInput() {
        GetTime getTime = new GetTime();
        double distance = -100;
        double speed = 50;
        ResponseEntity<Double> responseEntity = getTime.calculateTime(distance, speed);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void testCalculateTimeInternalError() {
        GetTime getTime = new GetTime();
        double distance = 100;
        double speed = 0;
        ResponseEntity<Double> responseEntity = getTime.calculateTime(distance, speed);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
}
