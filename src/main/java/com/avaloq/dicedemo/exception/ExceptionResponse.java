package com.avaloq.dicedemo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ExceptionResponse {

    private LocalDateTime timeStamp;

    private String message;

    private String details;

    public ExceptionResponse(LocalDateTime timeStamp, String message, String details) {
        this.timeStamp = timeStamp;
        this.message = message;
        this.details = details;
    }
}
