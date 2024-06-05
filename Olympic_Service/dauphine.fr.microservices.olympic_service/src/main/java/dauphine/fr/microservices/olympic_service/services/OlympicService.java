package dauphine.fr.microservices.olympic_service.services;

import dauphine.fr.microservices.gestion_sports.entities.Sport;
import dauphine.fr.microservices.gestion_users.entities.Planning;
import dauphine.fr.microservices.olympic_service.clients.EventServiceClient;
import dauphine.fr.microservices.olympic_service.clients.SiteServiceClient;
import dauphine.fr.microservices.olympic_service.clients.SportServiceClient;
import dauphine.fr.microservices.olympic_service.clients.UserPlanningServiceClient;
import jdk.internal.event.Event;
import jdk.vm.ci.code.site.Site;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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
        // Implementation needed for fetching sports by date and site
        return null;
    }

    public List<Site> getSitesBySportAndDate(UUID sportId, LocalDate date) {
        // Implementation needed for fetching sites by sport and date
        return null;
    }
}
