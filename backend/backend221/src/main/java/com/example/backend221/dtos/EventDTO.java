package com.example.backend221.dtos;

import com.example.backend221.entities.EventCategory;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.params.converter.JavaTimeConversionPattern;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
        @NotNull
        //  @Pattern(regexp = "^\\d{10}$")
        @Email(message = "An email must be well-formed as email address")
        private String bookingEmail;
        @Size(min=1,max = 100 , message = "Booking name must be between 1 to 100 characters")
        private String bookingName;
        @Size(min=1,max = 500 , message = "Notes must be between 1 to 500 characters")
        private String eventNotes;
        private Instant eventStartTime;
        private EventCategory eventCategory;
        private Integer eventDuration;
}
