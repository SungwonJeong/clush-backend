package com.example.clushapi.todo.controller;

import com.example.clushapi.common.dto.ResponseMessageDto;
import com.example.clushapi.common.exception.CustomException;
import com.example.clushapi.todo.dto.TodoRequestDto;
import com.example.clushapi.todo.dto.TodoResponseDto;
import com.example.clushapi.todo.service.TodoService;
import com.example.clushapi.user.dto.UserResponseDto;
import com.example.clushapi.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static com.example.clushapi.common.message.ErrorMessage.LOGIN_REQUIRED;
import static com.example.clushapi.common.message.ResponseMessage.CREATE_TODO_SUCCESS;
import static com.example.clushapi.common.message.ResponseMessage.DELETE_TODO_SUCCESS;
import static com.example.clushapi.common.message.ResponseMessage.FIND_TODO_List_SUCCESS;
import static com.example.clushapi.common.message.ResponseMessage.FIND_TODO_SUCCESS;
import static com.example.clushapi.common.message.ResponseMessage.UPDATE_TODO_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;
    private final UserService userService;

    @Operation(summary = "Todo 생성 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Todo가 성공적으로 생성되었습니다"),
            @ApiResponse(responseCode = "401", description = "로그인을 해주세요"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청: 제목이 없거나 날짜 형식이 잘못되었습니다."),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다")
    })
    @PostMapping
    public ResponseEntity<ResponseMessageDto<TodoResponseDto>> createTodo(@Valid @RequestBody TodoRequestDto todoRequestDto, HttpSession session) {
        UserResponseDto authenticatedUser = getAuthenticatedUser(session);

        TodoResponseDto response = todoService.createTodo(authenticatedUser.getUserId(), todoRequestDto);
        return ResponseMessageDto.toResponseEntity(CREATE_TODO_SUCCESS, response);

    }

    @Operation(summary = "특정 Todo 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todo 조회를 성공하였습니다"),
            @ApiResponse(responseCode = "401", description = "로그인을 해주세요"),
            @ApiResponse(responseCode = "404", description = "Todo를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다")
    })
    @GetMapping("/{todoId}")
    public ResponseEntity<ResponseMessageDto<TodoResponseDto>> findTodo(@PathVariable Long todoId, HttpSession session) {
        UserResponseDto authenticatedUser = getAuthenticatedUser(session);

        TodoResponseDto response = todoService.findTodo(authenticatedUser.getUserId(), todoId);
        return ResponseMessageDto.toResponseEntity(FIND_TODO_SUCCESS, response);
    }

    @Operation(summary = "특정 날짜의 Todo 리스트 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todo 리스트 조회를 성공하였습니다"),
            @ApiResponse(responseCode = "401", description = "로그인을 해주세요"),
            @ApiResponse(responseCode = "404", description = "Todo를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다")
    })
    @GetMapping
    public ResponseEntity<ResponseMessageDto<List<TodoResponseDto>>> findTodos(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate todoDate, HttpSession session) {
        UserResponseDto authenticatedUser = getAuthenticatedUser(session);

        List<TodoResponseDto> response = todoService.findTodos(todoDate, authenticatedUser.getUserId());
        return ResponseMessageDto.toResponseEntity(FIND_TODO_List_SUCCESS, response);
    }

    @Operation(summary = "Todo 수정 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todo가 성공적으로 수정되었습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청: 제목이 없거나 날짜 형식이 잘못되었습니다."),
            @ApiResponse(responseCode = "401", description = "로그인을 해주세요"),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다"),
            @ApiResponse(responseCode = "404", description = "Todo를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다")
    })
    @PatchMapping("/{todoId}")
    public ResponseEntity<ResponseMessageDto<TodoResponseDto>> updateTodo(@PathVariable Long todoId, @Valid @RequestBody TodoRequestDto todoRequestDto, HttpSession session) {
        UserResponseDto authenticatedUser = getAuthenticatedUser(session);

        TodoResponseDto response = todoService.updateTodo(todoId, authenticatedUser.getUserId(), todoRequestDto);
        return ResponseMessageDto.toResponseEntity(UPDATE_TODO_SUCCESS, response);
    }

    @Operation(summary = "Todo 삭제 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todo가 성공적으로 삭제되었습니다"),
            @ApiResponse(responseCode = "401", description = "로그인을 해주세요"),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다"),
            @ApiResponse(responseCode = "404", description = "Todo를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다")
    })
    @DeleteMapping("/{todoId}")
    public ResponseEntity<ResponseMessageDto<TodoResponseDto>> deleteTodo(@PathVariable Long todoId, HttpSession session) {
        UserResponseDto authenticatedUser = getAuthenticatedUser(session);

        TodoResponseDto response = todoService.deleteTodo(authenticatedUser.getUserId(), todoId);
        return ResponseMessageDto.toResponseEntity(DELETE_TODO_SUCCESS, response);
    }

    private UserResponseDto getAuthenticatedUser(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            throw new CustomException(LOGIN_REQUIRED);
        }
        return userService.findById(userId);
    }
}
