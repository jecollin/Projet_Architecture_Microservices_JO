package dauphine.fr.microservices.olympic_service.controllers;

import dauphine.fr.microservices.gestion_sports.entities.Sport;
import dauphine.fr.microservices.gestion_users.entities.Planning;
import dauphine.fr.microservices.olympic_service.services.OlympicService;
import jdk.internal.event.Event;
import jdk.vm.ci.code.site.Site;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/olympic")
public class OlympicRestController {

    @Autowired
    private OlympicService olympicService;

    @GetMapping("/events")
    public List<Event> getAllEvents() {
        return olympicService.getAllEvents();
    }

    @GetMapping("/sites")
    public List<Site> getAllSites() {
        return olympicService.getAllSites();
    }

    @GetMapping("/sports")
    public List<Sport> getAllSports() {
        return olympicService.getAllSports();
    }

    @GetMapping("/plannings/user/{userId}")
    public List<Planning> getUserPlannings(@PathVariable Integer userId) {
        return olympicService.getUserPlannings(userId);
    }

    @GetMapping("/sports/date/{date}/site/{siteId}")
    public List<Sport> getSportsByDateAndSite(@PathVariable LocalDate date, @PathVariable UUID siteId) {
        return olympicService.getSportsByDateAndSite(date, siteId);
    }

    @GetMapping("/sites/sport/{sportId}/date/{date}")
    public List<Site> getSitesBySportAndDate(@PathVariable UUID sportId, @PathVariable LocalDate date) {
        return olympicService.getSitesBySportAndDate(sportId, date);
    }
}
