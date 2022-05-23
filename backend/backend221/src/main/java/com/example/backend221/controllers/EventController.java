package com.example.backend221.controllers;

import com.example.backend221.dtos.EventDTO;
import com.example.backend221.entities.Event;
import com.example.backend221.repositories.EventRepository;
import com.example.backend221.services.EventCategoryService;
import com.example.backend221.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Valid;
import java.time.Instant;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/events")
public class EventController implements WebMvcConfigurer {
    @Autowired
    private EventRepository repository;
    @Autowired
    private final EventService eventService;
    @Autowired
    private final EventCategoryService eventCategoryService;
    @Autowired
    public EventController(EventRepository repository, EventService eventService,EventCategoryService eventCategoryService) {this.repository = repository;
        this.eventService = eventService;
        this.eventCategoryService = eventCategoryService;
    }

    @GetMapping("")
    public List<Event> getEvents(){
        return repository.findAll(Sort.by(Sort.Direction.ASC,"eventStartTime"));


    }

    @GetMapping("/{id}")
    public EventDTO getEventById(@PathVariable Integer id){
        return this.eventService.getEventDTO(id);
    }


//    @GetMapping("/category/{categoryId}")
//    public Page<Event> getEvents(@PathVariable Integer categoryId){
//        return (Page<Event>) eventCategoryService.findEventCategoryById(categoryId);
//
//
//    }
//    @GetMapping("/category/{id}")
//    public EventCategoryDTO getEventDTOById(@PathVariable Integer id){
//        return eventCategoryService.getEventCategoryDTO(id);
//    }
    @GetMapping("/category/{categoryId}")
    public List<Event> getEventByCategoryId(@PathVariable Integer categoryId){
        return eventService.getEventByCategoryId(categoryId);
    }
    //future
    @GetMapping("/upcoming/{current_datetime}")
    public List<Event> getAllFutureEvents(@PathVariable String current_datetime){

        return  repository.findAllFutureEvents(current_datetime);
    }
    //Past
    @GetMapping("/past/{current_datetime}")
    public List<Event> getAllPastEvents(@PathVariable String current_datetime){

        return  repository.getAllPastEvents(current_datetime);
    }
    //
    @GetMapping("/date/{justDate}")
    public List<Event> getAllByDay(@PathVariable String justDate){

        return  repository.getByDay(justDate);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Event createEvent(@RequestBody @Valid  EventDTO newEvent) {
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
    public Event update(@RequestBody @Valid  Event updateEvent,@PathVariable Integer id){
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



