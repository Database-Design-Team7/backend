package com.dbteam7.backend.user.service;

import com.dbteam7.backend.entity.User;
import com.dbteam7.backend.user.dto.LoginRequest;
import com.dbteam7.backend.user.dto.LoginResponse;
import com.dbteam7.backend.user.dto.SignupRequest;
import com.dbteam7.backend.user.dto.SignupResponse;
import com.dbteam7.backend.user.dto.UserDetailResponse;
import com.dbteam7.backend.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public SignupResponse signup(SignupRequest request) {
        // 이메일 중복 검증
        if (userRepository.existsByMail(request.getMail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // User 엔티티 생성
        User user = new User();
        user.setUserId(generateUserId());
        user.setUserName(request.getUserName());
        user.setMail(request.getMail());
        user.setPenalty(0); // 기본 패널티 0

        // 그룹 가입 시 UserGroup 테이블에 레코드가 생성되며, 그때 group_role_id가 할당됨
        User savedUser = userRepository.save(user);

        // 응답 생성
        SignupResponse response = new SignupResponse();
        response.setUserId(savedUser.getUserId());
        response.setUserName(savedUser.getUserName());
        response.setMail(savedUser.getMail());
        response.setMessage("회원가입이 완료되었습니다.");

        return response;
    }

    public LoginResponse login(LoginRequest request) {
        // 이름과 이메일로 사용자 조회
        Optional<User> userOptional = userRepository.findByUserNameAndMail(
                request.getUserName(), 
                request.getMail()
        );

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("이름 또는 이메일이 일치하지 않습니다.");
        }

        User user = userOptional.get();

        // 응답 생성
        LoginResponse response = new LoginResponse();
        response.setUserId(user.getUserId());
        response.setUserName(user.getUserName());
        response.setMail(user.getMail());
        response.setMessage("로그인에 성공했습니다.");

        return response;
    }

    public List<UserDetailResponse> getAllUsers() {
        // 전체 사용자 조회
        List<User> users = userRepository.findAll();

        // User 엔티티를 UserDetailResponse로 변환
        return users.stream()
                .map(user -> {
                    UserDetailResponse response = new UserDetailResponse();
                    response.setUserId(user.getUserId());
                    response.setUserName(user.getUserName());
                    response.setMail(user.getMail());
                    response.setPenalty(user.getPenalty());
                    return response;
                })
                .collect(Collectors.toList());
    }

    public UserDetailResponse getUserById(String userId) {
        // 사용자 ID로 조회
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }

        User user = userOptional.get();

        // UserDetailResponse로 변환
        UserDetailResponse response = new UserDetailResponse();
        response.setUserId(user.getUserId());
        response.setUserName(user.getUserName());
        response.setMail(user.getMail());
        response.setPenalty(user.getPenalty());

        return response;
    }

    private String generateUserId() {
        return "USER_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}