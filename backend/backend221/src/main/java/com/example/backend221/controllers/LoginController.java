package com.example.backend221.controllers;

import com.example.backend221.dtos.UserAllDto;
import com.example.backend221.dtos.UserDTO;
import com.example.backend221.dtos.UserVerifiedDTO;
import com.example.backend221.entities.User;
import com.example.backend221.repositories.UserRepository;
import com.example.backend221.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class LoginController {
        @Autowired
        private UserRepository repository;
        @Autowired
        private final UserService userService;



    public LoginController(UserService userService) {
        this.userService = userService;
    }

        @PostMapping("/match")
        @ResponseStatus(HttpStatus.CREATED)
        public ResponseEntity matchPassword(@RequestBody @Valid UserVerifiedDTO user) throws Exception {
            return userService.matchPassword(user);

        }

}
