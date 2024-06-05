package dauphine.fr.microservices.gestion_event.controllers;

import dauphine.fr.microservices.gestion_event.entities.Event;
import dauphine.fr.microservices.gestion_event.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventRestController {

    @Autowired
    private EventRepository repo;

    // Get all events
    @GetMapping("")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = repo.findAll();
        if (events.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(events);
    }

    // Get an event by ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getEventById(@PathVariable UUID id) {
        Optional<Event> event = repo.findById(id);
        if (!event.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Event with ID " + id + " not found");
        }
        return ResponseEntity.ok(event.get());
    }

    // Update an event by ID
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEventById(@RequestBody Event updatedEvent, @PathVariable UUID id) {
        Optional<Event> event = repo.findById(id);
        if (!event.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Event with ID " + id + " not found");
        }
        Event existingEvent = event.get();
        existingEvent.setName(updatedEvent.getName());
        existingEvent.setSiteId(updatedEvent.getSiteId());
        existingEvent.setDate(updatedEvent.getDate());
        existingEvent.setStartTime(updatedEvent.getStartTime());
        existingEvent.setEndTime(updatedEvent.getEndTime());
        existingEvent.setSportId(updatedEvent.getSportId());
        repo.save(existingEvent);
        return ResponseEntity.ok(existingEvent);
    }

    // Delete an event by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEventById(@PathVariable UUID id) {
        Optional<Event> event = repo.findById(id);
        if (!event.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Event with ID " + id + " not found");
        }
        repo.deleteById(id);
        return ResponseEntity.ok("Event with ID " + id + " deleted successfully");
    }

    // Create a new event
    @PostMapping("")
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event savedEvent = repo.save(event);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedEvent.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedEvent);
    }
}
