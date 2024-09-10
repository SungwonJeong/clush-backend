package com.example.clushapi.calendar.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CalendarSharingReqeustDto {

    @NotNull(message = "공유할 사용자 ID는 필수입니다.")
    private Long sharedUserId;

    @NotNull(message = "캘린더 ID는 필수입니다.")
    private Long calendarId;
}
