package com.project.ManageTodoApp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

import com.project.ManageTodoApp.dto.TodoDto;
import com.project.ManageTodoApp.service.TodoService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class TodoController {
    private TodoService todoService;

    @PostMapping("/user/{userId}/todo")
    public ResponseEntity<?> createTodo(@PathVariable String userId, @RequestBody  TodoDto todoDto) {
        todoService.createTodo(todoDto);
        return ResponseEntity.ok("Todo created successfully");
    }

    @GetMapping("/user/{userId}/todo")
    public ResponseEntity<?> getTodos(@PathVariable String userId) {
        // return ResponseEntity.ok(todoService.getTodos(userId));
        return ResponseEntity.ok("get data");
    }
}
