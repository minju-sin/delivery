package com.delivery.repository;

import com.delivery.domain.User;
import com.delivery.domain.UserRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUserId(String userId); //  아이디로 회원 정보 조회

    User findByPhone(String phone); //  전화번호
    List<User> findByRole(UserRole role);   //  권한으로 회원 정보 찾기
}
