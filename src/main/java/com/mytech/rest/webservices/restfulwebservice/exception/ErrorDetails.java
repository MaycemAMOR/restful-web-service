package com.mytech.rest.webservices.restfulwebservice.exception;

import java.time.LocalDateTime;

public record ErrorDetails(LocalDateTime timeStamp, String message, String details) {

}
