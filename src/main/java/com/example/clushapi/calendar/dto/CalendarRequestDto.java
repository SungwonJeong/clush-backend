package com.example.clushapi.calendar.dto;

import com.example.clushapi.calendar.entity.Calendar;
import com.example.clushapi.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class CalendarRequestDto {

    private String title;
    private String description;

    @Schema(description = "캘린더 날짜", example = "2024-09-10")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate calendarDate;

    @Schema(description = "일정 시작 시간", example = "2024-09-10 10:00:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @Schema(description = "일정 종료 시간", example = "2024-09-10 12:00:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    public Calendar toEntity(User user) {
        return Calendar.builder()
                .title(title)
                .description(description)
                .calendarDate(calendarDate)
                .startTime(startTime)
                .endTime(endTime)
                .user(user)
                .build();
    }

}
