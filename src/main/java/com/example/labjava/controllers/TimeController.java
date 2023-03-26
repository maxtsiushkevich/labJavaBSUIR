package com.example.labjava.controllers;

import com.example.labjava.counter.Counter;
import com.example.labjava.cache.Cache;
import com.example.labjava.exceptions.BadArgumentsException;
import com.example.labjava.exceptions.DivideException;
import com.example.labjava.models.TimeModel;
import com.example.labjava.services.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final Cache<Double, Double> cache;

    @Autowired
    public TimeController(TimeService timeService, TimeModel timeModel, Cache<Double, Double> cache) {
        this.timeService = timeService;
        this.timeModel = timeModel;
        this.cache = cache;
    }

    @GetMapping("/time")
    public ResponseEntity<TimeResponse> calculateTime(@RequestParam double distance, @RequestParam double speed) throws BadArgumentsException, DivideException {
        Counter.inc();
        timeService.validate(distance, speed);
        logger.info("Check parametes");

        double time;
        if (!cache.contain(distance)) {
            logger.info("Calculate");
            time = timeService.calculate(distance, speed);
            logger.info("Push to cache");
            cache.push(distance, time);
        } else {
            logger.info("Get from cache");
            time = cache.get(distance);
        }

        logger.info(String.format("Time for distance %f and speed %f is %f", distance, speed, time));

        timeModel.setTime(time);
        timeModel.setDistance(distance);
        timeModel.setSpeed(speed);

        TimeResponse response = new TimeResponse(Counter.getCounter(), time);

        return ResponseEntity.ok(response);
    }
}








