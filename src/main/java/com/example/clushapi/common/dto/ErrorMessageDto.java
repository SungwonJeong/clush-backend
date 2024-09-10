package com.example.clushapi.common.dto;


import com.example.clushapi.common.message.ErrorMessage;
import lombok.Builder;
import lombok.Getter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorMessageDto {

    private final String timestamp = LocalDateTime.now().toString();
    private final String message;
    private final HttpStatus httpStatus;

    public static ResponseEntity<ErrorMessageDto> toResponseEntity(ErrorMessage errorMessage) {
        return ResponseEntity
                .status(errorMessage.getHttpStatus())
                .body(ErrorMessageDto.builder()
                        .message(errorMessage.getMessage())
                        .httpStatus(errorMessage.getHttpStatus())
                        .build());
    }
}
