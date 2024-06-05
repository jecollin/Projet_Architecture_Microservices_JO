package dauphine.fr.microservices.olympic_service.clients;

import dauphine.fr.microservices.gestion_sports.entities.Sport;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "sport-service", url = "${sport-service.url}")
public interface SportServiceClient {
    @GetMapping("/sports")
    List<Sport> getAllSports();

    @GetMapping("/sports/{id}")
    Sport getSportById(@PathVariable("id") UUID id);
}
