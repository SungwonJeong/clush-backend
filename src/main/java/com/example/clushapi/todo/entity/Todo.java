package com.example.clushapi.todo.entity;

import com.example.clushapi.common.Timestamped;
import com.example.clushapi.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
public class Todo extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Todo의 제목은 공백이 되어서는 안됩니다.")
    private String title;

    private String description;

    @Column(nullable = false)
    private boolean completed = false;

    @Column(nullable = false)
    private LocalDate todoDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Todo(String title, String description, boolean completed, LocalDate todoDate, User user) {
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.todoDate = todoDate;
        this.user = user;
    }

    public void update(String title, String description, LocalDate todoDate) {
        if (title != null) {
            this.title = title;
        }
        if (description != null) {
            this.description = description;
        }
        if (todoDate != null) {
            this.todoDate = todoDate;
        }
    }
}
