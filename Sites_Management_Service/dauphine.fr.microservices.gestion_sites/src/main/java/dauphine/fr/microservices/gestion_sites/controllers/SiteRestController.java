package dauphine.fr.microservices.gestion_sites.controllers;

import dauphine.fr.microservices.gestion_sites.entities.Site;
import dauphine.fr.microservices.gestion_sites.repositories.SiteRepository;
import dauphine.fr.microservices.gestion_sites.clients.OlympicServiceClient;
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
@RequestMapping("/sites")
public class SiteRestController {

    @Autowired
    private SiteRepository repo;

    // Get all sites
    @GetMapping("")
    public ResponseEntity<List<Site>> getAllSites() {
        List<Site> sites = repo.findAll();
        if (sites.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sites);
    }

    // Get a single site by ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getSiteById(@PathVariable UUID id) {
        Optional<Site> site = repo.findById(id);
        if (site == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Site with ID " + id + " not found");
        }
        return ResponseEntity.ok(site);
    }

    // Update a site by ID
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSiteById(@RequestBody Site updatedSite, @PathVariable UUID id) {
        Optional<Site> site = repo.findById(id);
        if (!site.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Site with ID " + id + " not found");
        }
        Site existingSite = site.get();
        existingSite.setName(updatedSite.getName());
        existingSite.setGeographicalInfo(updatedSite.getGeographicalInfo());
        existingSite.setIsParalympic(updatedSite.getIsParalympic());
        repo.save(existingSite);
        return ResponseEntity.ok(existingSite);
    }

    // Delete a site by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSiteById(@PathVariable UUID id) {
        Optional<Site> site = repo.findById(id);
        if (!site.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Site with ID " + id + " not found");
        }
        repo.deleteById(id);
        return ResponseEntity.ok("Site with ID " + id + " deleted successfully");
    }

    // Create a new site
    @PostMapping("")
    public ResponseEntity<Site> createSite(@RequestBody Site site) {
        Site savedSite = repo.save(site);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedSite.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedSite);
    }
}
