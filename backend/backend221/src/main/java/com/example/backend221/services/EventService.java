package com.example.backend221.services;

import com.example.backend221.dtos.EventDTO;
import com.example.backend221.dtos.SimpleEventDTO;
import com.example.backend221.entities.Event;
import com.example.backend221.repositories.EventCategoryRepository;
import com.example.backend221.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.modelmapper.ModelMapper;

import java.time.Instant;
import java.util.List;

@Service
public class EventService {

        @Autowired
        private EventRepository repository;
        @Autowired
        private EventCategoryRepository eventCategoryRepository;
        @Autowired
        private ModelMapper modelMapper;

        public EventService() {
        }

        public SimpleEventDTO getSimpleEventDTO(Integer id) {
            Event event = this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Schedule Events"));
            return this.modelMapper.map(event, SimpleEventDTO.class);
        }

        private SimpleEventDTO convertEntityToDto(Event event) {
            SimpleEventDTO simpleEventDTO = new SimpleEventDTO();
            simpleEventDTO.setId(event.getId());
            simpleEventDTO.setBookingName(event.getBookingName());
            simpleEventDTO.setEventStartTime(event.getEventStartTime());
            simpleEventDTO.setEventCategory(event.getEventCategory());
            simpleEventDTO.setEventDuration(event.getEventDuration());
            return simpleEventDTO;
        }

    public EventDTO getEventDTO(Integer id) {
        Event event = this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Schedule Events"));
        return this.modelMapper.map(event, EventDTO.class);
    }


    public Event save(EventDTO newEvent) {
            newEvent.setEventDuration(eventCategoryRepository.getById(newEvent.getEventCategory().getId()).getEventDuration());
        Event e = modelMapper.map(newEvent,Event.class);
        return repository.saveAndFlush(e);
    }
    public boolean validateFuture(Instant eventStartTime){
            long milliStartTime = eventStartTime.toEpochMilli(); //แปลงเป็นmilli second
            long realTime = Instant.now().toEpochMilli();
            if (milliStartTime > realTime){
                return true;
            }return false;
    }
    public List<Event> getEventByCategoryId(Integer categoryId){
            return repository.findEventByEventCategory_IdOrderByEventStartTimeDesc(categoryId);
    }
}
