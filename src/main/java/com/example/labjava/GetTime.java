package com.example.labjava;

import ch.qos.logback.core.encoder.EchoEncoder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.http.*;
@SpringBootApplication
@RestController
public class GetTime {
    private static final Logger logger = LogManager.getLogger(GetTime.class);
    @GetMapping("/time")
    public ResponseEntity<Double> calculateTime( @RequestParam double distance, @RequestParam double speed)
    {
        try
        {
            // валидация входных параметров
            if (distance <= 0 || speed < 0)
            {
                logger.info(String.format("Wrong parameters"));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            double time = distance / speed;

            if (Double.isInfinite(time) || Double.isNaN(time))
                throw new Exception();

            logger.info(String.format("Time for distance %f and speed %f is %f", distance, speed, time));
            return ResponseEntity.ok(time);
        }
        catch (Exception e)
        {
            logger.error("Other Error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}


