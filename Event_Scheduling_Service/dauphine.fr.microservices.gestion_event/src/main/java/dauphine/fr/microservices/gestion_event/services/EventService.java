package dauphine.fr.microservices.gestion_event.services;

import dauphine.fr.microservices.gestion_event.entities.Event;
import dauphine.fr.microservices.gestion_event.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    // Get all events
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Get event by ID
    public Optional<Event> getEventById(UUID id) {
        return eventRepository.findById(id);
    }

    // Create a new event
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    // Update an event by ID
    public Optional<Event> updateEvent(UUID id, Event updatedEvent) {
        return eventRepository.findById(id).map(event -> {
            event.setName(updatedEvent.getName());
            event.setSiteId(updatedEvent.getSiteId());
            event.setDate(updatedEvent.getDate());
            event.setStartTime(updatedEvent.getStartTime());
            event.setEndTime(updatedEvent.getEndTime());
            event.setSportId(updatedEvent.getSportId());
            return eventRepository.save(event);
        });
    }

    // Delete an event by ID
    public boolean deleteEvent(UUID id) {
        return eventRepository.findById(id).map(event -> {
            eventRepository.deleteById(id);
            return true;
        }).orElse(false);
    }

    // Find events by date
    public List<Event> findByDate(LocalDate date) {
        return eventRepository.findByDate(date);
    }

    // Find events by site ID
    public List<Event> findBySiteId(Integer siteId) {
        return eventRepository.findBySiteId(siteId);
    }

    // Find events by date and site ID
    public List<Event> findByDateAndSiteId(LocalDate date, Integer siteId) {
        return eventRepository.findByDateAndSiteId(date, siteId);
    }
}
