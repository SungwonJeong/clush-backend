package com.example.clushapi.todo.dto;

import com.example.clushapi.todo.entity.Todo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TodoResponseDto {

    private String title;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate todoDate;

    private TodoResponseDto(Todo todo) {
        this.title = todo.getTitle();
        this.description = todo.getDescription();
        this.todoDate = todo.getTodoDate();
    }

    public static TodoResponseDto from(Todo todo) {
        return new TodoResponseDto(todo);
    }
}
