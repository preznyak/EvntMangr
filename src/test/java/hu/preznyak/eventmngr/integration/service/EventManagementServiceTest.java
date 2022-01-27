package hu.preznyak.eventmngr.integration.service;

import hu.preznyak.eventmngr.model.builder.EventBuilder;
import hu.preznyak.eventmngr.model.entity.Event;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class EventManagementServiceTest {

    @Test
    void isBeforeTest() throws InterruptedException {
        Event event = new EventBuilder().setDescription("Test").setLocation("TestLocation").setStartDate(LocalDateTime.now())
                .setTitle("TestTitle").setEndDate(LocalDateTime.now()).createEvent();
        Thread.sleep(20);
        Assertions.assertTrue(event.getStartDate().isBefore(LocalDateTime.now()));

    }

    @Test
    void anotherDummyTest() {
        Assertions.assertTrue(5>3);
    }
}
