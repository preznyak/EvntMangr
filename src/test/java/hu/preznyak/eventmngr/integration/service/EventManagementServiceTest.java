package hu.preznyak.eventmngr.integration.service;

import static org.assertj.core.api.Assertions.assertThat;
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
    
    Optional<Event> event;
    
    
    @BeforeEach
    public void setup() {
    	event = Optional.of(new Event(TITLE, DESCRIPTION, LOCATION, START_DATE, END_DATE));
    }

    @Test
    public void testFindEventById() {
    	when(eventRepository.findById(1)).thenReturn(event);
    	when(eventRepository.findById(null)).thenThrow(new IllegalArgumentException(GeneralMessages.ID_CAN_NOT_BE_NULL));
    	
        assertThat(eventManagementService.findById(1)).isNotNull();
        assertThat(eventManagementService.findById(1)).isEqualTo(event.get());
        
        assertThat(eventManagementService.findById(2)).isNull();
        
        assertThrows(IllegalArgumentException.class, () -> eventManagementService.findById(null));
    }

    @Test
    public void testFindAll() {
    	List<Event> events = new ArrayList<>();
    	events.add(event.get());
    	when(eventRepository.findAll()).thenReturn(events);
    	
    	assertThat(eventManagementService.findAll()).isNotEmpty();
    	assertThat(eventManagementService.findAll()).contains(event.get());
    }
    
    @Test
    public void testUpdate() {
    	Event testEvent = event.get();
    	when(eventRepository.save(testEvent)).thenReturn(testEvent);
    	
    	assertThat(eventManagementService.update(testEvent)).isEqualTo(testEvent);
    }
    
    @Test
    public void testSave() {
    	Event testEvent = event.get();
    	when(eventRepository.save(testEvent)).thenReturn(testEvent);
    	
    	assertThat(eventManagementService.save(testEvent)).isEqualTo(testEvent);
    }
    
    @Test
    public void testDelete() {
    	eventManagementService.delete(event.get());
    	
    	verify(eventRepository, times(1)).delete(event.get());
    }
    
    

}
