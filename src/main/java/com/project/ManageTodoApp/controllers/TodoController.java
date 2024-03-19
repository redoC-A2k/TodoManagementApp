package com.project.ManageTodoApp.controllers;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.ManageTodoApp.dto.NewTodoDto;
import com.project.ManageTodoApp.dto.TodoDto;
import com.project.ManageTodoApp.mapper.TodoMapper;
import com.project.ManageTodoApp.service.TodoService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class TodoController {
    private TodoService todoService;

    @PostMapping("/todo")
    public ResponseEntity<?> createTodo(@RequestAttribute Long userId, @RequestBody NewTodoDto newTodoDto) {
        todoService.createTodo(newTodoDto, userId);
        return ResponseEntity.ok("Todo created successfully");
    }

    @GetMapping("/todo")
    public ResponseEntity<?> getTodos(@RequestAttribute Long userId) {
        return new ResponseEntity<>(todoService.getTodosOfUser(userId), HttpStatus.OK);
    }

    @GetMapping("/todo/{todoId}")
    public ResponseEntity<?> getMethodName(@PathVariable Long todoId) {
        return new ResponseEntity<>(todoService.getTodoById(todoId), HttpStatus.OK);
    }

    @PutMapping("/todo/{todoId}")
    public ResponseEntity<?> updateTodo(@PathVariable Long todoId, @RequestBody TodoDto entity) {
        return new ResponseEntity<>(todoService.updateTodo(todoId, entity), HttpStatus.OK);
    }

    @DeleteMapping("/todo/{todoId}")
    public ResponseEntity<?> deleteTodoById(@PathVariable Long todoId, @RequestAttribute Long userId) {
        System.out.println("todoId: " + todoId + " userId: " + userId);
        return new ResponseEntity<>(todoService.deleteTodoById(userId, todoId), HttpStatus.OK);
    }
}
