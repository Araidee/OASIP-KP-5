package com.example.backend221.Enum;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    admin,
    lecturer,
    student;
    public String getAuthority() {
        return name();
    }


}
