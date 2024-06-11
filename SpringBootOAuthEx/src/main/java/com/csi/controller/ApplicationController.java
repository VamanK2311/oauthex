package com.csi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ApplicationController {

    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Welcome To Fintech");
    }

    @GetMapping("/services")
    public ResponseEntity<String> sayServices(){
        return ResponseEntity.ok("Full Stack Java");
    }

}
