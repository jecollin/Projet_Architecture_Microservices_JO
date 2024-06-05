package dauphine.fr.microservices.gestion_event.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.yaml.snakeyaml.events.Event;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "olympic-service", url = "${olympic-service.url}")
public interface OlympicServiceClient {
    @GetMapping("/olympic/events")
    List<Event> getAllEvents();

    @GetMapping("/olympic/events/{id}")
    Event getEventById(@PathVariable("id") UUID id);
}
