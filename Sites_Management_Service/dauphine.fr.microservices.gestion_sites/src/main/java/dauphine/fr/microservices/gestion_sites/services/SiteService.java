package dauphine.fr.microservices.gestion_sites.services;

import dauphine.fr.microservices.gestion_sites.entities.Site;
import dauphine.fr.microservices.gestion_sites.repositories.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SiteService {

    @Autowired
    private SiteRepository siteRepository;

    public List<Site> getAllSites() {
        return siteRepository.findAll();
    }

    public Optional<Site> getSiteById(UUID id) {
        return siteRepository.findById(id);
    }

    public Site createSite(Site site) {
        return siteRepository.save(site);
    }

    public Optional<Site> updateSite(UUID id, Site updatedSite) {
        return siteRepository.findById(id).map(site -> {
            site.setName(updatedSite.getName());
            site.setGeographicalInfo(updatedSite.getGeographicalInfo());
            site.setIsParalympic(updatedSite.getIsParalympic());
            return siteRepository.save(site);
        });
    }

    public boolean deleteSite(UUID id) {
        return siteRepository.findById(id).map(site -> {
            siteRepository.delete(site);
            return true;
        }).orElse(false);
    }

    public List<Site> findBySportId(UUID sportId) {
        // Assuming you have a method to fetch sites by sportId in repository
        return siteRepository.findBySportId(sportId);
    }

    public List<Site> findByDate(LocalDate date) {
        // Assuming you have a method to fetch sites by date in repository
        return siteRepository.findByDate(date);
    }

    public List<Site> findBySportIdAndDate(UUID sportId, LocalDate date) {
        // Assuming you have a method to fetch sites by sportId and date in repository
        return siteRepository.findBySportIdAndDate(sportId, date);
    }
}
