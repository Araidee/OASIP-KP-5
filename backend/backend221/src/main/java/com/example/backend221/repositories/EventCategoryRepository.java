package com.example.backend221.repositories;

import com.example.backend221.entities.EventCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventCategoryRepository extends JpaRepository<EventCategory, Integer> {
    EventCategory findEventCategoryById(Integer integer);
}