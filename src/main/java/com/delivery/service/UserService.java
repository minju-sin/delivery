package com.delivery.service;

import com.delivery.domain.User;
import com.delivery.domain.UserRole;
import com.delivery.dto.UserDTO;
import com.delivery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;

    //  회원 가입 서비스 - 사용자 권한만 부여한다.
    public void registerUser(UserDTO userDTO) {
        // userId, phone로 이미 등록된 사용자가 있는지 확인
        User existingUser = userRepository.findByUserId(userDTO.getUserId());
        User existingPhone = userRepository.findByPhone(userDTO.getPhone());

        // userId의 중복이면 데이터 저장 안함
        if (existingUser != null) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다..");
        } 
        // phone의 중복이면 데이터 저장 안함 
        else if(existingPhone != null) {
            throw new IllegalArgumentException("이미 존재하는 전화번호입니다..");
        } else {
            // UserDTO에서 필요한 정보를 User 엔티티로 매핑
            User user = new User();
            user.setUserId(userDTO.getUserId());
            user.setUsername(userDTO.getUsername());
            user.setDepartment(userDTO.getDepartment());

            // 비밀번호 저장
            user.setPassword(userDTO.getPassword());

            user.setPhone(userDTO.getPhone());
            user.setRole(UserRole.USER); // 사용자 권한으로 설정

            // UserRepository를 사용하여 User 엔티티를 데이터베이스에 저장
            userRepository.save(user);
        }
    }
}
