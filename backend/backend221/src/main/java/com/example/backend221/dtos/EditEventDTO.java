package com.example.backend221.dtos;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

public class EditEventDTO {
    @Size(max = 500 , message = "Booking name must lower than 500 characters")
    private String eventNotes;
    @Future(message = "eventStartTime must be future")
    @NotNull(message = "must not be null")
    private Instant eventStartTime;
}
