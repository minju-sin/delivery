package com.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {

    private String userId;      // 아이디(학번)
    private String username;    // 이름
    private String department;  // 학과
    private String password;    // 비밀번호
    private String phone;       // 전화번호
    private String role;        // 권한

}
