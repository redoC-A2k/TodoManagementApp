package com.project.ManageTodoApp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.ManageTodoApp.dto.NewTodoDto;
import com.project.ManageTodoApp.dto.TodoDto;
import com.project.ManageTodoApp.entity.Todo;
import com.project.ManageTodoApp.entity.User;
import com.project.ManageTodoApp.exception.ResourceNotFoundException;
import com.project.ManageTodoApp.mapper.TodoMapper;
import com.project.ManageTodoApp.repository.TodoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final UserService userService;

    @Override
    public Todo createTodo(NewTodoDto todoDto, Long userId) {
        Todo todo = TodoMapper.mapNewTododtoToEntity(todoDto);
        User user = userService.findUserById(userId);
        todo.setUser(user);
        return todoRepository.save(todo);
    }

    @Override
    @Transactional
    public List<Todo> deleteTodoById(Long userId, Long todoId) {
        List<Todo> todos = userService.deleteTodoById(userId, todoId);
        todoRepository.deleteById(todoId);
        return todos;
    }

    @Override
    public Todo getTodoById(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id : " + id));
        return todo;
    }

    @Override
    @Transactional
    public TodoDto updateTodo(Long id, TodoDto todoDto) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id : " + id));
        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todoRepository.save(todo);
        return todoDto;
    }

    @Transactional
    @Override
    public List<Todo> getTodosOfUser(Long userId) {
        User user = userService.findUserById(userId);
        List<Todo> todos = user.getTodos();
        return todos;
    }
}
