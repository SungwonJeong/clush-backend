package com.example.clushapi.calendar.dto;

import com.example.clushapi.calendar.entity.Calendar;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class CalendarResponseDto {

    private String title;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate calendarDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    private CalendarResponseDto(Calendar calendar) {
        this.title = calendar.getTitle();
        this.description = calendar.getDescription();
        this.calendarDate = calendar.getCalendarDate();
        this.startTime = calendar.getStartTime();
        this.endTime = calendar.getEndTime();
    }

    public static CalendarResponseDto from(Calendar calendar) {
        return new CalendarResponseDto(calendar);
    }
}
