package com.example.clushapi.common.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseMessage {

    SIGNUP_SUCCESS("회원가입에 성공하였습니다", HttpStatus.CREATED),
    LOGIN_SUCCESS("로그인에 성공하였습니다", HttpStatus.OK),
    ALREADY_LOGIN_SUCCESS("이미 로그인이 되어있습니다", HttpStatus.OK),

    CREATE_TODO_SUCCESS("Todo 생성에 성공하였습니다", HttpStatus.CREATED),
    FIND_TODO_SUCCESS("Todo 조회에 성공하였습니다", HttpStatus.OK),
    FIND_TODO_List_SUCCESS("Todo 리스트 조회에 성공하였습니다", HttpStatus.OK),
    UPDATE_TODO_SUCCESS("Todo 변경에 성공하였습니다", HttpStatus.OK),
    DELETE_TODO_SUCCESS("Todo 삭제에 성공하였습니다", HttpStatus.OK),

    CREATE_CALENDAR_SUCCESS("캘린더 생성에 성공하였습니다", HttpStatus.CREATED),
    FIND_CALENDAR_SUCCESS("캘린더 조회에 성공하였습니다", HttpStatus.OK),
    FIND_CALENDAR_List_SUCCESS("캘린더 리스트 조회에 성공하였습니다", HttpStatus.OK),
    UPDATE_CALENDAR_SUCCESS("캘린더 변경에 성공하였습니다", HttpStatus.OK),
    DELETE_CALENDAR_SUCCESS("캘린더 삭제에 성공하였습니다", HttpStatus.OK),
    SHARE_CALENDAR_SUCCESS("캘린더가 성공적으로 공유되었습니다.", HttpStatus.OK),
    FIND_SHARED_CALENDAR_SUCCESS("공유된 캘린더를 성공적으로 조회하였습니다.", HttpStatus.OK),
    UNSHARED_CALENDAR_SUCCESS("캘린더 공유가 성공적으로 취소되었습니다.", HttpStatus.OK);

    private final String message;
    private final HttpStatus httpStatus;
}
