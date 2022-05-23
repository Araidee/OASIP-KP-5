package com.example.backend221.repositories;

import com.example.backend221.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
//    @Query(value = "select * from EventCategory  where id = ?1",nativeQuery = true)
     List<Event> findEventByEventCategory_IdOrderByEventStartTimeDesc(Integer categoryId);
     @Query(value = "select * from event where eventStartTime > :current_datetime order by eventStartTime",nativeQuery = true)
        List<Event> findAllFutureEvents(@Param("current_datetime") String current_datetime);
    @Query(value = "select * from event where eventStartTime <= :current_datetime order by eventStartTime",nativeQuery = true)
    List<Event> getAllPastEvents(@Param("current_datetime")String current_datetime);


}