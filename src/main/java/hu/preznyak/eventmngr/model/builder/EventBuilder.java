package hu.preznyak.eventmngr.model.builder;

import hu.preznyak.eventmngr.model.entity.Event;

import java.time.LocalDateTime;

public class EventBuilder {
    String title;
    String description;
    String location;
    LocalDateTime startDate;
    LocalDateTime endDate;

    public EventBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public EventBuilder setDescription(String description) {
        this.description = description;
        return this;
    }


    public EventBuilder setLocation(String location) {
        this.location = location;
        return this;
    }



    public EventBuilder setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public EventBuilder setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public Event createEvent(){
        return new Event(title, description, location, startDate, endDate);
    }
}
