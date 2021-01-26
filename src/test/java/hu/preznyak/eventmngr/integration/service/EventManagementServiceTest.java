package hu.preznyak.eventmngr.integration.service;

import hu.preznyak.eventmngr.model.builder.EventBuilder;
import hu.preznyak.eventmngr.model.entity.Event;
import hu.preznyak.eventmngr.service.EventManagementService;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class EventManagementServiceTest {

    @Autowired
    EventManagementService eventManagementService;

    @Test
    public void testFindEventById() {
        TestCase.assertNotNull(eventManagementService.findById(321));
    }

    @Test
    public void testSaveEvent() {
        EventBuilder eventBuilder = new EventBuilder();
        Event newEvent = eventManagementService.save(eventBuilder.setTitle("Dukai Regina és Horgos Ármin esküvője").setStartDate(LocalDateTime.now()).setLocation("Püspökladány").createEvent());
        TestCase.assertEquals(newEvent, eventManagementService.findById(newEvent.getId()));
    }
}
