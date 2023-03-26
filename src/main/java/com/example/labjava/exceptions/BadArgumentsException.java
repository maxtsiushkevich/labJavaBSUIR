package com.example.labjava.exceptions;

import com.example.labjava.controllers.TimeController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Wrong parameters")
public class BadArgumentsException extends Exception {
    private static final Logger logger = LogManager.getLogger(TimeController.class);

    public BadArgumentsException(String message) {
        super(message);
        logger.error(message);
    }
}


