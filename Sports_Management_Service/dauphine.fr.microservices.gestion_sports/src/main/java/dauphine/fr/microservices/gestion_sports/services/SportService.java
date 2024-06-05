package dauphine.fr.microservices.gestion_sports.services;

import dauphine.fr.microservices.gestion_sports.entities.Sport;
import dauphine.fr.microservices.gestion_sports.repositories.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SportService {

    @Autowired
    private SportRepository sportRepository;

    // Get all sports
    public List<Sport> getAllSports() {
        return sportRepository.findAll();
    }

    // Get sport by ID
    public Optional<Sport> getSportById(UUID id) {
        return sportRepository.findById(id);
    }

    // Create a new sport
    public Sport createSport(Sport sport) {
        return sportRepository.save(sport);
    }

    // Update a sport by ID
    public Optional<Sport> updateSport(UUID id, Sport updatedSport) {
        return sportRepository.findById(id).map(sport -> {
            sport.setName(updatedSport.getName());
            return sportRepository.save(sport);
        });
    }

    // Delete a sport by ID
    public boolean deleteSport(UUID id) {
        return sportRepository.findById(id).map(sport -> {
            sportRepository.delete(sport);
            return true;
        }).orElse(false);
    }
}
