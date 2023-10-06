package com.delivery.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {

    @Pattern(regexp = "^[0-9]{8}$", message = "아이디는 8자의 숫자만 입력 가능합니다.")
    private String userId;      // 아이디(학번)

    @Pattern(regexp = "^[a-zA-Z가-힣]+$", message = "이름은 영어와 한글만 입력 가능합니다.")
    private String username;    // 이름

    @Pattern(regexp = "^[가-힣]+$", message = "학과는 한글만 입력 가능합니다.")
    private String department;  // 학과

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?!.*\\s).{8,20}$"
            , message = "비밀번호는 영어 대소문자, 숫자, 특수문자를 포함하여 8자 이상 20자 이하로 입력해야 합니다.")
    private String password;    // 비밀번호

    @Pattern(regexp = "^[0-9-]+$", message = "전화번호는 숫자와 하이픈(-)만 입력 가능합니다.")
    private String phone;       // 전화번호

    private String address;     //  도로명 주소

    private String detailsAddress;  //  상세 주소

    private String role;        // 권한

}
