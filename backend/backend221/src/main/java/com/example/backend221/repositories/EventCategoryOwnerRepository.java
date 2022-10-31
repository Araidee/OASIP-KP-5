package com.example.backend221.repositories;

import com.example.backend221.entities.EventCategoryOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventCategoryOwnerRepository extends JpaRepository<EventCategoryOwner, Integer> {
    List<EventCategoryOwner> findByUserID(String Username);
}