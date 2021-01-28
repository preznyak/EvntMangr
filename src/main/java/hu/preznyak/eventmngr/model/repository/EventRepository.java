package hu.preznyak.eventmngr.model.repository;

import hu.preznyak.eventmngr.exception.DateIntervalException;
import hu.preznyak.eventmngr.model.entity.Event;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends CrudRepository<Event, Integer> {

    List<Event> findEventsByStartDateAfter(LocalDateTime startDate);
    List<Event> findEventsByStartDateBefore(LocalDateTime startDate);
    List<Event> findEventsByStartDateBetween(LocalDateTime start, LocalDateTime end) throws DateIntervalException;
    Optional<Event> findEventByTitle(String title);
    List<Event> findEventsByTitleContaining(String titlePart);
    List<Event> findEventsByLocationContaining(String location);


}
