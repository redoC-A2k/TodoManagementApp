package com.project.ManageTodoApp.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class HomeController {
    
    @GetMapping("/home")
    public String homeString() {
        return new String("In home controller");
    }

    
}
