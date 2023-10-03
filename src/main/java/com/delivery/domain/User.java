package com.delivery.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * 회원 정보 DB
 * 회원 id, 아이디(학번), 이름, 학과, 비밀번호, 전화번호
 */

@Getter //  get 함수를 일괄적으로 만듦
@Setter
@NoArgsConstructor  //  기본 생성자 만들어 줌
@Entity //  DB 테이블 역할
public class User {
    @Id
    @Column(nullable = false, length = 50)   //  반드시 값을 가져야할 때 사용
    private String userId;  //  아이디(학번)

    @Column(nullable = false, length = 50)
    private String username;    //  이름

    @Column(nullable = false, length = 50)
    private String department;  //  학과

    @Column(nullable = false, length = 20)
    private String password;    //  비밀번호

    @Column(nullable = false, length = 20)
    private String phone;   //  전화번호

    private UserRole role;  //  권한 확인

    // 유저 id 를 제외한 나머지 생성자 작성
    public User(String userId, String username, String department, String password, String phone) {
        this.userId = userId;
        this.username = username;
        this.department = department;
        this.password = password;
        this.phone = phone;
    }


}
