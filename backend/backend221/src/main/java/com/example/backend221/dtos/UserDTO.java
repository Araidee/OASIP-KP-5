package com.example.backend221.dtos;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO  {
    private Integer id;
    private String name;
    private String password;
    private String email;
    private String role;
    private Instant createdOn;
    private  Instant updatedOn;
}
