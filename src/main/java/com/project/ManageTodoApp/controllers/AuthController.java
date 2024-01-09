package com.project.ManageTodoApp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ManageTodoApp.dto.UserDto;
import com.project.ManageTodoApp.service.UserService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Setter
@Getter
class NewUserRequestBody {
    private String email;
    private String password;
    private String name;
}


@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AuthController {
    private UserService userService;

    @PostMapping("/users/signup")
    public ResponseEntity<String> signup(@RequestBody NewUserRequestBody userSourcBody) {
        // System.out.println(userSourcBody);
        UserDto userDto = new UserDto();
        userDto.setEmail(userSourcBody.getEmail());
        userDto.setName(userSourcBody.getName());
        userService.createUser(userDto, userSourcBody.getPassword());
        return new ResponseEntity<>("Signup Successfull",HttpStatus.CREATED);
    }

    @GetMapping("/users/data")
    public ResponseEntity<String> getData() {
        return new ResponseEntity<>("Data can be accessed",HttpStatus.OK);
    }
}
