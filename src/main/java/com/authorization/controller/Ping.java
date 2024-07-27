package com.authorization.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
class RootController {

    @GetMapping
    public String root() {
        return "Hello, this is a test route!";
    }
}
