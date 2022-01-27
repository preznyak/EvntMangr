package hu.preznyak.eventmngr.service;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	
	private final Logger logger = LogManager.getLogger();
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
    		logger.info("Event found with id " + id + " :" + event.toString());
    	} else {
    		logger.error("Event search with id: null");
    		throw new IllegalArgumentException(GeneralMessages.ID_CAN_NOT_BE_NULL);
    	}
        return event;
    }

    @Override
    public Event update(Event entity) throws EntityNotFoundException {
    	Event event = eventRepository.findById(entity.getId()).orElse(null);
    	if (event == null) {
    		logger.error("Event is null, can not be updated.");
    		throw new EventNotFoundException(GeneralMessages.EVENT_NOT_FOUND);
    	}
    	event.setTitle(entity.getTitle());
    	event.setLocation(entity.getLocation());
    	event.setDescription(entity.getDescription());
    	event.setStartDate(entity.getStartDate());
    	event.setEndDate(entity.getEndDate());
    	logger.info("Event updated: " + event.toString());
        return eventRepository.save(event);
    }

    @Override
    public Iterable<Event> findAll() {
    	logger.info("Finding all events.");
        return eventRepository.findAll();
    }

    @Override
    public Event save(Event event) {
    	logger.info("New event created: " + event.toString());
        return eventRepository.save(event);
    }

    @Override
    public void delete(Event entity) {
    	logger.info("Event to be deleted: " + entity.toString());
        eventRepository.delete(entity);
    }
    
	@Override
	public void deleteById(Integer id) throws EventNotFoundException {
		if (eventRepository.findById(id).isEmpty()) {
	    	logger.info("Event event not found with id: " + id);
			throw new EventNotFoundException(GeneralMessages.EVENT_NOT_FOUND);
        } 
    	logger.info("Event to be deleted with id: " + id);
        eventRepository.deleteById(id);		
	}
    
    public List<Event> findEventsByStartDateBefore(LocalDateTime startDate) {
    	logger.info("Searching event by start date before: " + startDate);
    	return eventRepository.findEventsByStartDateBefore(startDate);
    }
    
    public List<Event> findEventsByStartDateAfter(LocalDateTime startDate) {
    	logger.info("Searching event by start date after: " + startDate);
    	return eventRepository.findEventsByStartDateAfter(startDate);
    }
    
    public List<Event> findEventsByStartDateBetween(LocalDateTime start, LocalDateTime end) throws DateIntervalException {
    	if (start.isBefore(end)) {
    		logger.info("Searching event by start date between: " + start + " and " + end);
    		return eventRepository.findEventsByStartDateBetween(start, end);
    	} else {
    		logger.warn("End date is before Start date.");
    		throw new DateIntervalException(GeneralMessages.START_AFTER_END_EXCEPTION);
    	}
    }
    
    public Event findEventByTitle(String title) {
    	logger.info("Searching event by title: " + title);
    	Event event = null;
    	if (title != null) {
    		event = eventRepository.findEventByTitle(title).orElse(null);
    		logger.info("Event found with title " + title + " :" + event.toString());
    	} else {
    		logger.error("Event search with title: null");
    		throw new IllegalArgumentException(GeneralMessages.EVENT_TITLE_CAN_NOT_BE_NULL);
    	}
        return event;
    }
    
    public List<Event> findEventsByTitleContaining(String titlePart) {
    	logger.info("Searching event containing title: " + titlePart);
    	return eventRepository.findEventsByTitleContaining(titlePart);
    }
    
    public List<Event> findEventsByLocationContaining(String location) {
    	logger.info("Searching event by location: " + location);
    	return eventRepository.findEventsByLocationContaining(location);
    }


    public boolean isTheEventBeforeDate(Event event, LocalDateTime date){
    	if(event.getStartDate().isBefore(date)){
    		return true;
		}
    	return false;
	}
    
}
