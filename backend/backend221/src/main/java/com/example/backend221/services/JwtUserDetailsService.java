package com.example.backend221.services;


import java.util.ArrayList;
import java.util.Collection;

import com.example.backend221.Enum.Role;
import com.example.backend221.entities.User;
import com.example.backend221.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        Collection<Role> userRole = new ArrayList<>();
        userRole.add(user.getRole());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), userRole);
    }
}
