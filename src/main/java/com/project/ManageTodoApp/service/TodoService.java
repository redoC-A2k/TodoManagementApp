package com.project.ManageTodoApp.service;

import java.util.List;

import com.project.ManageTodoApp.dto.TodoDto;

import com.project.ManageTodoApp.dto.NewTodoDto;
import com.project.ManageTodoApp.entity.Todo;

public interface TodoService {
    Todo createTodo(NewTodoDto newTodoDto,Long userId);
    Todo getTodoById(Long id);
    TodoDto updateTodo(Long id, TodoDto todoDto);
    List<Todo> deleteTodoById(Long userId, Long id);
    List<Todo> getTodosOfUser(Long userId);
}
