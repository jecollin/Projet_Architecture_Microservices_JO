package dauphine.fr.microservices.gestion_sites.repositories;


import dauphine.fr.microservices.gestion_sites.entities.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SiteRepository extends JpaRepository<Site,Integer>{

    Optional<Site> findById(UUID id);

    List<Site> findBySportId(UUID sportId);

    List<Site> findByDate(LocalDate date);

    List<Site> findBySportIdAndDate(UUID sportId, LocalDate date);

    void deleteById(UUID id);

}
