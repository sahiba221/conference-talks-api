package com.example.demo1_conference_talks_api.exception;

public class DuplicateResourceException extends  RuntimeException{
    public DuplicateResourceException(String  message){
        super(message);
    }
}
