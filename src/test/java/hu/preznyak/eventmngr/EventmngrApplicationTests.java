package hu.preznyak.eventmngr;

import hu.preznyak.eventmngr.model.repository.EventRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EventmngrApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	EventRepository eventRepository;

	@Test
	public void testFindEventById(){
		Assertions.assertNotNull(eventRepository.findById(321));
	}

}
