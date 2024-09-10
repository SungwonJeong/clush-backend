package com.example.clushapi.calendar.dto;

import com.example.clushapi.calendar.entity.CalendarSharing;
import lombok.Getter;

@Getter
public class SharedCalendarResponseDto {

    private Long sharedUserId;
    private String sharedUsername;
    private Long calendarId;
    private CalendarResponseDto calendar;

    private SharedCalendarResponseDto(CalendarSharing calendarSharing, CalendarResponseDto calendarDto) {
        this.sharedUserId = calendarSharing.getSharedUser().getId();
        this.sharedUsername = calendarSharing.getSharedUser().getUsername();
        this.calendarId = calendarSharing.getCalendar().getId();
        this.calendar = calendarDto;
    }

    public static SharedCalendarResponseDto of(CalendarSharing calendarSharing, CalendarResponseDto calendarDto) {
        return new SharedCalendarResponseDto(calendarSharing, calendarDto);
    }
}
