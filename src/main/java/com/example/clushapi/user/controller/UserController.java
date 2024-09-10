package com.example.clushapi.user.controller;

import com.example.clushapi.common.dto.ResponseMessageDto;
import com.example.clushapi.user.dto.UserRequestDto;
import com.example.clushapi.user.dto.UserResponseDto;
import com.example.clushapi.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.clushapi.common.message.ResponseMessage.ALREADY_LOGIN_SUCCESS;
import static com.example.clushapi.common.message.ResponseMessage.LOGIN_SUCCESS;
import static com.example.clushapi.common.message.ResponseMessage.SIGNUP_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입이 성공적으로 완료되었습니다."),
            @ApiResponse(responseCode = "400", description = "중복된 이름이 존재합니다")
    })
    @PostMapping("/signup")
    public ResponseEntity<ResponseMessageDto<UserResponseDto>> signup(@Valid @RequestBody UserRequestDto signupRequestDto) {
        UserResponseDto response = userService.signup(signupRequestDto);
        return ResponseMessageDto.toResponseEntity(SIGNUP_SUCCESS, response);
    }

    @Operation(summary = "로그인 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인이 성공적으로 완료되었습니다."),
            @ApiResponse(responseCode = "400", description = "비밀번호가 일치하지 않습니다"),
            @ApiResponse(responseCode = "401", description = "이미 로그인된 사용자입니다."),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다")
    })
    @PostMapping("/login")
    public ResponseEntity<ResponseMessageDto<UserResponseDto>> login(@Valid @RequestBody UserRequestDto loginRequestDto, HttpServletRequest request) {
        UserResponseDto response = userService.login(loginRequestDto);

        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("userId") != null) {
            return ResponseMessageDto.toResponseEntity(ALREADY_LOGIN_SUCCESS, response);
        }

        session = request.getSession(true);
        session.setAttribute("userId", response.getUserId());
        session.setMaxInactiveInterval(1800);

        return ResponseMessageDto.toResponseEntity(LOGIN_SUCCESS, response);
    }
}
