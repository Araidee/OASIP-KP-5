package com.example.backend221.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.time.Instant;
@Getter
@Setter
@Entity
@Table(name = "event")
public class Event {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookingId", nullable = false)
    private Integer id;

    @Column(name = "bookingEmail", nullable = false, length = 50)
    private String bookingEmail;

    @Column(name = "bookingName", nullable = false, length = 100)
    private String bookingName;
    @Future(message = "eventStartTime must be future")
    @Column(name = "eventStartTime", nullable = false)
    private Instant eventStartTime;
    @Column(name = "eventNotes", length = 500)
    private String eventNotes;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "eventCategory", nullable = false)
    private EventCategory eventCategory;

    @Column(name = "eventDuration", nullable = false)
    private Integer eventDuration;

    @Size(max = 500)
    @Column(name = "attachment", length = 500)
    private String attachment;

}
