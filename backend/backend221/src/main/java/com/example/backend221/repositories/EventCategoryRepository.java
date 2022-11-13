package com.example.backend221.repositories;

import com.example.backend221.entities.EventCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventCategoryRepository extends JpaRepository<EventCategory, Integer> {
    //    @Query(value = "select * from EventCategory  where id = ?1",nativeQuery = true)
    EventCategory findEventCategoryById(Integer id);
}