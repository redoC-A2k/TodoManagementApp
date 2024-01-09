package com.project.ManageTodoApp.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.project.ManageTodoApp.dto.UserDto;
import com.project.ManageTodoApp.entity.User;

public interface UserService extends UserDetailsService{
    User createUser(UserDto userDto, String password);
    // TODO: Add more methods
}
