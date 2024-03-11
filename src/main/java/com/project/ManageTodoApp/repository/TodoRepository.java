package com.project.ManageTodoApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.ManageTodoApp.entity.Todo;

import java.util.List;


public interface TodoRepository extends JpaRepository<Todo, Long>{
    // @Query("Select t from Todo t where t.userId=:userId")
    // List<Todo> findTodosByUser(Long userId);
}
