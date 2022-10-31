package com.example.backend221.services;

import com.example.backend221.dtos.UserMatchDTO;
import com.example.backend221.dtos.UserVerifiedDTO;
import com.example.backend221.entities.User;
import com.example.backend221.repositories.UserRepository;
import com.example.backend221.utils.ListMapper;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class MatchService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUserDetailsService userDetailsService;
    public ResponseEntity matchPassword(UserMatchDTO matchDTO)  throws Exception{
        User user = repository.findByEmail(matchDTO.getEmail());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email doesn't exists");
        }
        Argon2 argon2 = Argon2Factory.create();
        if (argon2.verify(user.getPassword(), matchDTO.getPassword())) {
            authenticate(matchDTO.getEmail(),matchDTO.getPassword());
            final UserDetails userDetails = userDetailsService.loadUserByUsername(matchDTO.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body("Password  Matched");

        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password Not Matched");
    }
    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }


}
