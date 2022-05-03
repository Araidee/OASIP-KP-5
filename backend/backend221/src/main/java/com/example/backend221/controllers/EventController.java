package com.example.backend221.controllers;

import com.example.backend221.dtos.EventDTO;
import com.example.backend221.entities.Event;
import com.example.backend221.repositories.EventRepository;
import com.example.backend221.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/events")
public class EventController {
    @Autowired
    private EventRepository repository;
    private final EventService eventService;
    @Autowired
    public EventController(EventRepository repository, EventService eventService) {this.repository = repository;
        this.eventService = eventService;
    }

    @GetMapping("")
    public List<Event> getEvents(){
        return repository.findAll();


    }

    @GetMapping("/{id}")
    public EventDTO getEventById(@PathVariable Integer id){
        return this.eventService.getEventDTO(id);
    }


}



