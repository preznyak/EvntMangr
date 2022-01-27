package hu.preznyak.eventmngr.integration.service;

import hu.preznyak.eventmngr.model.builder.EventBuilder;
import hu.preznyak.eventmngr.model.entity.Event;
import hu.preznyak.eventmngr.service.EventManagementService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class EventManagementServiceTest {

    @Autowired
    EventManagementService eventManagementService;

    @Test
    void isBeforeTest() throws InterruptedException {
        Event event = new EventBuilder().setDescription("Test").setLocation("TestLocation").setStartDate(LocalDateTime.now())
                .setTitle("TestTitle").setEndDate(LocalDateTime.now()).createEvent();
        Thread.sleep(20);
        Assertions.assertTrue(eventManagementService.isTheEventBeforeDate(event, LocalDateTime.now()));

    }
}
