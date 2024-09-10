package com.example.clushapi.user.dto;

import com.example.clushapi.user.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private Long userId;
    private String username;

    private UserResponseDto(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
    }

    public static UserResponseDto from(User user) {
        return new UserResponseDto(user);
    }
}
