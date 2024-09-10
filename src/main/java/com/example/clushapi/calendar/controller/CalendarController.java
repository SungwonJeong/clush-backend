package com.example.clushapi.calendar.controller;

import com.example.clushapi.calendar.dto.CalendarRequestDto;
import com.example.clushapi.calendar.dto.CalendarResponseDto;
import com.example.clushapi.calendar.dto.CalendarSharingReqeustDto;
import com.example.clushapi.calendar.dto.CalendarSharingResponseDto;
import com.example.clushapi.calendar.dto.SharedCalendarResponseDto;
import com.example.clushapi.calendar.service.CalendarService;
import com.example.clushapi.common.dto.ResponseMessageDto;
import com.example.clushapi.common.exception.CustomException;
import com.example.clushapi.user.dto.UserResponseDto;
import com.example.clushapi.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.clushapi.common.message.ErrorMessage.LOGIN_REQUIRED;
import static com.example.clushapi.common.message.ResponseMessage.CREATE_CALENDAR_SUCCESS;
import static com.example.clushapi.common.message.ResponseMessage.DELETE_CALENDAR_SUCCESS;
import static com.example.clushapi.common.message.ResponseMessage.FIND_CALENDAR_SUCCESS;
import static com.example.clushapi.common.message.ResponseMessage.FIND_SHARED_CALENDAR_SUCCESS;
import static com.example.clushapi.common.message.ResponseMessage.SHARE_CALENDAR_SUCCESS;
import static com.example.clushapi.common.message.ResponseMessage.UNSHARED_CALENDAR_SUCCESS;
import static com.example.clushapi.common.message.ResponseMessage.UPDATE_CALENDAR_SUCCESS;

@Tag(name = "3. Calendar API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/calendar")
public class CalendarController {

    private final CalendarService calendarService;
    private final UserService userService;

    @Operation(summary = "1. Calendar 생성 API", tags = {"3. Calendar API"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "캘린더가 성공적으로 생성되었습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청: 날짜가 없거나 날짜 형식이 잘못되었습니다."),
            @ApiResponse(responseCode = "401", description = "로그인을 해주세요"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다")
    })
    @PostMapping
    public ResponseEntity<ResponseMessageDto<CalendarResponseDto>> createCalendar(@Valid  @RequestBody CalendarRequestDto calendarRequestDto, HttpSession session) {
        UserResponseDto authenticatedUser = getAuthenticatedUser(session);

        CalendarResponseDto response = calendarService.createCalendar(authenticatedUser.getUserId(), calendarRequestDto);
        return ResponseMessageDto.toResponseEntity(CREATE_CALENDAR_SUCCESS, response);
    }

