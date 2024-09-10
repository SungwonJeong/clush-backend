package com.example.clushapi.todo.service;

import com.example.clushapi.common.exception.CustomException;
import com.example.clushapi.todo.dto.TodoRequestDto;
import com.example.clushapi.todo.dto.TodoResponseDto;
import com.example.clushapi.todo.entity.Todo;
import com.example.clushapi.todo.repository.TodoRepository;
import com.example.clushapi.user.entity.User;
import com.example.clushapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.clushapi.common.message.ErrorMessage.FORBIDDEN_ACCESS;
import static com.example.clushapi.common.message.ErrorMessage.NOT_FOUND_TODO;
import static com.example.clushapi.common.message.ErrorMessage.NOT_FOUND_USER;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    // 투두 생성
    @Transactional
    public TodoResponseDto createTodo(Long userId, TodoRequestDto todoRequestDto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER)
        );

        Todo todo = todoRequestDto.toEntity(user);
        todoRepository.save(todo);

        return TodoResponseDto.from(todo);
    }

    // 투두 조회
    @Transactional(readOnly = true)
    public TodoResponseDto findTodo(Long userId, Long todoId) {
        Todo todo = checkAuthorization(userId, todoId);
        return TodoResponseDto.from(todo);
    }

    // 특정한 날의 투두 리스트 조회
    @Transactional(readOnly = true)
    public List<TodoResponseDto> findTodos(LocalDate todoDate, Long userId) {
        List<Todo> todos = todoRepository.findAllByUserIdAndTodoDate(userId, todoDate);

        if (todos.isEmpty()) {
            throw new CustomException(NOT_FOUND_TODO);
        }

        return todos.stream()
                .map(TodoResponseDto::from)
                .collect(Collectors.toList());
    }

    // 투두 수정
    @Transactional
    public TodoResponseDto updateTodo(Long todoId, Long userId, TodoRequestDto todoRequestDto) {
        Todo todo = checkAuthorization(userId, todoId);
        todo.update(todoRequestDto.getTitle(), todoRequestDto.getDescription(), todoRequestDto.getTodoDate());
        return TodoResponseDto.from(todo);
    }

    // 투두 삭제
    @Transactional
    public TodoResponseDto deleteTodo(Long userId, Long todoId) {
        Todo todo = checkAuthorization(userId, todoId);
        todoRepository.deleteById(todoId);
        return TodoResponseDto.from(todo);
    }

    // 권한 체크
    private Todo checkAuthorization(Long userId, Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new CustomException(NOT_FOUND_TODO)
        );

        if (!todo.getUser().getId().equals(userId)) {
            throw new CustomException(FORBIDDEN_ACCESS);
        }

        return todo;
    }
}
