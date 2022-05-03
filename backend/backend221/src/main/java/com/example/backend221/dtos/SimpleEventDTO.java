package com.example.backend221.dtos;

import com.example.backend221.entities.EventCategory;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class SimpleEventDTO {
    private Integer id;
    private String bookingName;
    private Instant eventStartTime;
    private EventCategory eventCategory;
    private Integer eventDuration;
}
