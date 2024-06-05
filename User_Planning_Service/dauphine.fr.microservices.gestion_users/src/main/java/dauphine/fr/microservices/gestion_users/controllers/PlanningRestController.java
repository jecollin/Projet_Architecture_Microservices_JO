package dauphine.fr.microservices.gestion_users.controllers;

import dauphine.fr.microservices.gestion_users.entities.Planning;
import dauphine.fr.microservices.gestion_users.repositories.PlanningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/plannings")
public class PlanningRestController {

    @Autowired
    private PlanningRepository repo;

    // Get all plannings for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Planning>> getAllPlannings(@PathVariable Integer userId) {
        List<Planning> plannings = repo.findByUserId(userId);
        if (plannings.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(plannings);
    }

    // Get a specific planning by ID
    @GetMapping("/{id}")
    public ResponseEntity<Planning> getPlanningById(@PathVariable Integer id) {
        Optional<Planning> planning = repo.findById(id);
        return planning.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    // Create a new planning
    @PostMapping("")
    public ResponseEntity<Planning> createPlanning(@RequestBody Planning planning) {
        Planning newPlanning = repo.save(planning);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newPlanning.getId())
                .toUri();
        return ResponseEntity.created(location).body(newPlanning);
    }

    // Update a planning
    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePlanning(@RequestBody Planning updatedPlanning, @PathVariable Integer id) {
        Optional<Planning> existingPlanning = repo.findById(id);
        if (!existingPlanning.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Planning with ID " + id + " not found");
        }
        Planning existing = existingPlanning.get();
        existing.setUserId(updatedPlanning.getUserId());
        existing.setEventId(updatedPlanning.getEventId());
        existing.setScheduledTime(updatedPlanning.getScheduledTime());
        repo.save(existing);
        return ResponseEntity.ok(existing);
    }

    // Delete a planning
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlanning(@PathVariable Integer id) {
        Optional<Planning> existingPlanning = repo.findById(id);
        if (!existingPlanning.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Planning with ID " + id + " not found");
        }
        repo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
