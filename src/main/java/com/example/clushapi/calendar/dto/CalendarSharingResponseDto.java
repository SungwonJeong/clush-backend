package com.example.clushapi.calendar.dto;

import com.example.clushapi.calendar.entity.CalendarSharing;
import lombok.Getter;

@Getter
public class CalendarSharingResponseDto {

    private Long sharedUserId;
    private Long calendarId;

    private CalendarSharingResponseDto(CalendarSharing sharing) {
        this.sharedUserId = sharing.getSharedUser().getId();
        this.calendarId = sharing.getCalendar().getId();
    }

    public static CalendarSharingResponseDto from(CalendarSharing sharing) {
        return new CalendarSharingResponseDto(sharing);
    }
}
