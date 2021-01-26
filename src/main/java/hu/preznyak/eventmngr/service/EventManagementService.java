package hu.preznyak.eventmngr.service;

import hu.preznyak.eventmngr.model.entity.Event;
import hu.preznyak.eventmngr.model.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventManagementService extends GenericService<Event> {
    private EventRepository eventRepository;

    @Autowired
    public EventManagementService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event findById(Integer id) {
        return eventRepository.findById(id).orElse(null);
    }

    @Override
    public Event update(Event entity) {
        return eventRepository.save(entity);
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
}
