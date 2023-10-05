package com.delivery.repository;

import com.delivery.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUserId(String userId); //  아이디로 회원 정보 조회
}
