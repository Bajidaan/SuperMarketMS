package com.bajidan.supermarketms.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HttpMessageUtil {
    public static ResponseEntity<String> badRequest(String message) {

        return new ResponseEntity<>(String.format("\"message\": \"%s\"", message), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<String> successful(String message) {
        return ResponseEntity.ok(String.format("\"message\": \"%s\"", message));
    }
     public static ResponseEntity<String> getResponse(String message, HttpStatus httpStatus) {
         return new ResponseEntity<>(String.format("\"message\": \"%s\"", message), httpStatus);
        }

    public static ResponseEntity<String> internalServerError(String message) {
        return new ResponseEntity<>(String.format("\"message\": \"%s\"", message), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    public static ResponseEntity<String> internalServerError() {

        return new ResponseEntity<>(String.format("\"message\": \"%s\"", "Something went wrong"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    public static ResponseEntity<String> unAuthorized(String message) {
            return new ResponseEntity<>(String.format("\"message\": \"%s\"", message), HttpStatus.UNAUTHORIZED);
        }





}
