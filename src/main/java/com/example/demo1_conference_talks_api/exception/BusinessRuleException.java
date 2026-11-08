package com.example.demo1_conference_talks_api.exception;

public class BusinessRuleException extends RuntimeException {
    public BusinessRuleException(String message){
        super(message);
    }

}
