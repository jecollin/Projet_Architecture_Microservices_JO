package dauphine.fr.microservices.gestion_users.repositories;

import dauphine.fr.microservices.gestion_users.entities.Planning;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanningRepository extends JpaRepository<Planning, Integer> {
    List<Planning> findByUserId(Integer userId);
}
