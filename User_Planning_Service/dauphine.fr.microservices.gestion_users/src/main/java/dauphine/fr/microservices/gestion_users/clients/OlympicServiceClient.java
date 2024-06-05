package dauphine.fr.microservices.gestion_users.clients;

import dauphine.fr.microservices.gestion_users.entities.Planning;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "olympic-service", url = "${olympic-service.url}")
public interface OlympicServiceClient {
    @GetMapping("/olympic/plannings/")
    List<Planning> getAllPlannings();

    @GetMapping("/olympic/plannings/{id}")
    Planning getPlanningById(@PathVariable("id") UUID id);
}
