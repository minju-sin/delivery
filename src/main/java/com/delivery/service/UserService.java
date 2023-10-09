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

            user.setPhone(userDTO.getPhone());  //  전화번호 저장
            user.setAddress(userDTO.getAddress());  //  도로명 주소 저장
            user.setDetailsAddress(userDTO.getDetailsAddress());    //  상세 주소 저장
            user.setRole(UserRole.USER); // 사용자 권한으로 설정

            // UserRepository를 사용하여 User 엔티티를 데이터베이스에 저장
            userRepository.save(user);
        }
    }

    // 사용자 정보 업데이트 서비스
    public void updateUser(String userId, UserDTO userDTO) {
        // 사용자 ID를 기반으로 데이터베이스에서 사용자 정보 가져오기
        User user = userRepository.findByUserId(userId);

        if (user != null) {
            // UserDTO에서 가져온 정보로 사용자 정보 업데이트
            user.setPassword(userDTO.getPassword());
            user.setDepartment(userDTO.getDepartment());
            user.setAddress(userDTO.getAddress());
            user.setDetailsAddress(userDTO.getDetailsAddress());

            // 데이터베이스에 업데이트된 사용자 정보 저장
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다."); // 사용자를 찾을 수 없는 경우 예외 처리
        }
    }

    // 사용자 삭제 서비스
    public void deleteUser(String userId) {
        // UserRepository를 사용하여 사용자를 찾습니다.
        User user = userRepository.findByUserId(userId);

        if (user != null) {
            // 사용자를 삭제합니다.
            userRepository.delete(user);
        } else {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }
    }
}
