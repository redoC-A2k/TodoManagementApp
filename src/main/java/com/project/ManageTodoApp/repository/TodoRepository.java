package com.project.ManageTodoApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.ManageTodoApp.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long>{
    
}