    @Operation(summary = "2. 특정 Calendar 조회 API", tags = {"3. Calendar API"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "캘린더 조회가 성공적으로 완료되었습니다."),
            @ApiResponse(responseCode = "401", description = "로그인을 해주세요"),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "캘린더를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다")
    })
    @GetMapping("/{calendarId}")
    public ResponseEntity<ResponseMessageDto<CalendarResponseDto>> findCalendar(@PathVariable Long calendarId, HttpSession session) {
        UserResponseDto authenticatedUser = getAuthenticatedUser(session);

        CalendarResponseDto response = calendarService.findCalendar(authenticatedUser.getUserId(), calendarId);
        return ResponseMessageDto.toResponseEntity(FIND_CALENDAR_SUCCESS, response);
    }

    @Operation(summary = "3. Calendar 수정 API", tags = {"3. Calendar API"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "캘린더가 성공적으로 수정되었습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청: 날짜가 없거나 날짜 형식이 잘못되었습니다."),
            @ApiResponse(responseCode = "401", description = "로그인을 해주세요"),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "캘린더를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다")
    })
    @PatchMapping("/{calendarId}")
    public ResponseEntity<ResponseMessageDto<CalendarResponseDto>> updateCalendar(@PathVariable Long calendarId, @Valid @RequestBody CalendarRequestDto calendarRequestDto, HttpSession session) {
        UserResponseDto authenticatedUser = getAuthenticatedUser(session);

        CalendarResponseDto response = calendarService.updateCalendar(authenticatedUser.getUserId(), calendarId, calendarRequestDto);
        return ResponseMessageDto.toResponseEntity(UPDATE_CALENDAR_SUCCESS, response);
    }

    @Operation(summary = "4. Calendar 삭제 API", tags = {"3. Calendar API"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "캘린더가 성공적으로 삭제되었습니다."),
            @ApiResponse(responseCode = "401", description = "로그인을 해주세요"),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "캘린더를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다")
    })
    @DeleteMapping("/{calendarId}")
    public ResponseEntity<ResponseMessageDto<CalendarResponseDto>> deleteCalendar(@PathVariable Long calendarId, HttpSession session) {
        UserResponseDto authenticatedUser = getAuthenticatedUser(session);

        CalendarResponseDto response = calendarService.deleteCalendar(authenticatedUser.getUserId(), calendarId);
        return ResponseMessageDto.toResponseEntity(DELETE_CALENDAR_SUCCESS, response);
    }

    @Operation(summary = "5. 캘린더 공유 API", tags = {"3. Calendar API"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "캘린더가 성공적으로 공유되었습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청: 캘린더 id와 공유될 사용자 id를 꼭 넣어주세요."),
            @ApiResponse(responseCode = "401", description = "로그인을 해주세요"),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "캘린더나 사용자 혹은 공유될 사용자를 찾을 수 없습니다.")
    })
    @PostMapping("/share")
    public ResponseEntity<ResponseMessageDto<CalendarSharingResponseDto>> shareCalendar(@Valid @RequestBody CalendarSharingReqeustDto calendarSharingReqeustDto, HttpSession session) {
        UserResponseDto authenticatedUser = getAuthenticatedUser(session);
        CalendarSharingResponseDto response = calendarService.shareCalendar(authenticatedUser.getUserId(), calendarSharingReqeustDto);
        return ResponseMessageDto.toResponseEntity(SHARE_CALENDAR_SUCCESS, response);
    }

    @Operation(summary = "6. 공유된 캘린더 조회 API", tags = {"3. Calendar API"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공유된 캘린더를 성공적으로 조회하였습니다"),
            @ApiResponse(responseCode = "401", description = "로그인을 해주세요"),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "캘린더나 사용자를 찾을 수 없습니다.")
    })
    @GetMapping("/share/{calendarId}")
    public ResponseEntity<ResponseMessageDto<SharedCalendarResponseDto>> findSharedCalendar(@PathVariable Long calendarId, HttpSession session) {
        UserResponseDto authenticatedUser = getAuthenticatedUser(session);
        SharedCalendarResponseDto response = calendarService.findSharedCalendar(calendarId, authenticatedUser.getUserId());
        return ResponseMessageDto.toResponseEntity(FIND_SHARED_CALENDAR_SUCCESS, response);
    }

    @Operation(summary = "7. 캘린더 공유 해제 API", tags = {"3. Calendar API"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "캘린더 공유가 성공적으로 해제되었습니다."),
            @ApiResponse(responseCode = "401", description = "로그인을 해주세요"),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
            @ApiResponse(responseCode = "404", description = "캘린더 및 공유된 캘린더를 찾을 수 없습니다.")
    })
    @DeleteMapping("/unshare/{calendarId}/{sharedUserId}")
    public ResponseEntity<ResponseMessageDto<CalendarSharingResponseDto>> unshareCalendar(@PathVariable Long calendarId, @PathVariable Long sharedUserId, HttpSession session) {
        UserResponseDto authenticatedUser = getAuthenticatedUser(session);
        CalendarSharingResponseDto response = calendarService.unshareCalendar(calendarId, sharedUserId, authenticatedUser.getUserId());
        return ResponseMessageDto.toResponseEntity(UNSHARED_CALENDAR_SUCCESS, response);
    }

    private UserResponseDto getAuthenticatedUser(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            throw new CustomException(LOGIN_REQUIRED);
        }
        return userService.findById(userId);
    }
}
