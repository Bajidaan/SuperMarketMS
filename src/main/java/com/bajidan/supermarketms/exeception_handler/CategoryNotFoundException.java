package com.bajidan.supermarketms.exeception_handler;

public class CategoryNotFoundException extends RuntimeException {


    public CategoryNotFoundException(String message) {
        super(message);
    }
}
