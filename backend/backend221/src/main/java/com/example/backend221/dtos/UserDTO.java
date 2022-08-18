package com.example.backend221.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDTO implements Serializable {
    private final Integer id;
    private final String name;
    private final String email;
    private final String role;
}
