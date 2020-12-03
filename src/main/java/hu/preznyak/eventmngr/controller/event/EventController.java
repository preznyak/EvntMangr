package hu.preznyak.eventmngr.controller.event;

import hu.preznyak.eventmngr.model.builder.EventBuilder;
import hu.preznyak.eventmngr.model.entity.Event;
import hu.preznyak.eventmngr.model.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class EventController {

    EventRepository eventRepository;

    @Autowired
    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @PostMapping("event/create/{title}/{price}")
    public Event createEvent(@PathVariable String title, @PathVariable Integer price){

        return eventRepository.save(new EventBuilder().setTitle(title).setPrice(price).createEvent());
    }
}
