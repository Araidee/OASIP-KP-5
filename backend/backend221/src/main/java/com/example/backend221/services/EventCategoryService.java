package com.example.backend221.services;

import com.example.backend221.dtos.EventCategoryDTO;
import com.example.backend221.dtos.EventDTO;
import com.example.backend221.entities.Event;
import com.example.backend221.entities.EventCategory;
import com.example.backend221.repositories.EventCategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
@Service
public class EventCategoryService {
    @Autowired
    private EventCategoryRepository repository;
    @Autowired
    private ModelMapper modelMapper;

    public EventCategoryService() {
    }

    public EventCategoryDTO getEventCategoryDTO(Integer id) {
        EventCategory eventCategory = this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Events Category"));
        return this.modelMapper.map(eventCategory, EventCategoryDTO.class);
    }
}
