package com.example.backend221.services;

import com.example.backend221.dtos.EventDTO;
import com.example.backend221.dtos.SimpleEventDTO;
import com.example.backend221.dtos.UserDTO;
import com.example.backend221.entities.Event;
import com.example.backend221.entities.User;
import com.example.backend221.repositories.EventCategoryRepository;
import com.example.backend221.repositories.EventRepository;
import com.example.backend221.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private ModelMapper modelMapper;

    public UserService() {
    }

    public UserDTO getUserDTO(Integer id){
        User user = this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No Users"));
        return this.modelMapper.map(user,UserDTO.class);
    }
    private UserDTO convertUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        return userDTO;
    }


    public User save(UserDTO newUser) {
//        newUser.setEventDuration(eventCategoryRepository.getById(newEvent.getEventCategory().getId()).getEventDuration());
        User u = modelMapper.map(newUser,User.class);
        return repository.saveAndFlush(u);
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
}
