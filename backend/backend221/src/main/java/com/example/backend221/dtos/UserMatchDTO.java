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

@Data
public class UserMatchDTO implements  Serializable{
    @Email(message = "is not email")
    @NotNull(message = "is Not Null")
    @Size(min=1 ,max=50)
    private String email;
    @NotNull(message = "is Not Null")
//    @Size(min=8 , max=18)
    private String password;

    public void setEmail(String email) {
        this.email = email.trim();
    }

    public void setPassword(String password) {
        this.password = password.trim();
    }
}
