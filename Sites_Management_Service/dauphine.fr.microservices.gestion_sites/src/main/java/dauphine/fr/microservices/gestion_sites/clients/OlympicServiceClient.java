package dauphine.fr.microservices.gestion_sites.clients;

import dauphine.fr.microservices.gestion_sites.entities.Site;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "olympic-service", url = "${olympic-service.url}")
public interface OlympicServiceClient {
    @GetMapping("/olympic/sites")
    List<Site> getAllSites();

    @GetMapping("/olympic/sites/{id}")
    Site getSiteById(@PathVariable("id") UUID id);
}
