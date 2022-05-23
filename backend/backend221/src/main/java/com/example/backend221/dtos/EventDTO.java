package com.example.backend221.dtos;

import com.example.backend221.entities.EventCategory;
import com.fasterxml.jackson.annotation.JsonFormat;
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
        private Integer id;
        @NotNull(message = "must not be null")
        //  @Pattern(regexp = "^\\d{10}$")
        @Email(message = "An email must be well-formed as email address")
        private String bookingEmail;
        @NotNull(message = "must not be null")
        @Size(min=1,max = 100 , message = "Booking name must be between 1 to 100 characters")
        private String bookingName;
        private String eventNotes;
        @Future(message = "eventStartTime must be future")
        @NotNull(message = "must not be null")
        private Instant eventStartTime;
        @NotNull(message = "must not be null")
        private EventCategory eventCategory;
        private Integer eventDuration;
}
