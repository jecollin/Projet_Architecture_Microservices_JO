package dauphine.fr.microservices.gestion_event;

import dauphine.fr.microservices.gestion_event.controllers.EventRestController;
import dauphine.fr.microservices.gestion_event.entities.Event;
import dauphine.fr.microservices.gestion_event.repositories.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.testng.annotations.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@SpringBootTest
public class EventRestControllerTest {

	@Mock
	private EventRepository eventRepository;

	@InjectMocks
	private EventRestController eventRestController;

	private Event event1;
	private Event event2;
	private UUID id1;
	private UUID id2;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		id1 = UUID.randomUUID();
		id2 = UUID.randomUUID();
		event1 = new Event("Event 1", 1, LocalDate.now(), LocalTime.now(), LocalTime.now().plusHours(2), 1);
		event1.setId(id1);
		event2 = new Event("Event 2", 2, LocalDate.now().plusDays(1), LocalTime.now(), LocalTime.now().plusHours(3), 2);
		event2.setId(id2);

		given(eventRepository.findAll()).willReturn(Arrays.asList(event1, event2));
		given(eventRepository.findById(id1)).willReturn(Optional.of(event1));
		given(eventRepository.findById(id2)).willReturn(Optional.of(event2));
	}

	@Test
	public void testGetAllEvents() {
		ResponseEntity<List<Event>> response = eventRestController.getAllEvents();

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotEmpty();
		assertThat(response.getBody()).contains(event1, event2);

		verify(eventRepository).findAll();
	}

	@Test
	public void testGetEventById() {
		ResponseEntity<Object> response = eventRestController.getEventById(id1);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo(event1);

		verify(eventRepository).findById(id1);
	}

	@Test
	public void testCreateEvent() {
		Event newEvent = new Event("New Event", 3, LocalDate.now().plusDays(2), LocalTime.now(), LocalTime.now().plusHours(1), 3);
		given(eventRepository.save(newEvent)).willReturn(newEvent);

		ResponseEntity<Event> response = eventRestController.createEvent(newEvent);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getBody()).isEqualTo(newEvent);

		verify(eventRepository).save(newEvent);
	}

	@Test
	public void testUpdateEvent() {
		Event updatedEvent = new Event("Updated Event", 1, LocalDate.now(), LocalTime.now(), LocalTime.now().plusHours(2), 1);
		updatedEvent.setId(id1);
		given(eventRepository.findById(id1)).willReturn(Optional.of(event1));
		given(eventRepository.save(event1)).willReturn(updatedEvent);

		ResponseEntity<Object> response = eventRestController.updateEventById(updatedEvent, id1);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo(updatedEvent);

		verify(eventRepository).findById(id1);
		verify(eventRepository).save(event1);
	}

	@Test
	public void testDeleteEvent() {
		given(eventRepository.findById(id1)).willReturn(Optional.of(event1));

		ResponseEntity<Object> response = eventRestController.deleteEventById(id1);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo("Event with ID " + id1 + " deleted successfully");

		verify(eventRepository).findById(id1);
		verify(eventRepository, times(1)).deleteById(id1);
	}
}
