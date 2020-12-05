package hu.preznyak.eventmngr.controller.event;

import hu.preznyak.eventmngr.model.builder.EventBuilder;
import hu.preznyak.eventmngr.model.entity.Event;
import hu.preznyak.eventmngr.model.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")
public class EventController {

    EventRepository eventRepository;

    @Autowired
    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @PostMapping("/create/title")
    public ResponseEntity<Event> createEventWithTitle(@RequestParam("title") String title) {

        return new ResponseEntity<>(eventRepository.save(new EventBuilder().setTitle(title).createEvent()), HttpStatus.CREATED);
    }

    @GetMapping("/findAll")
    public ResponseEntity<Iterable<Event>> findAllEvent() {
        return new ResponseEntity<>(eventRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event newEvent = eventRepository.save(new EventBuilder()
                .setTitle(event.getTitle()).setDescription(event.getDescription()).setLocation(event.getLocation())
                .setStartDate(event.getStartDate()).setEndDate(event.getEndDate()).createEvent());
        return new ResponseEntity<>(newEvent, HttpStatus.CREATED);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Event> findEventById(@PathVariable Integer id) {
        Event event = eventRepository.findById(id).orElse(null);
        if (event == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEventById(@PathVariable Integer id) {
        if (eventRepository.findById(id).orElse(null) == null) {
            return new ResponseEntity<>("Event does not exist.", HttpStatus.BAD_REQUEST);
        }
        eventRepository.deleteById(id);
        return new ResponseEntity<>("Event deleted.", HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Event> updateEvent(@RequestBody Event event) {

        Event toUpdate = eventRepository.findById(event.getId()).orElse(null);
        if(toUpdate == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        toUpdate.setLocation(event.getLocation());
        toUpdate.setStartDate(event.getStartDate());
        toUpdate.setEndDate(event.getEndDate());
        return new ResponseEntity<>(eventRepository.save(toUpdate), HttpStatus.OK);
    }
}
