package com.example.backend221.entities;

import com.example.backend221.Enum.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100 , unique = true)
    private String name;

    @Column(name = "password", length = 50)
    private String password;

    @Column(name = "email", nullable = false, length = 50 , unique = true)
    private String email;


    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;
    @JsonIgnore
    @Column(name = "createdOn", nullable = false, insertable = false , updatable = false)
    private Instant createdOn;
    @JsonIgnore
    @Column(name = "updatedOn", nullable = false, insertable = false , updatable = false)
    private Instant updatedOn;



}