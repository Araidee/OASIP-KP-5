package com.example.backend221.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class UserAddDTO {
        private Integer id;
        @Size(min=1 , max=100)
        @NotNull(message = "is Not Null")
        private String name;
        @Email(message = "is not email")
        @NotNull(message = "is Not Null")
        @Size(min=1 ,max=50)
        private String email;

        @NotNull(message = "is Not Null")
        private String role;

        @NotNull(message = "is Not Null")
        private String password;

        public void setName(String name) {
            this.name = name.trim();
        }

        public void setEmail(String email) {
            this.email = email.trim();
        }
    }

