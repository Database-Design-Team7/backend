package com.dbteam7.backend.user.controller;

import com.dbteam7.backend.user.dto.LoginRequest;
import com.dbteam7.backend.user.dto.LoginResponse;
import com.dbteam7.backend.user.dto.SignupRequest;
import com.dbteam7.backend.user.dto.SignupResponse;
import com.dbteam7.backend.user.dto.UserDetailResponse;
import com.dbteam7.backend.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User Management", description = "사용자 관리 API")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    @Operation(summary = "회원 가입", description = "이름, 이메일(중복 검증)을 입력하여 회원가입합니다.")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest request) {
        try {
            SignupResponse response = userService.signup(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("회원가입 중 오류가 발생했습니다."));
        }
    }

    @PostMapping("/login")
    @Operation(summary = "로그인 및 인증", description = "이름, 이메일을 입력하여 로그인합니다.")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            LoginResponse response = userService.login(request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("로그인 중 오류가 발생했습니다."));
        }
    }

    @GetMapping
    @Operation(summary = "사용자 목록 조회 (관리자)", description = "시스템에 등록된 전체 사용자 목록 및 상세 정보를 조회합니다.")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<UserDetailResponse> users = userService.getAllUsers();
            return ResponseEntity.status(HttpStatus.OK).body(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("사용자 목록 조회 중 오류가 발생했습니다."));
        }
    }

    @GetMapping("/{userId}")
    @Operation(summary = "사용자 상세 조회", description = "특정 사용자의 상세 정보를 조회합니다.")
    public ResponseEntity<?> getUserById(@PathVariable String userId) {
        try {
            UserDetailResponse user = userService.getUserById(userId);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("사용자 조회 중 오류가 발생했습니다."));
        }
    }

    // 에러 응답을 위한 record
    private record ErrorResponse(String message) {
    }
}

