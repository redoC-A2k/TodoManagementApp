package com.project.ManageTodoApp.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.project.ManageTodoApp.dto.UserDto;
import com.project.ManageTodoApp.entity.User;
import com.project.ManageTodoApp.entity.Todo;

public interface UserService extends UserDetailsService{
    User createUser(UserDto userDto, String password);
    User findUserById(Long id);
    User findUserByEmail(String email);
    List<Todo> deleteTodoById(Long userId, Long id);
    // TODO: Add more methods
}
