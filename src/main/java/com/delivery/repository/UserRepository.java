package com.delivery.repository;

import com.delivery.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUserId(String userId);   //  사용자 아이디를 불러옴
}
