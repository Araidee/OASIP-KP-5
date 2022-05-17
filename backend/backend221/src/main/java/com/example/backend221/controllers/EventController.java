package com.example.backend221.controllers;

import com.example.backend221.dtos.EventDTO;
import com.example.backend221.entities.Event;
import com.example.backend221.repositories.EventRepository;
import com.example.backend221.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/events")
public class EventController implements WebMvcConfigurer {
    @Autowired
    private EventRepository repository;
    private final EventService eventService;
    @Autowired
    public EventController(EventRepository repository, EventService eventService) {this.repository = repository;
        this.eventService = eventService;
    }

    @GetMapping("")
    public List<Event> getEvents(){
        return repository.findAll(Sort.by(Sort.Direction.ASC,"eventStartTime"));


    }

    @GetMapping("/{id}")
    public EventDTO getEventById(@PathVariable Integer id){
        return this.eventService.getEventDTO(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Event createEvent(@Valid @RequestBody EventDTO newEvent) {
//        return EventService.save(newEvent)
//        return ResponseEntity.ok("User is valid");

        return eventService.save(newEvent);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        repository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,id+"Event does not exist"));
        repository.deleteById(id);

    }

    @PutMapping("/{id}")
    public Event update(@RequestBody Event updateEvent,@PathVariable Integer id){
        Event event = repository.findById(id).map(o->mapEvent(o,updateEvent))
                .orElseGet(()->
                {
                    updateEvent.setId(id);
                    return updateEvent;
                });
        return repository.saveAndFlush(event);
    }

    private Event mapEvent(Event existingEvent, Event updateEvent) {
        existingEvent.setEventStartTime(updateEvent.getEventStartTime());
        existingEvent.setEventNotes(updateEvent.getEventNotes());
        return existingEvent;
    }

}



