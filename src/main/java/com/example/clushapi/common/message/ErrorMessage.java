package com.example.clushapi.common.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    NOT_FOUND_USER("존재하지 않은 사용자입니다", HttpStatus.NOT_FOUND),
    DUPLICATED_USERNAME("중복된 이름이 존재합니다.", HttpStatus.BAD_REQUEST),
    NOT_VALID_PASSWORD("비밀번호가 일치하지 않습니다", HttpStatus.BAD_REQUEST),
    LOGIN_REQUIRED("로그인을 해주세요", HttpStatus.UNAUTHORIZED),
    FORBIDDEN_ACCESS("해당 작업에 대한 권한이 없습니다", HttpStatus.FORBIDDEN),

    NOT_FOUND_TODO("Todo를 찾을 수 없습니다", HttpStatus.NOT_FOUND),

    NOT_FOUND_CALENDAR("캘린더를 찾을 수 없습니다", HttpStatus.NOT_FOUND),

    NOT_FOUND_SHARED_USER("공유할 사용자를 찾지 못했습니다", HttpStatus.NOT_FOUND),
    NOT_FOUND_SHARED_USER_AND_CALENDAR("공유된 캘린더를 찾지 못했습니다", HttpStatus.NOT_FOUND);;

    private final String message;
    private final HttpStatus httpStatus;
}
