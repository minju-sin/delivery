package com.delivery.service;

import com.delivery.domain.User;
import com.delivery.domain.UserRole;
import com.delivery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public boolean isUserIdUnique(String userId) {
        User existingUser = userRepository.findByUserId(userId);
        return existingUser == null;
    }

    public void signupUser(User user){
        //  사용자에게 기본적으로 일반 사용자 권한 할당
        user.setRole(UserRole.USER);

        // 중복 사용자 ID 체크
        if (!isUserIdUnique(user.getUserId())) {
            throw new RuntimeException("사용자 ID가 이미 존재합니다.");
        }

        // 사용자 저장
        userRepository.save(user);
    }
}
