package com.example.clushapi.user.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.clushapi.common.exception.CustomException;
import com.example.clushapi.user.dto.UserRequestDto;
import com.example.clushapi.user.dto.UserResponseDto;
import com.example.clushapi.user.entity.User;
import com.example.clushapi.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.clushapi.common.message.ErrorMessage.DUPLICATED_USERNAME;
import static com.example.clushapi.common.message.ErrorMessage.NOT_FOUND_USER;
import static com.example.clushapi.common.message.ErrorMessage.NOT_VALID_PASSWORD;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponseDto signup(UserRequestDto signupRequestDto) {
        userRepository.findByUsername(signupRequestDto.getUsername())
                .ifPresent(m -> {
                    throw new CustomException(DUPLICATED_USERNAME);
                });

        String hashedPassword = hashPassword(signupRequestDto.getPassword());

        User user = signupRequestDto.toEntity(signupRequestDto.getUsername(), hashedPassword);
        userRepository.save(user);
        return UserResponseDto.from(user);
    }

    @Transactional
    public UserResponseDto login(UserRequestDto loginRequestDto) {
        User user = userRepository.findByUsername(loginRequestDto.getUsername()).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER)
        );

        if (!checkPassword(loginRequestDto.getPassword(), user.getPassword())) {
            throw new CustomException(NOT_VALID_PASSWORD);
        }

        return UserResponseDto.from(user);
    }

    private String hashPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    private boolean checkPassword(String password, String hashedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);
        return result.verified;
    }

    @Transactional(readOnly = true)
    public UserResponseDto findById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));
        return UserResponseDto.from(user);
    }
}
