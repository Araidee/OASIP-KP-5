package com.example.backend221.entities;

import com.example.backend221.entities.EventCategory;
import com.example.backend221.entities.User;

import javax.persistence.*;

@Entity
@Table(name = "eventCategoryOwner")
public class EventCategoryOwner {
    @Id
    @Column(name = "CategoryOwnerId", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userID", nullable = false)
    private User userID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eventCategoryID")
    private EventCategory eventCategoryID;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

    public EventCategory getEventCategoryID() {
        return eventCategoryID;
    }

    public void setEventCategoryID(EventCategory eventCategoryID) {
        this.eventCategoryID = eventCategoryID;
    }

}