package com.example.backend221.repositories;

import com.example.backend221.entities.EventCategoryOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventCategoryOwnerRepository extends JpaRepository<EventCategoryOwner, Integer> {
}