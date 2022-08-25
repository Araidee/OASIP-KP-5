package com.example.backend221.entities;

import javax.persistence.*;
import java.time.Instant;

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

    @Lob
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private String role;

    @Column(name = "createdOn", nullable = false, insertable = false , updatable = false)
    private Instant createdOn;

    @Column(name = "updatedOn", nullable = false, insertable = false , updatable = false)
    private Instant updatedOn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

}