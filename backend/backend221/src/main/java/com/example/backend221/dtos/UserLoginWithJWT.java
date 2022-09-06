package com.example.backend221.dtos;

import lombok.*;

import javax.management.relation.Role;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.GenerationType.AUTO;

//@Entity
//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginWithJWT {
//    @Id @GeneratedValue(strategy = AUTO)
    private Integer id;
    private String name;
    private String email;
    private String password;
//    @ManyToMany(fetch = FetchType.EAGER)
//    private Collection<Role> roles = new ArrayList<>();
}
