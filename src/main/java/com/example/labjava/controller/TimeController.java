package com.example.labjava.controller;

import com.example.labjava.async.ResultAsync;
import com.example.labjava.cache.Cache;
import com.example.labjava.counter.CounterThread;
import com.example.labjava.exception.BadArgumentsException;
import com.example.labjava.exception.DivideException;
import com.example.labjava.model.TimeModel;
import com.example.labjava.response.CounterResponse;
import com.example.labjava.response.ResultResponse;
import com.example.labjava.response.TimeResponse;
import com.example.labjava.service.TimeControllerService;
import com.example.labjava.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TimeController {

    private static final Logger logger = LogManager.getLogger(TimeController.class);
    private final TimeService timeService;
    private final Cache<String, Double> cache;
    private final CounterThread counterThread;
    private final TimeControllerService timeControllerService;

    private final ResultAsync resultAsync;

    @Autowired
    public TimeController(TimeService timeService,
                          Cache<String, Double> cache,
                          CounterThread counterThread,
                          TimeControllerService timeControllerService,
                          ResultAsync resultAsync) {
        this.timeService = timeService;
        this.cache = cache;
        this.counterThread = counterThread;
        this.timeControllerService = timeControllerService;
        this.resultAsync = resultAsync;
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

        TimeResponse response = new TimeResponse(CounterThread.getCounter(), time);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/calculate")
    public ResponseEntity<?> calculate(@RequestBody List<TimeModel> listOfTimeModel) {

        List<Double> resultList = listOfTimeModel.stream().map(x -> {

            String cacheKey = String.format("%.2f_%.2f", x.getDistance(), x.getSpeed());
            double time = timeService.calculate(x.getDistance(), x.getSpeed());
            logger.info(String.format("Time for distance %f and speed %f is %f", x.getDistance(), x.getSpeed(), time));

            return time;
        }).collect(Collectors.toList());

        double max = timeService.findMax(resultList);
        double min = timeService.findMin(resultList);
        double avg = timeService.findAverage(resultList);

        ResultResponse calculation = new ResultResponse(resultList, max, min, avg);

        return ResponseEntity.ok(calculation);
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


    @PostMapping("/write")
    public Integer methodDecode(@RequestBody TimeModel request) throws BadArgumentsException {

        timeService.validate(request.getDistance(), request.getSpeed());

        TimeModel result = timeControllerService.findByDistanceAndSpeed(request.getDistance(), request.getSpeed());

        if (result == null) {
            int id = resultAsync.createHalfEmptyModel(request);
            resultAsync.computeAsync(id);

            return id;
        } else
            return result.getId();
    }

    @GetMapping("/find/{id}")
    public TimeModel result(@PathVariable("id") int id) {
        return timeControllerService.findOne(id);
    }

}