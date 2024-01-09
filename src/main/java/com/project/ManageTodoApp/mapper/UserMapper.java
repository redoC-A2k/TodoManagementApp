package com.project.ManageTodoApp.mapper;

import com.project.ManageTodoApp.dto.UserDto;
import com.project.ManageTodoApp.entity.User;

public class UserMapper {
    public static User mapUserDtoToEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        return user;
    }

    public static UserDto mapUserEntityToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        return userDto;
    }
}
