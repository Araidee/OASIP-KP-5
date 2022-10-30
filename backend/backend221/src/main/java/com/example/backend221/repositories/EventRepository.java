package com.example.backend221.repositories;

import com.example.backend221.entities.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
        //    @Query(value = "select * from EventCategory  where id = ?1",nativeQuery = true)
        List<Event> findEventByEventCategory_IdOrderByEventStartTimeDesc(Integer categoryId);

        //      List<Event> findAllByEventCategory_IdAndAndEventStartTime(Integer categoryId, LocalDateTime present,LocalDateTime tomorrow);
        @Query(value = "select * from event where eventStartTime > :current_datetime order by eventStartTime", nativeQuery = true)
        List<Event> findAllFutureEvents(@Param("current_datetime") String current_datetime);

        @Query(value = "select * from event where eventStartTime <= :current_datetime order by eventStartTime desc", nativeQuery = true)
        List<Event> getAllPastEvents(@Param("current_datetime") String current_datetime);

        @Query(value = "select * from event where DATE_FORMAT(eventStartTime,'%Y-%m-%d')=:justDate order by eventStartTime ", nativeQuery = true)
        List<Event> getByDay(@Param("justDate") String justDate);

        List<Event> findAllByBookingEmail(String bookingEmail, Pageable pageable);

        List<Event> findAllByBookingEmail(String bookingEmail, Sort sort);

        @Query(value = "select e.* from event e join eventCategory ec on e.event_category_id = ec.event_category_id join event_category_owner eco on ec.event_category_id = eco.event_category_id where eco.user_id = :lecturer_id order by e.event_start_time desc", nativeQuery = true)
        List<Event> findAllEventByLecturerCategory(Integer lecturer_id);
        List<Event> findAllByEventCategoryId(Integer eventCategoryId, Pageable pageable);
        List<Event> findAllByBookingEmailAndEventCategoryId(String email, Integer eventCategoryId, Pageable pageable);
        List<Event> findAllByEventStartTimeBefore(Instant now, Pageable pageable);

        List<Event> findAllByBookingEmailAndEventStartTimeBefore(String email, Instant now, Pageable pageable);

        List<Event> findAllByBookingEmailAndEventStartTimeAfter(String email, Instant now, Pageable pageable);
        List<Event> findAllByEventStartTimeAfter(Instant now, Pageable pageable);
        List<Event> findAllByEventStartTimeAfterAndEventCategoryId(Instant now, Integer eventCategoryId, Pageable pageable);
        List<Event> findAllByBookingEmailAndEventStartTimeAfterAndEventCategoryId(String email, Instant now, Integer eventCategoryId, Pageable pageable);
        List<Event> findAllByBookingEmailAndEventStartTimeBeforeAndEventCategoryId(String email, Instant now, Integer eventCategoryId, Pageable pageable);
        List<Event> findAllByEventStartTimeBeforeAndEventCategoryId(Instant now, Integer eventCategoryId, Pageable pageable);
        List<Event> findAllByBookingEmailAndIdNot(String email, Integer eventId, Pageable pageable);
        List<Event> findAllByIdNot(Integer eventId, Pageable pageable);
        List<Event> findAllByEventStartTimeBetween(Instant d, Instant t, Pageable pageable);
        List<Event> findAllByBookingEmailAndEventStartTimeBetween(String email, Instant d, Instant t, Pageable pageable);
        List<Event> findAllByEventCategoryIdAndEventStartTimeBetween(Integer eventCategoryid, Instant d, Instant t, Pageable pageable);
        List<Event> findAllByBookingEmailAndEventCategoryIdAndEventStartTimeBetween(String email, Integer eventCategoryid, Instant d, Instant t, Pageable pageable);
        List<Event> findAllByIdNotAndEventCategoryIdAndEventStartTimeBetween(Integer eventId, Integer eventCategoryid, Instant d, Instant t,  Pageable pageable);
        List<Event> findAll(Sort sort);

}