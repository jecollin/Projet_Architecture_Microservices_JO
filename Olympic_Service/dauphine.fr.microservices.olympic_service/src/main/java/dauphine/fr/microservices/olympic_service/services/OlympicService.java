package dauphine.fr.microservices.olympic_service.services;

import dauphine.fr.microservices.gestion_event.entities.Event;
import dauphine.fr.microservices.gestion_sites.entities.Site;
import dauphine.fr.microservices.gestion_sports.entities.Sport;
import dauphine.fr.microservices.gestion_users.entities.Planning;
import dauphine.fr.microservices.olympic_service.clients.EventServiceClient;
import dauphine.fr.microservices.olympic_service.clients.SiteServiceClient;
import dauphine.fr.microservices.olympic_service.clients.SportServiceClient;
import dauphine.fr.microservices.olympic_service.clients.UserPlanningServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OlympicService {

    @Autowired
    private EventServiceClient eventServiceClient;

    @Autowired
    private SiteServiceClient siteServiceClient;

    @Autowired
    private SportServiceClient sportServiceClient;

    @Autowired
    private UserPlanningServiceClient userPlanningServiceClient;

    public List<Event> getAllEvents() {
        return eventServiceClient.getAllEvents();
    }

    public List<Site> getAllSites() {
        return siteServiceClient.getAllSites();
    }

    public List<Sport> getAllSports() {
        return sportServiceClient.getAllSports();
    }

    public List<Planning> getUserPlannings(Integer userId) {
        return userPlanningServiceClient.getAllPlannings(userId);
    }

    public List<Sport> getSportsByDateAndSite(LocalDate date, UUID siteId) {
        List<Sport> allSports = sportServiceClient.getAllSports();
        return allSports.stream()
                .filter(sport -> {
                    List<Event> events = eventServiceClient.getAllEvents();
                    return events.stream()
                            .anyMatch(event -> event.getDate().equals(date)
                                    && event.getSiteId().equals(siteId)
                                    && event.getSportId().equals(sport.getId()));
                })
                .collect(Collectors.toList());
    }

    public List<Site> getSitesBySportAndDate(UUID sportId, LocalDate date) {
        List<Site> allSites = siteServiceClient.getAllSites();
        return allSites.stream()
                .filter(site -> {
                    List<Event> events = eventServiceClient.getAllEvents();
                    return events.stream()
                            .anyMatch(event -> event.getDate().equals(date)
                                    && event.getSiteId().equals(site.getId())
                                    && event.getSportId().equals(sportId));
                })
                .collect(Collectors.toList());
    }
}
