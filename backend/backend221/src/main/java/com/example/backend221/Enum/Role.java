package com.example.backend221.Enum;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    admin,
    LECTURER,
    student;
    public String getAuthority() {
        return name();
    }
}
