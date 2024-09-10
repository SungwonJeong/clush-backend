package com.example.clushapi.user.dto;

import com.example.clushapi.user.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserRequestDto {

    @NotNull(message = "사용자 이름을 입력해주세요")
    private String username;

    @NotNull(message = "비밀번호를 입력해주세요")
    private String password;

    public User toEntity(String username, String password) {
        return User.builder()
                .username(username)
                .password(password)
                .build();
    }
}
