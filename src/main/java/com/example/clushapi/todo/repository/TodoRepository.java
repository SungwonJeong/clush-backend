package com.example.clushapi.todo.repository;

import com.example.clushapi.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query("SELECT t FROM Todo t WHERE t.user.id = :userId AND t.todoDate = :todoDate")
    List<Todo> findAllByUserIdAndTodoDate(@Param("userId") Long userId, @Param("todoDate") LocalDate todoDate);
}
