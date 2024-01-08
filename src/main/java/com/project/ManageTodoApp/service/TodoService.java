package com.project.ManageTodoApp.service;

import com.project.ManageTodoApp.dto.TodoDto;
import com.project.ManageTodoApp.entity.Todo;

public interface TodoService {
    Todo createTodo(TodoDto todoDto);
    Todo getTodoById(Long id);
    TodoDto updateTodo(Long id, TodoDto todoDto);
    void deleteTodoById(Long id);
}
