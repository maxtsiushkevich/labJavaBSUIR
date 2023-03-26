package com.example.labjava.controllers;

import com.example.labjava.exceptions.BadArgumentsException;
import com.example.labjava.exceptions.DivideException;
import com.example.labjava.models.TimeModel;
import com.example.labjava.services.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
public class TimeController {

    private static final Logger logger = LogManager.getLogger(TimeController.class);
    private final TimeService timeService;
    private final TimeModel timeModel;

    @Autowired
    public TimeController(TimeService timeService, TimeModel timeModel) {
        this.timeService = timeService;
        this.timeModel = timeModel;
    }

    @GetMapping("/time")
    public ResponseEntity<?> calculateTime(@RequestParam double distance, @RequestParam double speed) throws BadArgumentsException, DivideException {
        timeService.validate(distance, speed);
        logger.info("Check parametes");

        double time = timeService.calculate(distance, speed);
        logger.info(String.format("Time for distance %f and speed %f is %f", distance, speed, time));

        timeModel.setTime(time);
        timeModel.setDistance(distance);
        timeModel.setSpeed(speed);

        return new ResponseEntity<>("time: " + time, HttpStatus.OK);
    }
}


