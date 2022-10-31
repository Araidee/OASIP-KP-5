package com.example.backend221.controllers;

import com.example.backend221.dtos.UserMatchDTO;
import com.example.backend221.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@RestController
@RequestMapping("/api/match")
@CrossOrigin(origins = "*")
public class MatchController {
        @Autowired
        private final MatchService matchService;

        public MatchController(MatchService matchService) {
                this.matchService = matchService;
        }

        @PostMapping("")
        public ResponseEntity<Object> matchPassword(@RequestBody @Valid UserMatchDTO matchDTO) throws Exception {
                return matchService.matchPassword(matchDTO);
        }
}