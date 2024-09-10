package com.example.clushapi.calendar.entity;

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
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Calendar extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calendar_id")
    private Long id;

    private String title;

    private String description;

    @Column(nullable = false)
    @NotNull(message = "날짜는 null이 될 수 없습니다.")
    private LocalDate calendarDate;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Calendar(String title, String description, LocalDate calendarDate, LocalDateTime startTime, LocalDateTime endTime, User user) {
        this.title = title;
        this.description = description;
        this.calendarDate = calendarDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.user = user;
    }

    public void update(String title, String description, LocalDate calendarDate, LocalDateTime startTime, LocalDateTime endTime) {
        if (title != null) {
            this.title = title;
        }
        if (description != null) {
            this.description = description;
        }
        if (calendarDate != null) {
            this.calendarDate = calendarDate;
        }
        if (startTime != null) {
            this.startTime = startTime;
        }
        if (endTime != null) {
            this.endTime = endTime;
        }
    }
}
