package com.example.backend221.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditEventDTO {
    @Size(max = 500 , message = "Booking name must lower than 500 characters")
    private String eventNotes;
    @Future(message = "eventStartTime must be future")
    @NotNull(message = "must not be null")
    private Instant eventStartTime;


    public void setEventStartTime(Instant eventStartTime) {
        this.eventStartTime = eventStartTime;
    }
}
