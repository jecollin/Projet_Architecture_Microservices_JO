package dauphine.fr.microservices.gestion_sports.repositories;

import dauphine.fr.microservices.gestion_sports.entities.Sport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SportRepository extends JpaRepository<Sport, UUID> {
}
