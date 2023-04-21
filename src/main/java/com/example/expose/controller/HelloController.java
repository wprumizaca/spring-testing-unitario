package com.example.expose.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "Hola mundo";
    }
}
