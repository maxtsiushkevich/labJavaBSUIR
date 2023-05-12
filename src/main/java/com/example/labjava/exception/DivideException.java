package com.example.labjava.exception;

import com.example.labjava.controller.TimeController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Division by zero")
public class DivideException extends RuntimeException {
    private static final Logger logger = LogManager.getLogger(TimeController.class);

    public DivideException(String message) {
        super(message);
        logger.error(message);
    }
}