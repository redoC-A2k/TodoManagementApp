package com.project.ManageTodoApp.mapper;

import com.project.ManageTodoApp.dto.TodoDto;
import com.project.ManageTodoApp.entity.Todo;

public class TodoMapper {
    public static Todo mapTododtoToEntity(TodoDto todoDto){
        Todo todo = new Todo();
        todo.setId(todoDto.getId());
        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        return todo;
    }

    public static TodoDto mapTodoEntityToDto(Todo todo){
        TodoDto todoDto = new TodoDto();
        todoDto.setId(todo.getId());
        todoDto.setTitle(todo.getTitle());
        todoDto.setDescription(todo.getDescription());
        return todoDto;
    }
}
