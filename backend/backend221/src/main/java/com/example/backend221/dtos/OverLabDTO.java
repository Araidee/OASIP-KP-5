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
public class OverLabDTO {
    private Instant eventStartTime;
    private EventCategory eventCategory;
    private Integer eventDuration;
}
