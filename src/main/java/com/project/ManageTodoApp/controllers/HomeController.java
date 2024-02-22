package com.project.ManageTodoApp.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class HomeController {
    
    @GetMapping("/home")
    public String homeString() {
        return new String("In home controller");
    }

    @GetMapping("/users/data")
    public ResponseEntity<String> getData() {
        return new ResponseEntity<>("Data can be accessed", HttpStatus.OK);
    }
}
