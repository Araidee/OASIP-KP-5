package com.example.backend221.dtos;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO  {
    private Integer id;
//    @NotNull(message = "must not be null")
//    @Size(min=1,max = 100 , message = "Username must be between 1 to 100 characters")
    private String name;
//    @NotNull(message = "must not be null")
//    @Email(message = "An email must be well-formed as email address")
    private String email;
//    @NotNull(message = "must not be null")
    private String role;
}
