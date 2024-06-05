package dauphine.fr.microservices.olympic_service.clients;

import jdk.internal.event.Event;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "event-service", url = "${event-service.url}")
public interface EventServiceClient {
    @GetMapping("/events")
    List<Event> getAllEvents();

    @GetMapping("/events/{id}")
    Event getEventById(@PathVariable("id") UUID id);
}
