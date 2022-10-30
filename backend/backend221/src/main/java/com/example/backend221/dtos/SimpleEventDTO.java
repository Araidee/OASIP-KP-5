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
public class SimpleEventDTO {
    private Integer Id;
    private Instant eventStartTime;
    private Integer eventDuration;
    private EventCategoryDTO eventCategory;
    private String bookingName;
    private String bookingEmail;
    private String eventNotes;
}
