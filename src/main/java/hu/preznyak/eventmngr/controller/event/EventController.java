package hu.preznyak.eventmngr.controller.event;

import hu.preznyak.eventmngr.model.builder.EventBuilder;
import hu.preznyak.eventmngr.model.entity.Event;
import hu.preznyak.eventmngr.model.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/")
public class EventController {

    EventRepository eventRepository;

    @Autowired
    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @PostMapping("event/create/{title}")
    @ResponseStatus(HttpStatus.CREATED)
    public Event createEventWithTitle(@PathVariable String title) {

        return eventRepository.save(new EventBuilder().setTitle(title).createEvent());
    }

    @GetMapping("event/findAll")
    @ResponseStatus(HttpStatus.FOUND)
    public Iterable<Event> findAllEvent() {
        return eventRepository.findAll();
    }

    @PostMapping("event/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Event createEvent(@RequestBody Event event) {
        return eventRepository.save(new EventBuilder()
                .setTitle(event.getTitle()).setDescription(event.getDescription()).setLocation(event.getLocation())
                .setStartDate(event.getStartDate()).setEndDate(event.getEndDate()).createEvent());
    }

    @GetMapping("event/find/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public Event findEventById(@PathVariable Integer id){
        return eventRepository.findById(id).orElse(null);
    }

    @DeleteMapping("event/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEventById(@PathVariable Integer id) {
        eventRepository.deleteById(id);
    }

    @PutMapping("event/update")
    @ResponseStatus(HttpStatus.OK)
    public Event updateEvent(@RequestBody Event event) {
        //TODO Remove the get() method
        Event toUpdate = eventRepository.findById(event.getId()).get();
        toUpdate.setLocation(event.getLocation());
        toUpdate.setStartDate(event.getStartDate());
        toUpdate.setEndDate(event.getEndDate());
        return eventRepository.save(toUpdate);
    }
}
