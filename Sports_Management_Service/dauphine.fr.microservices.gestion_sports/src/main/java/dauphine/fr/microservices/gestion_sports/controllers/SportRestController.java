package dauphine.fr.microservices.gestion_sports.controllers;

import dauphine.fr.microservices.gestion_sports.entities.Sport;
import dauphine.fr.microservices.gestion_sports.repositories.SportRepository;
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
@RequestMapping("/sports")
public class SportRestController {

    @Autowired
    private SportRepository repo;

    // Get all sports
    @GetMapping("")
    public ResponseEntity<List<Sport>> getAllSports() {
        List<Sport> sports = repo.findAll();
        if (sports.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sports);
    }

    // Get a sport by ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getSportById(@PathVariable UUID id) {
        Optional<Sport> sport = repo.findById(id);
        if (!sport.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Sport with ID " + id + " not found");
        }
        return ResponseEntity.ok(sport.get());
    }

    // Create a new sport
    @PostMapping("")
    public ResponseEntity<Sport> createSport(@RequestParam String name) {
        Sport newSport = new Sport(UUID.randomUUID(), name);
        Sport savedSport = repo.save(newSport);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedSport.getId()).toUri();
        return ResponseEntity.created(location).body(savedSport);
    }

    // Update a sport by ID
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSportById(@RequestBody Sport updatedSport, @PathVariable UUID id) {
        Optional<Sport> sport = repo.findById(id);
        if (!sport.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Sport with ID " + id + " not found");
        }
        Sport existingSport = sport.get();
        existingSport.setName(updatedSport.getName());
        repo.save(existingSport);
        return ResponseEntity.ok(existingSport);
    }

    // Delete a sport by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSportById(@PathVariable UUID id) {
        Optional<Sport> sport = repo.findById(id);
        if (!sport.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Sport with ID " + id + " not found");
        }
        repo.delete(sport.get());
        return ResponseEntity.ok("Sport with ID " + id + " deleted successfully");
    }
}
