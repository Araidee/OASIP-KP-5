package com.example.backend221.controllers;

import com.example.backend221.entities.Event;
import com.example.backend221.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    @Autowired
    private EventRepository repository;

    @GetMapping("")

    public List<Event> getEvents(){
        return repository.findAll();


    }
}



