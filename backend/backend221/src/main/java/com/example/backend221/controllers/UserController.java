package com.example.backend221.controllers;

import com.example.backend221.dtos.EventDTO;
import com.example.backend221.dtos.UserDTO;
import com.example.backend221.entities.Event;
import com.example.backend221.entities.User;
import com.example.backend221.repositories.UserRepository;
import com.example.backend221.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository repository;
    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public List<User> getUsers(){
        return repository.findAll(Sort.by(Sort.Direction.ASC,"name"));
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Integer id){
        return this.userService.getUserDTO(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody @Valid UserDTO newUser){
//        return EventService.save(newEvent)
//        return ResponseEntity.ok("User is valid");

        return userService.save(newUser);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        repository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,id+"User does not exist"));
        repository.deleteById(id);

    }

    @PutMapping("/{id}")
    public User update(@RequestBody @Valid  User updateUser,@PathVariable Integer id){
        User user = repository.findById(id).map(o->userService.mapUser(o,updateUser))
                .orElseGet(()->
                {
                    updateUser.setId(id);
                    return updateUser;
                });
        return repository.saveAndFlush(user);
    }
}
