package com.project.ManageTodoApp.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.ManageTodoApp.dto.UserDto;
import com.project.ManageTodoApp.entity.Todo;
import com.project.ManageTodoApp.entity.User;
import com.project.ManageTodoApp.exception.ResourceNotFoundException;
import com.project.ManageTodoApp.mapper.UserMapper;
import com.project.ManageTodoApp.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username : " + username));
        return userDetails;
    }

    @Override
    public User createUser(UserDto userDto, String password) {
        User user = UserMapper.mapUserDtoToEntity(userDto);
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(password));
        user.setTodos(new ArrayList<Todo>());
        userRepository.save(user);
        return user;
    }

}
