package com.example.backend221.controllers;

import com.example.backend221.component.JwtRequestFilter;
import com.example.backend221.component.JwtTokenUtil;

import com.example.backend221.dtos.UserMatchDTO;
import com.example.backend221.entities.User;
import com.example.backend221.repositories.UserRepository;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import io.jsonwebtoken.impl.DefaultClaims;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/jwt")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody UserMatchDTO authenticationRequest) throws Exception {
//        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        UserDetails user = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        User myUser = userRepository.findByEmail(authenticationRequest.getEmail());

        Argon2 argon2 = Argon2Factory.create();
        if (argon2.verify(user.getPassword(), authenticationRequest.getPassword())) {
            final String token = jwtTokenUtil.generateToken(user, myUser.getName());
            final String token2 = jwtTokenUtil.generateRefreshToken(user, myUser.getName());
            HashMap<String, String> objectToResponse = new HashMap<String, String>();
            objectToResponse.put("token", token);
            objectToResponse.put("refreshToken", token2);
            return ResponseEntity.ok(objectToResponse);
        }
        else  return ResponseEntity.status(404).body("Password Invaild");
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
    @RequestMapping(value = "/loginms", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationTokenMS(@RequestBody String MsJwtToken) throws Exception {
        System.out.println("loginmsstart :");
        System.out.println("MsJwtToken: " + MsJwtToken);
        JSONObject payload = jwtRequestFilter.extractMSJwt(MsJwtToken);
        System.out.println(payload.get("roles"));
        String role = "";
        String email = "";
        String name = "";
        HashMap<String, Object> claims = new HashMap<>();
        try {
//            role = payload.getString("roles").replaceAll("[^a-zA-Z]+", "");
            role = payload.getString("roles").replaceAll("[^a-zA-Z]+", "");

        } catch (JSONException ex) {
            role = "GUEST";
        }

        email = payload.getString("preferred_username");
        name = payload.getString("name");
        final String token = jwtTokenUtil.doGenerateTokenForMs(claims, email, role, name, 0);
        final String token2 = jwtTokenUtil.doGenerateTokenForMs(claims, email, role, name, 1);
        HashMap<String, String> objectToResponse = new HashMap<String, String>();
        objectToResponse.put("token", token);
        objectToResponse.put("refreshtoken", token2);
        return ResponseEntity.ok(objectToResponse);

    }
    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public ResponseEntity<?> refreshToken(HttpServletRequest request) throws Exception {
        HashMap<String, String> objectToResponse = new HashMap<String, String>();
        // From the HttpRequest get the claims
        System.out.println(request);
        DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");
        if(claims == null){
            return ResponseEntity.status(403).body("Claims == null, Can't Refresh");
        }
        Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
        String token = jwtTokenUtil.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString());
        objectToResponse.put("token", token);
        return ResponseEntity.ok(objectToResponse);
    }

    public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
        Map<String, Object> expectedMap = new HashMap<String, Object>();
        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            expectedMap.put(entry.getKey(), entry.getValue());
        }
        return expectedMap;
    }
}