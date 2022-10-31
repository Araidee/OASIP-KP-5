package com.example.backend221.controllers;

import com.example.backend221.dtos.EditEventDTO;
import com.example.backend221.dtos.EventDTO;
import com.example.backend221.dtos.SimpleEventDTO;
import com.example.backend221.entities.Event;
import com.example.backend221.repositories.EventRepository;
import com.example.backend221.services.EventCategoryService;
import com.example.backend221.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
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

    @GetMapping("/all")
    public List<SimpleEventDTO> getAllEvent(HttpServletRequest request) {
        return eventService.getAllEvent(request);

    }

    @GetMapping("/{id}")
    public ResponseEntity getEventById(@PathVariable Integer id , HttpServletRequest request) {
        return eventService.getSimpleEventById(id,request);
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
    @GetMapping("/categories/{categoryId}")
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

    @PostMapping("/adding")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createEvent(@RequestBody @Valid  EventDTO newEvent, HttpServletRequest request) throws MethodArgumentNotValidException, MessagingException, IOException {
//        return EventService.save(newEvent)
//        return ResponseEntity.ok("User is valid");
        System.out.println("postmapping");
        return eventService.create(newEvent, request);
    }
//    @PostMapping("")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Event createEvent(@RequestBody @Valid  EventDTO newEvent) {
////        return EventService.save(newEvent)
////        return ResponseEntity.ok("User is valid");
//        if(eventService.isOverlap(newEvent)== true){
//            return eventService.save(newEvent);
//        }throw new RuntimeException(failed);
//    }
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id, HttpServletRequest request){
        return eventService.deleteEventById(id, request);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@Valid @RequestBody EditEventDTO update, @PathVariable int id, HttpServletRequest request) {
        return eventService.editEvent(update, id, request);
    }
    private Event mapEvent(Event existingEvent, Event updateEvent) {
        existingEvent.setEventStartTime(updateEvent.getEventStartTime());
        existingEvent.setEventNotes(updateEvent.getEventNotes());
        return existingEvent;
    }
    @GetMapping("/filtration")
    public List<SimpleEventDTO> getEventByFilterCategory(@RequestParam(defaultValue = "0") int eventCategoryId,
                                                         @RequestParam(defaultValue = "all") String pastOrFutureOrAll,
                                                         @RequestParam(defaultValue = "") String date,
                                                         @RequestParam(defaultValue = "0") int offsetMin,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "4") int pageSize,
                                                         HttpServletRequest request) {
        return eventService.getAllEventFilterByEventCategoryAndPassOrFutureOrAll(request, eventCategoryId, pastOrFutureOrAll, date, offsetMin, page, pageSize);
    }
}



