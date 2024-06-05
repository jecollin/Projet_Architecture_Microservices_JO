package dauphine.fr.microservices.gestion_users.services;

import dauphine.fr.microservices.gestion_users.entities.Planning;
import dauphine.fr.microservices.gestion_users.repositories.PlanningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanningService {

    @Autowired
    private PlanningRepository planningRepository;

    // Get all plannings for a user
    public List<Planning> getAllPlannings(Integer userId) {
        return planningRepository.findByUserId(userId);
    }

    // Get a specific planning by ID
    public Optional<Planning> getPlanningById(Integer id) {
        return planningRepository.findById(id);
    }

    // Create a new planning
    public Planning createPlanning(Planning planning) {
        return planningRepository.save(planning);
    }

    // Update a planning
    public Optional<Planning> updatePlanning(Integer id, Planning updatedPlanning) {
        return planningRepository.findById(id).map(planning -> {
            planning.setUserId(updatedPlanning.getUserId());
            planning.setEventId(updatedPlanning.getEventId());
            planning.setScheduledTime(updatedPlanning.getScheduledTime());
            return planningRepository.save(planning);
        });
    }

    // Delete a planning
    public boolean deletePlanning(Integer id) {
        return planningRepository.findById(id).map(planning -> {
            planningRepository.delete(planning);
            return true;
        }).orElse(false);
    }
}
