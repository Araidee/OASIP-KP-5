package com.example.backend221.services;

import com.example.backend221.Enum.Role;
import com.example.backend221.component.JwtResponse;
import com.example.backend221.component.JwtTokenUtil;
import com.example.backend221.dtos.UserAllDto;
import com.example.backend221.dtos.UserDTO;
import com.example.backend221.dtos.UserVerifiedDTO;
import com.example.backend221.entities.User;
import com.example.backend221.repositories.UserRepository;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import io.jsonwebtoken.impl.DefaultClaims;
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
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;


//    @PostConstruct
//    public void init() {
//        // just for testing purposes only
//        User user = new User();
//        user.setEmail("admin");
//        user.setPassword(passwordEncoder.encode("password"));
//        user.setUserRoles(Collections.singletonList(new UserRole(UserRoleType.ADMIN)));
//        userRepository.save(user);
//    }

    public UserDTO getUserDTO(Integer id) {
        User user = this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Users"));
        return this.modelMapper.map(user, UserDTO.class);
    }

    public UserAllDto getUserAllDTO(Integer id) {
        User user = this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Users"));
        return this.modelMapper.map(user, UserAllDto.class);
    }

    private UserDTO convertUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());

        return userDTO;
    }

    public String argon2Hashing(String stringToHash) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2i, 8, 16);
        return argon2.hash(22, 65536, 1, stringToHash); //97 length of string
    }

    private String securePassword(String password) {
        return null;
    }


    public ResponseEntity createUser(UserDTO newUser) {
//        newUser.setEventDuration(eventCategoryRepository.getById(newEvent.getEventCategory().getId()).getEventDuration());
        User u = modelMapper.map(newUser, User.class);
        u.setPassword(argon2Hashing(u.getPassword()));
        repository.saveAndFlush(u);
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }

    public User mapUser(User existingUser, User updateUser) {
        existingUser.setName(updateUser.getName());
        existingUser.setEmail(updateUser.getEmail());
        existingUser.setRole(updateUser.getRole());
        return existingUser;
    }


    public ResponseEntity matchLoginPassword(UserVerifiedDTO userVerified)  throws Exception{
        User user = repository.findByEmail(userVerified.getEmail());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email doesn't exists");
        }
        Argon2 argon2 = Argon2Factory.create();
        if (argon2.verify(user.getPassword(), userVerified.getPassword())) {
            authenticate(userVerified.getEmail(),userVerified.getPassword());
            final UserDetails userDetails = userDetailsService.loadUserByUsername(userVerified.getEmail());
            final String token = jwtTokenUtil.generateToken(userDetails, user.getName());
            HashMap<String, String> objectToResponse = new HashMap<String, String>();
            objectToResponse.put("token", token);
            objectToResponse.put("name", user.getName());
            return ResponseEntity.ok(objectToResponse);
//            return ResponseEntity.ok(new JwtResponse(token));
//            return ResponseEntity.status(HttpStatus.OK).body("Password  Matched");

        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password Incorrect");
    }
//    public ResponseEntity refreshToken(HttpServletRequest request) throws Exception {
//        // From the HttpRequest get the claims
//        DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");
//        HashMap<String, String> objectToResponse = new HashMap<String, String>();
//        Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
//        String token = jwtTokenUtil.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString());
//        objectToResponse.put("token", token);
//        return ResponseEntity.ok(objectToResponse);
//    }
//    public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
//        Map<String, Object> expectedMap = new HashMap<String, Object>();
//        for (Map.Entry<String, Object> entry : claims.entrySet()) {
//            expectedMap.put(entry.getKey(), entry.getValue());
//        }
//        return expectedMap;
//    }
    //    private UserDTO convertEntityToDto(Event event) {
//        UserDTO userDTO = new UserDTO();
//        UserDTO.setId(event.getId());
//        UserDTO.setBookingName(event.getBookingName());
//        UserDTO.setEventStartTime(event.getEventStartTime());
//        UserDTO.setEventCategory(event.getEventCategory());
//        UserDTO.setEventDuration(event.getEventDuration());
//        return UserDTO;
//    }
//    public User registerUser(User user){
//        user.setId();
//    }
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
