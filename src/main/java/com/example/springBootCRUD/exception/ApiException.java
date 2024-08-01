package com.example.springBootCRUD.exception;

import org.springframework.http.HttpStatus;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ApiException {
    private String message;
    private int statusCode;
    private HttpStatus httpStatus;
    private ZonedDateTime timeStamp;

    public ApiException(){
        this.timeStamp = ZonedDateTime.now(ZoneId.of("Z"));
    }

    public ApiException(String message, int statusCode, HttpStatus httpStatus) {
        this();
        this.message = message;
        this.statusCode = statusCode;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getTimeStamp() {
        return timeStamp;
    }
}
