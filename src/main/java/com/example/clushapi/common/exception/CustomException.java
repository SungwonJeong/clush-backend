package com.example.clushapi.common.exception;

import com.example.clushapi.common.message.ErrorMessage;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

    private final ErrorMessage errorMessage;

    public CustomException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }
}
