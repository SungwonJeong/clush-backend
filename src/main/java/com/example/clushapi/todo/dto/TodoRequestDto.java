package com.example.clushapi.todo.dto;

import com.example.clushapi.todo.entity.Todo;
import com.example.clushapi.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
public class TodoRequestDto {

    @NotBlank(message = "Todo의 제목은 공백이 되어서는 안됩니다.")
    private String title;
    private String description;

    @Schema(description = "Todo 날짜", example = "2024-09-10")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate todoDate;

    public Todo toEntity(User user) {
        return Todo.builder()
                .title(title)
                .description(description)
                .todoDate(todoDate)
                .user(user)
                .build();
    }
}
