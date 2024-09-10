package com.example.clushapi.calendar.dto;

import com.example.clushapi.calendar.entity.Calendar;
import com.example.clushapi.user.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class CalendarRequestDto {

    private String title;
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "날짜는 null이 될 수 없습니다.")
    private LocalDate calendarDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

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
