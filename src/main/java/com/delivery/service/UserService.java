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

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void signupUser(User user){
        //  사용자에게 기본적으로 일반 사용자 권한 할당
        user.setRole(UserRole.USER);

        // 사용자 저장
        userRepository.save(user);
    }
}
