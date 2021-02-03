package hu.preznyak.eventmngr.controller.event;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import hu.preznyak.eventmngr.exception.*;
import hu.preznyak.eventmngr.model.entity.Event;
import hu.preznyak.eventmngr.service.EventManagementService;

@RestController
@RequestMapping("/event")
public class EventController {

    EventManagementService eventManagementService;

    @Autowired
    public EventController(EventManagementService eventManagementService) {
        this.eventManagementService = eventManagementService;
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
        Event event = eventManagementService.findById(id);
        if (event == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEventById(@PathVariable Integer id) throws EntityNotFoundException {
    	eventManagementService.deleteById(id);
        return new ResponseEntity<>("Event deleted.", HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Event> updateEvent(@RequestBody Event event) throws EntityNotFoundException {
        return new ResponseEntity<>(eventManagementService.update(event), HttpStatus.OK);
    }

    @GetMapping("/find/contains/location")
    public ResponseEntity<List<Event>> findEventsByLocationContaining(@RequestParam String location) {
        return new ResponseEntity<>(eventManagementService.findEventsByLocationContaining(location), HttpStatus.FOUND);
    }

    @GetMapping("/find/title")
    public ResponseEntity<Event> findEventsByTitle(@RequestParam String title) {
        Event event = eventManagementService.findEventByTitle(title);
        return new ResponseEntity<>(event, HttpStatus.FOUND);
    }

    @GetMapping("/find/contains/title")
    public ResponseEntity<List<Event>> findEventsByTitleContaining(@RequestParam String titlePart) {
        return new ResponseEntity<>(eventManagementService.findEventsByTitleContaining(titlePart), HttpStatus.OK);
    }

    @GetMapping("/find/start/before")
    public ResponseEntity<List<Event>> findEventsByStartDateBefore(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate) {
        return new ResponseEntity<>(eventManagementService.findEventsByStartDateBefore(startDate), HttpStatus.OK);
    }

    @GetMapping("/find/start/after")
    public ResponseEntity<List<Event>> findEventsByStartDateAfter(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate) {
        return new ResponseEntity<>(eventManagementService.findEventsByStartDateAfter(startDate), HttpStatus.OK);
    }

    @GetMapping("/find/start/between")
    public ResponseEntity<List<Event>> findEventsByStartDateBetween(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) throws DateIntervalException {
        return new ResponseEntity<>(eventManagementService.findEventsByStartDateBetween(start, end), HttpStatus.OK);
    }
}
