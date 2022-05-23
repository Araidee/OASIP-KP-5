package com.example.backend221.controllers;
import com.example.backend221.dtos.EventCategoryDTO;
import com.example.backend221.entities.Event;
import com.example.backend221.entities.EventCategory;
import com.example.backend221.repositories.EventCategoryRepository;
import com.example.backend221.services.EventCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/eventCategories")
public class EventCategoriesController {
    @Autowired
    private EventCategoryRepository repository;
    @Autowired
    private final EventCategoryService eventCategoryService;
    @Autowired
    public EventCategoriesController(EventCategoryService eventCategoryService) {
        this.eventCategoryService = eventCategoryService;
    }
    @GetMapping("")
    public List<EventCategory> getEventCategories(){
        return repository.findAll(Sort.by(Sort.Direction.DESC,("id")));
    }

    @GetMapping("{id}")
    public EventCategoryDTO getEventDTOById(@PathVariable Integer id){
        return this.eventCategoryService.getEventCategoryDTO(id);
    }
//    @PatchMapping("/{id}")
//    public ResponseEntity<EventCategory> update(@Valid @PathVariable EventCategoryDTO eventCategory, @PathVariable Integer id){
////        EventCategory eventCategory = repository.findById(id).map(o->mapEventCategory(o,updateEventCategory))
////                .orElseGet(()->
////                {
////                    updateEventCategory.setId(id);
////                    return updateEventCategory;
////                });
////        return repository.saveAndFlush(eventCategory);
////        EventCategory eventCategory= repository.findEventCategoryById(id);
////        eventCategory.setEventDuration(updateEventCategory.getEventDuration());
////        eventCategory.setEventCategoryDescription(updateEventCategory.getEventCategoryDescription());
////        eventCategory.setEventCategoryName(updateEventCategory.getEventCategoryName());
////          repository.saveAndFlush(eventCategory);
////        return ResponseEntity.ok(eventCategory);
//    }
@PutMapping("/{id}")
public EventCategory update(@RequestBody @Valid EventCategory updateEventCategory, @PathVariable Integer id){
    EventCategory eventCategory = repository.findById(id).map(o->mapEventCategory(o,updateEventCategory))
            .orElseGet(()->
            {
                updateEventCategory.setId(id);
                return updateEventCategory;
            });
    return repository.saveAndFlush(eventCategory);
}


    private EventCategory mapEventCategory(EventCategory existingEventCategory, EventCategory updateEventCategory) {
        existingEventCategory.setEventDuration(updateEventCategory.getEventDuration());
        existingEventCategory.setEventCategoryDescription(updateEventCategory.getEventCategoryDescription());
        existingEventCategory.setEventCategoryName(updateEventCategory.getEventCategoryName());
        return existingEventCategory;
    }

}
