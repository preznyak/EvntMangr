package hu.preznyak.eventmngr.controller.event;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.preznyak.eventmngr.exception.DateIntervalException;
import hu.preznyak.eventmngr.model.builder.EventBuilder;
import hu.preznyak.eventmngr.model.entity.Event;
import hu.preznyak.eventmngr.model.repository.EventRepository;
import hu.preznyak.eventmngr.service.EventManagementService;

@RestController
@RequestMapping("/event")
public class EventController {

    EventRepository eventRepository;
    EventManagementService eventManagementService;

    @Autowired
    public EventController(EventRepository eventRepository, EventManagementService eventManagementService) {
        this.eventRepository = eventRepository;
        this.eventManagementService = eventManagementService;
    }

    @PostMapping("/create/title")
    public ResponseEntity<Event> createEventWithTitle(@RequestParam("title") String title) {

        return new ResponseEntity<>(eventRepository.save(new EventBuilder().setTitle(title).createEvent()), HttpStatus.CREATED);
    }

    @GetMapping("/findAll")
    public ResponseEntity<Iterable<Event>> findAllEvent() {
        return new ResponseEntity<>(eventManagementService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Event> createEvent(@Valid @RequestBody Event event) {
        Event newEvent = eventManagementService.save(event);
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
        if (toUpdate == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        toUpdate.setLocation(event.getLocation());
        toUpdate.setStartDate(event.getStartDate());
        toUpdate.setEndDate(event.getEndDate());
        return new ResponseEntity<>(eventRepository.save(toUpdate), HttpStatus.OK);
    }

    @GetMapping("/find/contains/location")
    public ResponseEntity<List<Event>> findEventsByLocationContaining(@RequestParam String location) {
        return new ResponseEntity<>(eventRepository.findEventsByLocationContaining(location), HttpStatus.FOUND);
    }

    @GetMapping("/find/title")
    public ResponseEntity<Event> findEventsByTitle(@RequestParam String title) {
        Event event = eventRepository.findEventByTitle(title).orElse(null);
        if (event == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(event, HttpStatus.FOUND);
    }

    @GetMapping("/find/contains/title")
    public ResponseEntity<List<Event>> findEventsByTitleContaining(@RequestParam String titlePart) {
        return new ResponseEntity<>(eventRepository.findEventsByTitleContaining(titlePart), HttpStatus.OK);
    }

    @GetMapping("/find/start/before")
    public ResponseEntity<List<Event>> findEventsByStartDateBefore(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate) {
        return new ResponseEntity<>(eventManagementService.findEventsByStartDateBefore(startDate), HttpStatus.OK);
    }

    @GetMapping("/find/start/after")
    public ResponseEntity<List<Event>> findEventsByStartDateAfter(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate) {
        return new ResponseEntity<>(eventRepository.findEventsByStartDateAfter(startDate), HttpStatus.OK);
    }

    @GetMapping("/find/start/between")
    public ResponseEntity<List<Event>> findEventsByStartDateBetween(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) throws DateIntervalException {
        return new ResponseEntity<>(eventRepository.findEventsByStartDateBetween(start, end), HttpStatus.OK);
    }
}
