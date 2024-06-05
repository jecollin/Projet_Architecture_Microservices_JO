package dauphine.fr.microservices.gestion_event.repositories;

import dauphine.fr.microservices.gestion_event.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {

    Optional<Event> findById(UUID id);

    void deleteById(UUID id);

    @Query("SELECT e FROM Event e WHERE e.date = :date")
    List<Event> findByDate(LocalDate date);

    @Query("SELECT e FROM Event e WHERE e.siteId = :siteId")
    List<Event> findBySiteId(Integer siteId);

    @Query("SELECT e FROM Event e WHERE e.date = :date AND e.siteId = :siteId")
    List<Event> findByDateAndSiteId(LocalDate date, Integer siteId);
}
