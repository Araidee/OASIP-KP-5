package com.example.backend221.dtos;

import com.example.backend221.entities.EventCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
        private Integer id;
        private String bookingEmail;
        private String bookingName;
        private Instant eventStartTime;
        private String eventNotes;
        private EventCategory eventCategory;
        private Integer eventDuration;
}
