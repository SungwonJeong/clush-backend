package com.example.clushapi.common.exception;

import com.example.clushapi.common.dto.ErrorMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorMessageDto> handleCustomException(CustomException e) {
        logException(e);
        return ErrorMessageDto.toResponseEntity(e.getErrorMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageDto> handleNotValidFormatException(MethodArgumentNotValidException e) {
        logException(e);
        List<String> errorMessages = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage()).toList();

        String combinedErrorMessage = String.join(", ", errorMessages);
        log.debug("Validation errors: {}", combinedErrorMessage);

        ErrorMessageDto errorMessageDto = ErrorMessageDto.builder()
                .message(combinedErrorMessage)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();
        return ResponseEntity.status(errorMessageDto.getHttpStatus()).body(errorMessageDto);
    }

    private void logException(Exception e) {
        log.error(e.getClass().getSimpleName() + " thrown:" + e.getMessage());
        if (e instanceof CustomException customException) {
            log.error("[ERROR] {}", customException.getErrorMessage());
        }
    }
}
