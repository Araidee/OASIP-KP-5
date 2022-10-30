package com.example.backend221.Enum;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN,
    LECTURER,
    STUDENT;
    public String getAuthority() {
        return name();
    }
}
