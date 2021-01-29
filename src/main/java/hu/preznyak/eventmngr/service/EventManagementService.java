package hu.preznyak.eventmngr.service;

import java.time.LocalDateTime;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.preznyak.eventmngr.constant.GeneralMessages;
import hu.preznyak.eventmngr.exception.DateIntervalException;
import hu.preznyak.eventmngr.exception.EntityNotFoundException;
import hu.preznyak.eventmngr.exception.EventNotFoundException;
import hu.preznyak.eventmngr.model.entity.Event;
import hu.preznyak.eventmngr.model.repository.EventRepository;

@Service
public class EventManagementService extends GenericService<Event> {
	
	private final org.apache.logging.log4j.Logger logger = LogManager.getLogger();
    private EventRepository eventRepository;

    @Autowired
    public EventManagementService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event findById(Integer id) {
    	Event event = null;
    	if (id != null) {
    		event = eventRepository.findById(id).orElse(null);
    	} else {
    		throw new IllegalArgumentException(GeneralMessages.ID_CAN_NOT_BE_NULL);
    	}
        return event;
    }

    @Override
    public Event update(Event entity) throws EntityNotFoundException {
    	Event event = eventRepository.findById(entity.getId()).orElse(null);
    	if (event == null) {
    		throw new EventNotFoundException(GeneralMessages.EVENT_NOT_FOUND);
    	}
    	event.setTitle(entity.getTitle());
    	event.setLocation(entity.getLocation());
    	event.setDescription(entity.getDescription());
    	event.setStartDate(entity.getStartDate());
    	event.setEndDate(entity.getEndDate());
        return eventRepository.save(event);
    }

    @Override
    public Iterable<Event> findAll() {
        return eventRepository.findAll();
    }

    @Override
    public Event save(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public void delete(Event entity) {
        eventRepository.delete(entity);
    }
    
    public List<Event> findEventsByStartDateBefore(LocalDateTime startDate) {
    	logger.info("Searching event before date: " + startDate);
    	return eventRepository.findEventsByStartDateBefore(startDate);
    }
    
    public List<Event> findEventsByStartDateAfter(LocalDateTime startDate) {
    	return eventRepository.findEventsByStartDateAfter(startDate);
    }
    
    public List<Event> findEventsByStartDateBetween(LocalDateTime start, LocalDateTime end) throws DateIntervalException {
    	if (start.isBefore(end)) {
    		return eventRepository.findEventsByStartDateBetween(start, end);
    	} else {
    		throw new DateIntervalException(GeneralMessages.START_AFTER_END_EXCEPTION);
    	}
    }
    
    public Event findEventByTitle(String title) {
    	return eventRepository.findEventByTitle(title).orElse(null);
    }
    
    public List<Event> findEventsByTitleContaining(String titlePart) {
    	return eventRepository.findEventsByTitleContaining(titlePart);
    }
    
    public List<Event> findEventsByLocationContaining(String location) {
    	return eventRepository.findEventsByLocationContaining(location);
    }
    
}
