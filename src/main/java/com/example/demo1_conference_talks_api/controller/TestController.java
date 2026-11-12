package com.example.demo1_conference_talks_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/public/test")
    public String publicHello() {
        return "Public endpoint";
    } // everyone can access

    @GetMapping("/user/test")
    public String userHello() {
        return "Hello USER";
    } // users can access

    @GetMapping("/admin/test")
    public String adminHello() {
        return "Hello ADMIN";
    } // admins can access
}
