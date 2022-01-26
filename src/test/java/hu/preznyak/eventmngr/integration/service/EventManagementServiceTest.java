package hu.preznyak.eventmngr.integration.service;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import hu.preznyak.eventmngr.constant.GeneralMessages;
import hu.preznyak.eventmngr.exception.DateIntervalException;
import hu.preznyak.eventmngr.exception.EntityNotFoundException;
import hu.preznyak.eventmngr.model.entity.Event;
import hu.preznyak.eventmngr.model.repository.EventRepository;
import hu.preznyak.eventmngr.service.EventManagementService;

@SpringBootTest
public class EventManagementServiceTest {
	
	private static final String TITLE = "Test Title"; 
	private static final String DESCRIPTION = "Test Description";
	private static final String LOCATION = "Test Location";
	private static final LocalDateTime START_DATE = LocalDateTime.now();
	private static final LocalDateTime END_DATE = LocalDateTime.now().plusDays(1);
	
    @InjectMocks
    EventManagementService eventManagementService;
    
    @Mock
    EventRepository eventRepository;
    
    Optional<Event> event1;
    Optional<Event> event2;
    List<Event> events = new ArrayList<>();
    
    
    @BeforeEach
    public void setup() {
    	event1 = Optional.of(new Event(TITLE, DESCRIPTION, LOCATION, START_DATE, END_DATE));
    	event1.get().setId(1);
    	event2 = Optional.of(new Event(TITLE, DESCRIPTION, LOCATION, START_DATE, END_DATE));
    	events.add(event1.get());
    	events.add(event2.get());
    }
 /*
    @Test
    public void testFindEventById() {
    	when(eventRepository.findById(1)).thenReturn(event1);
    	when(eventRepository.findById(null)).thenThrow(new IllegalArgumentException(GeneralMessages.ID_CAN_NOT_BE_NULL));
    	
        assertThat(eventManagementService.findById(1)).isNotNull();
        assertThat(eventManagementService.findById(1)).isEqualTo(event1.get());
        
        assertThat(eventManagementService.findById(2)).isNull();
        
        assertThrows(IllegalArgumentException.class, () -> eventManagementService.findById(null));
    }*/

    @Test
    public void testFindAll() {
    	when(eventRepository.findAll()).thenReturn(events);
    	
    	assertThat(eventManagementService.findAll()).isNotEmpty();
    	assertThat(eventManagementService.findAll()).contains(event1.get());
    	assertThat(eventManagementService.findAll()).containsExactlyElementsOf(events);
    }
    
    @Test
    public void testUpdate() throws EntityNotFoundException {
    	Event testEvent = event1.get();
    	when(eventRepository.findById(1)).thenReturn(event1);
    	when(eventRepository.save(testEvent)).thenReturn(testEvent);
    	
    	assertThat(eventManagementService.update(testEvent)).isEqualTo(testEvent);
    }
    
    @Test
    public void testSave() {
    	Event testEvent = event1.get();
    	when(eventRepository.save(testEvent)).thenReturn(testEvent);
    	
    	assertThat(eventManagementService.save(testEvent)).isEqualTo(testEvent);
    }
    
    @Test
    public void testDelete() {
    	eventManagementService.delete(event1.get());
    	
    	verify(eventRepository, times(1)).delete(event1.get());
    }
    
    @Test
    public void testFindEventsByStartDateBefore() {
    	when(eventRepository.findEventsByStartDateBefore(START_DATE)).thenReturn(events);
    	
    	assertThat(eventManagementService.findEventsByStartDateBefore(START_DATE)).contains(event1.get());
    	assertThat(eventManagementService.findEventsByStartDateBefore(START_DATE)).containsExactlyElementsOf(events);
    	assertThat(eventManagementService.findEventsByStartDateBefore(START_DATE)).isNotEmpty();
    	
    }
    
    @Test
    public void testFindEventsByStartDateAfter() {
    	when(eventRepository.findEventsByStartDateAfter(START_DATE)).thenReturn(events);
    	
    	assertThat(eventManagementService.findEventsByStartDateAfter(START_DATE)).contains(event1.get());
    	assertThat(eventManagementService.findEventsByStartDateAfter(START_DATE)).containsExactlyElementsOf(events);
    	assertThat(eventManagementService.findEventsByStartDateAfter(START_DATE)).isNotEmpty();
    	
    }
    
    @Test
    public void testFindEventsByStartDateBetweenSuccess() throws DateIntervalException {
    	when(eventRepository.findEventsByStartDateBetween(START_DATE, END_DATE)).thenReturn(events);
    	
    	assertThat(eventManagementService.findEventsByStartDateBetween(START_DATE, END_DATE)).contains(event1.get());
    	assertThat(eventManagementService.findEventsByStartDateBetween(START_DATE, END_DATE)).containsExactlyElementsOf(events);
    	assertThat(eventManagementService.findEventsByStartDateBetween(START_DATE, END_DATE)).isNotEmpty();
    	
    }
    
    @Test
    public void testFindEventsByStartDateBetweenFailed() throws DateIntervalException {
    	when(eventRepository.findEventsByStartDateBetween(END_DATE, START_DATE)).thenThrow(DateIntervalException.class);
    	
    	assertThrows(DateIntervalException.class, () -> eventManagementService.findEventsByStartDateBetween(END_DATE, START_DATE));
    	try {
    		eventManagementService.findEventsByStartDateBetween(END_DATE, START_DATE);
    	} catch(DateIntervalException exception) {
    		assertEquals(exception.getErrorMessage(), GeneralMessages.START_AFTER_END_EXCEPTION);
    	}
    	
    }
    
    @Test
    public void testFindEventByTitle() {
    	when(eventRepository.findEventByTitle(TITLE)).thenReturn(event1);
    	
    	assertThat(eventManagementService.findEventByTitle(TITLE)).isEqualTo(event1.get());
    }
    
    @Test
    public void testFindEventsByTitleContaining() {
    	when(eventRepository.findEventsByTitleContaining(TITLE.substring(1))).thenReturn(events);
    	
    	assertThat(eventManagementService.findEventsByTitleContaining(TITLE.substring(1))).isNotEmpty();
    	assertThat(eventManagementService.findEventsByTitleContaining(TITLE.substring(1))).containsExactlyElementsOf(events);
    	
    }
    
    @Test
    public void testFindEventsByLocationContaining() {
    	when(eventRepository.findEventsByLocationContaining(LOCATION.substring(1))).thenReturn(events);
    	
    	assertThat(eventManagementService.findEventsByLocationContaining(LOCATION.substring(1))).isNotEmpty();
    	assertThat(eventManagementService.findEventsByLocationContaining(LOCATION.substring(1))).containsExactlyElementsOf(events);
    }
    
    
    
    
    
    

}
