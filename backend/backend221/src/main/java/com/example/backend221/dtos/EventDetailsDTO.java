package com.example.backend221.dtos;


import com.example.backend221.entities.EventCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDetailsDTO {
    private Instant eventStartTime;
    private Integer eventDuration;
    private String bookingName;
    private String bookingEmail;
    private Integer id;
    private EventCategory eventCategory;
    private String eventNotes;
}
