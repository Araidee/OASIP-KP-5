package com.example.backend221.controllers;
import com.example.backend221.dtos.EventCategoryDTO;
import com.example.backend221.entities.Event;
import com.example.backend221.entities.EventCategory;
import com.example.backend221.repositories.EventCategoryRepository;
import com.example.backend221.services.EventCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/eventCategories")
public class EventCategoriesController {
    @Autowired
    private EventCategoryRepository repository;
    private final EventCategoryService eventCategoryService;
    @Autowired
    public EventCategoriesController(EventCategoryService eventCategoryService) {
        this.eventCategoryService = eventCategoryService;
    }
    @GetMapping("")
    public List<EventCategory> getEventCategories(){
        return repository.findAll(Sort.by(Sort.Direction.DESC,"eventCategoryName"));
    }
    @GetMapping("/{id}")
    public EventCategoryDTO getEventDTOById(@PathVariable Integer id){
        return this.eventCategoryService.getEventCategoryDTO(id);
    }
    @PutMapping("/{id}")
    public EventCategory update(@RequestBody EventCategory updateEventCategory, @PathVariable Integer id){
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
