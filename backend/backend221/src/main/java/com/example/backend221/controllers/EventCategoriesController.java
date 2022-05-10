package com.example.backend221.controllers;
import com.example.backend221.entities.EventCategory;
import com.example.backend221.repositories.EventCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/eventCategories")
public class EventCategoryController {
    @Autowired
    private EventCategoryRepository repository;
    @Autowired
    public EventCategoryController(EventCategoryRepository repository) {
        this.repository = repository;
    }
    @GetMapping("")
    public List<EventCategory> getEventCategories(){
        return repository.findAll();


    }

}
