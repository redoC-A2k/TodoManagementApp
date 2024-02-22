package com.project.ManageTodoApp.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class NewTodoDto {
    private String title;
    private String description;
}
