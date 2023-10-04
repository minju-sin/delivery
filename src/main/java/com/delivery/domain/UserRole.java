package com.delivery.domain;

/* 이용자가 관리자 권한을 가지는지 사용자 권한을 가지는지 체크하는 부분
 * 공지사항은 관리자만 작성할 수 있음
 */
public enum UserRole {
    USER("ROLE_USER"), // 사용자 역할
    ADMIN("ROLE_ADMIN"); // 관리자 역할

    private final String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
