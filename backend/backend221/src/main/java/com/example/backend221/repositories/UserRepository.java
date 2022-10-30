package com.example.backend221.repositories;

import com.example.backend221.Enum.Role;
import com.example.backend221.entities.Event;
import com.example.backend221.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByEmail(String email);

    @Query(value = "insert into user (name, email, role, password) values (:#{#user.getName()}, :#{#user.getEmail()}, :#{#user.getRole()}, :#{#user.getPassword()})", nativeQuery = true)
    @Modifying
    @Transactional
    public void saveUser(@Param("user") User user);

    @Query(value = "UPDATE user SET name = :#{#user.getName()} , email = :#{#user.getEmail()} , role = :#{#user.getRole()} ,updatedOn =CURRENT_TIMESTAMP where id = :#{#user.getId()} ", nativeQuery = true)
    @Modifying
    @Transactional
    public void editUser(@Param("user") User user);

    Optional<User> findUserByEmail(String email);
}