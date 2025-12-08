package com.dbteam7.backend.user.service;

import com.dbteam7.backend.entity.User;
import com.dbteam7.backend.user.dto.SignupRequest;
import com.dbteam7.backend.user.dto.SignupResponse;
import com.dbteam7.backend.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

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

        // 저장
        // 주의: 회원가입 시에는 그룹에 속하지 않으므로 권한이 없음
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

    private String generateUserId() {
        return "USER_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}