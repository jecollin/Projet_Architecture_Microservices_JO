package dauphine.fr.microservices.gestion_sports.clients;

import dauphine.fr.microservices.gestion_sports.entities.Sport;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "olympic-service", url = "${olympic-service.url}")
public interface OlympicServiceClient {
    @GetMapping("/olympic/sports")
    List<Sport> getAllSports();

    @GetMapping("/olympic/sports/{id}")
    Sport getSportById(@PathVariable("id") UUID id);
}
