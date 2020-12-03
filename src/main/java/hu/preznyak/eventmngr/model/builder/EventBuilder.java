package hu.preznyak.eventmngr.model.builder;

import hu.preznyak.eventmngr.model.entity.Event;

public class EventBuilder {
    String title;
    String description;
    Integer price;
    Integer deposit;
    String contact;
    String location;

    public EventBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public EventBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public EventBuilder setPrice(Integer price) {
        this.price = price;
        return this;
    }

    public EventBuilder setDeposit(Integer deposit) {
        this.deposit = deposit;
        return this;
    }

    public EventBuilder setContact(String contact) {
        this.contact = contact;
        return this;
    }

    public EventBuilder setLocation(String location) {
        this.location = location;
        return this;
    }

    public Event createEvent(){
        return new Event(title,description,price,deposit,contact,location);
    }
}
