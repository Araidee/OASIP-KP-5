package com.example.backend221.dtos;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAllDto implements Serializable {
    private Integer id;
    private  String name;
    private String password;
    private String email;
    private  String role;
    private  Instant createdOn;
    private  Instant updatedOn;
}
