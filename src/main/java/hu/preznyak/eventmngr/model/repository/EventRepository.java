package hu.preznyak.eventmngr.model.repository;

import hu.preznyak.eventmngr.model.entity.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Integer> {
}
