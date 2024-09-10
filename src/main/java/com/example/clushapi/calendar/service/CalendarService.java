package com.example.clushapi.calendar.service;

import com.example.clushapi.calendar.dto.CalendarRequestDto;
import com.example.clushapi.calendar.dto.CalendarResponseDto;
import com.example.clushapi.calendar.dto.CalendarSharingReqeustDto;
import com.example.clushapi.calendar.dto.CalendarSharingResponseDto;
import com.example.clushapi.calendar.dto.SharedCalendarResponseDto;
import com.example.clushapi.calendar.entity.Calendar;
import com.example.clushapi.calendar.entity.CalendarSharing;
import com.example.clushapi.calendar.repository.CalendarRepository;
import com.example.clushapi.calendar.repository.CalendarSharingRepository;
import com.example.clushapi.common.exception.CustomException;
import com.example.clushapi.user.entity.User;
import com.example.clushapi.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.clushapi.common.message.ErrorMessage.FORBIDDEN_ACCESS;
import static com.example.clushapi.common.message.ErrorMessage.NOT_FOUND_CALENDAR;
import static com.example.clushapi.common.message.ErrorMessage.NOT_FOUND_SHARED_USER;
import static com.example.clushapi.common.message.ErrorMessage.NOT_FOUND_SHARED_USER_AND_CALENDAR;
import static com.example.clushapi.common.message.ErrorMessage.NOT_FOUND_USER;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;
    private final UserRepository userRepository;
    private final CalendarSharingRepository calendarSharingRepository;

    // 캘린더 생성
    @Transactional
    public CalendarResponseDto createCalendar(Long userId, CalendarRequestDto calendarRequestDto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER)
        );

        Calendar calendar = calendarRequestDto.toEntity(user);
        calendarRepository.save(calendar);
        return CalendarResponseDto.from(calendar);
    }

    // 캘린더 조회
    @Transactional(readOnly = true)
    public CalendarResponseDto findCalendar(Long userId, Long calendarId) {
        Calendar calendar = checkAuthorization(userId, calendarId);
        return CalendarResponseDto.from(calendar);
    }

    // 캘린더 수정
    @Transactional
    public CalendarResponseDto updateCalendar(Long userId, Long calendarId, CalendarRequestDto calendarRequestDto) {
        Calendar calendar = checkAuthorization(userId, calendarId);
        calendar.update(calendarRequestDto.getTitle(),
                calendarRequestDto.getDescription(),
                calendarRequestDto.getCalendarDate(),
                calendarRequestDto.getStartTime(),
                calendarRequestDto.getEndTime());
        return CalendarResponseDto.from(calendar);
    }

    // 캘린더 삭제
    @Transactional
    public CalendarResponseDto deleteCalendar(Long userId, Long calendarId) {
        Calendar calendar = checkAuthorization(userId, calendarId);
        calendarSharingRepository.deleteByCalendarId(calendarId);
        calendarRepository.deleteById(calendarId);
        return CalendarResponseDto.from(calendar);
    }

    // 일정 공유 등록
    @Transactional
    public CalendarSharingResponseDto shareCalendar(Long ownerId, @Valid CalendarSharingReqeustDto calendarSharingReqeustDto) {
        Calendar calendar = checkAuthorization(ownerId, calendarSharingReqeustDto.getCalendarId());

        User sharedUser = userRepository.findById(calendarSharingReqeustDto.getSharedUserId()).orElseThrow(
                () -> new CustomException(NOT_FOUND_SHARED_USER)
        );

        CalendarSharing calendarSharing = CalendarSharing.builder()
                .sharedUser(sharedUser)
                .calendar(calendar)
                .build();
        calendarSharingRepository.save(calendarSharing);

        return CalendarSharingResponseDto.from(calendarSharing);
    }

    @Transactional(readOnly = true)
    public SharedCalendarResponseDto findSharedCalendar(Long calendarId, Long sharedUserId) {
        Calendar calendar = calendarRepository.findById(calendarId).orElseThrow(
                () -> new CustomException(NOT_FOUND_CALENDAR)
        );

        CalendarSharing calendarSharing =  calendarSharingRepository.findByCalendarIdAndSharedUserId(calendar.getId(), sharedUserId).orElseThrow(
                () -> new CustomException(FORBIDDEN_ACCESS)
        );

        CalendarResponseDto responseDto = CalendarResponseDto.from(calendar);
        return SharedCalendarResponseDto.of(calendarSharing, responseDto);
    }

    @Transactional
    public CalendarSharingResponseDto unshareCalendar(Long calendarId, Long sharedUserId, Long ownerId) {
        Calendar calendar = checkAuthorization(ownerId, calendarId);

        CalendarSharing calendarSharing = calendarSharingRepository.findByCalendarIdAndSharedUserId(calendarId, sharedUserId).orElseThrow(
                () -> new CustomException(NOT_FOUND_SHARED_USER_AND_CALENDAR)
        );
        calendarSharingRepository.deleteById(calendarSharing.getId());
        return CalendarSharingResponseDto.from(calendarSharing);
    }

    // 권한 체크
    private Calendar checkAuthorization(Long userId, Long calendarId) {
        Calendar calendar = calendarRepository.findById(calendarId).orElseThrow(
                () -> new CustomException(NOT_FOUND_CALENDAR)
        );

        if (!calendar.getUser().getId().equals(userId)) {
            throw new CustomException(FORBIDDEN_ACCESS);
        }

        return calendar;
    }
}
