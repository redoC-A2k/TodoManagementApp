package com.project.ManageTodoApp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ManageTodoApp.dto.NewTodoDto;
import com.project.ManageTodoApp.service.TodoService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class TodoController {
    private TodoService todoService;

    @PostMapping("/todo")
    public ResponseEntity<?> createTodo(@RequestAttribute Long userId, @RequestBody NewTodoDto newTodoDto) {
        // todoService.createTodo(todoDto, userId);
        System.out.println(userId);
        System.out.println(newTodoDto);
        return ResponseEntity.ok("Todo created successfully");
    }

    @GetMapping("/todo")
    public ResponseEntity<?> getTodos(@PathVariable String userId) {
        // return ResponseEntity.ok(todoService.getTodos(userId));
        return ResponseEntity.ok("get data");
    }
}
