package com.example.backend221.services;

import com.example.backend221.dtos.UserAllDto;
import com.example.backend221.dtos.UserDTO;
import com.example.backend221.entities.User;
import com.example.backend221.repositories.UserRepository;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private ModelMapper modelMapper;


//    @PostConstruct
//    public void init() {
//        // just for testing purposes only
//        User user = new User();
//        user.setEmail("admin");
//        user.setPassword(passwordEncoder.encode("password"));
//        user.setUserRoles(Collections.singletonList(new UserRole(UserRoleType.ADMIN)));
//        userRepository.save(user);
//    }

    public UserDTO getUserDTO(Integer id){
        User user = this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No Users"));
        return this.modelMapper.map(user,UserDTO.class);
    }
    public UserAllDto getUserAllDTO(Integer id){
        User user = this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No Users"));
        return this.modelMapper.map(user, UserAllDto.class);
    }
    private UserDTO convertUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());

        return userDTO;
    }
   public String argon2Hashing(String stringToHash){
       Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2i, 8, 16);
       return argon2.hash(22, 65536, 1, stringToHash); //97 length of string
   }
    private String securePassword(String password) {
        return null;
    }


    public ResponseEntity createUser(UserDTO newUser) {
//        newUser.setEventDuration(eventCategoryRepository.getById(newEvent.getEventCategory().getId()).getEventDuration());
        User u = modelMapper.map(newUser,User.class);
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
}
