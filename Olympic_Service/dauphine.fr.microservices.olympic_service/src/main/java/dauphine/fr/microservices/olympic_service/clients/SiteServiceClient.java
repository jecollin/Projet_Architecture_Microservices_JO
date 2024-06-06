package dauphine.fr.microservices.olympic_service.clients;

import dauphine.fr.microservices.gestion_sites.entities.Site;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "site-service", url = "${site-service.url}")
public interface SiteServiceClient {
    @GetMapping("/sites")
    List<Site> getAllSites();

    @GetMapping("/sites/{id}")
    Site getSiteById(@PathVariable("id") UUID id);
}
