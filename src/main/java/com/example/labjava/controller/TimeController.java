package com.example.labjava.controller;

import com.example.labjava.cache.Cache;
import com.example.labjava.counter.CounterThread;
import com.example.labjava.exception.BadArgumentsException;
import com.example.labjava.exception.DivideException;
import com.example.labjava.model.TimeModel;
import com.example.labjava.request.DistanceSpeedRequest;
import com.example.labjava.response.CalcResponse;
import com.example.labjava.response.CounterResponse;
import com.example.labjava.response.StatResponse;
import com.example.labjava.response.TimeResponse;
import com.example.labjava.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@RestController
public class TimeController {

    private static final Logger logger = LogManager.getLogger(TimeController.class);
    private final TimeService timeService;
    private final TimeModel timeModel;
    private final Cache<String, Double> cache;
    private final CounterThread counterThread;

    @Autowired
    public TimeController(TimeService timeService,
                          TimeModel timeModel,
                          Cache<String, Double> cache,
                          CounterThread counterThread) {
        this.timeService = timeService;
        this.timeModel = timeModel;
        this.cache = cache;
        this.counterThread = counterThread;
    }

    @GetMapping("/count")
    public ResponseEntity<?> viewCounter() {
        logger.info(String.format("Counter %d", CounterThread.getCounter()));

        CounterResponse response = new CounterResponse(CounterThread.getCounter());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/time")
    public ResponseEntity<?> calculateTime(@RequestParam double distance, @RequestParam double speed) throws BadArgumentsException, DivideException {
        counterThread.start();

        timeService.validate(distance, speed);

        logger.info("Check parametes");

        String cacheKey = String.format("%.2f_%.2f", distance, speed);

        double time = getTime(distance, speed, cacheKey);

        logger.info(String.format("Time for distance %f and speed %f is %f. Counter %d", distance, speed, time, CounterThread.getCounter()));

        timeModel.setTime(time);
        timeModel.setDistance(distance);
        timeModel.setSpeed(speed);

        TimeResponse response = new TimeResponse(CounterThread.getCounter(), time);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/calculate")
    public ResponseEntity<?> calculate(@RequestBody DistanceSpeedRequest request) throws BadArgumentsException {
        List<TimeResponse> responses = new ArrayList<>();

        List<Double> distances = request.getDistances();
        List<Double> speeds = request.getSpeeds();

        timeService.validate(distances, speeds);
        logger.info("Check parametes");

        IntStream.range(0, Math.min(distances.size(), speeds.size()))
                .forEach(i -> {
                    counterThread.start();
                    double distance = distances.get(i);
                    double speed = speeds.get(i);
                    double time;

                    try {

                        String cacheKey = String.format("%.2f_%.2f", distance, speed);
                        time = getTime(distance, speed, cacheKey);

                        logger.info(String.format("Time for distance %f and speed %f is %f. Counter %d", distance, speed, time, CounterThread.getCounter()));

                        TimeResponse response = new TimeResponse(CounterThread.getCounter(), time);
                        responses.add(response);

                    } catch (DivideException e) {
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
                    }

                });

        StatResponse statResponse = new StatResponse(distances, speeds);

        CalcResponse calculationResponse = new CalcResponse(statResponse, responses);

        return ResponseEntity.ok(calculationResponse);

    }

    private double getTime(@RequestParam double distance, @RequestParam double speed, String cacheKey) throws DivideException {
        double time;
        if (cache.contain(cacheKey)) {
            logger.info("Get from cache");
            time = cache.get(cacheKey);
        } else {
            logger.info("Calculate");
            time = timeService.calculate(distance, speed);
            logger.info("Push to cache");
            cache.push(cacheKey, time);
        }
        return time;
    }
}