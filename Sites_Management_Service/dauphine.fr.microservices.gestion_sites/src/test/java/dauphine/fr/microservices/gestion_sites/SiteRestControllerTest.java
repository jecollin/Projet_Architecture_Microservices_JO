package dauphine.fr.microservices.gestion_sites;

import dauphine.fr.microservices.gestion_sites.controllers.SiteRestController;
import dauphine.fr.microservices.gestion_sites.entities.Site;
import dauphine.fr.microservices.gestion_sites.repositories.SiteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SiteRestController.class)
public class SiteRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SiteRepository siteRepository;

    private Site sampleSite;

    @BeforeEach
    public void setup() {
        UUID siteId = UUID.randomUUID();
        sampleSite = new Site("Test Site", "Test Location", false);
        sampleSite.setId(siteId);

        // Setting up mockMvc
        // This is not required if you use @WebMvcTest which automatically configures MockMvc
    }

    @Test
    public void testGetAllSites_ReturnsEmptyList() throws Exception {
        given(siteRepository.findAll()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/sites")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetSiteById_ReturnsSite() throws Exception {
        given(siteRepository.findById(sampleSite.getId())).willReturn(Optional.of(sampleSite));

        mockMvc.perform(get("/sites/" + sampleSite.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(sampleSite.getName()));
    }

    @Test
    public void testAddSite_CreatesNewSite() throws Exception {
        given(siteRepository.save(any(Site.class))).willReturn(sampleSite);

        mockMvc.perform(post("/sites")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Site\", \"geographicalInfo\":\"Test Location\", \"isParalympic\":false}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/sites/" + sampleSite.getId())));
    }

    @Test
    public void testUpdateSite_UpdatesSite() throws Exception {
        given(siteRepository.findById(sampleSite.getId())).willReturn(Optional.of(sampleSite));
        given(siteRepository.save(any(Site.class))).willReturn(sampleSite);

        mockMvc.perform(put("/sites/" + sampleSite.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Name\", \"geographicalInfo\":\"Updated Location\", \"isParalympic\":true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"));
    }

    @Test
    public void testDeleteSite_DeletesSite() throws Exception {
        given(siteRepository.findById(sampleSite.getId())).willReturn(Optional.of(sampleSite));
        doNothing().when(siteRepository).delete(sampleSite);

        mockMvc.perform(delete("/sites/" + sampleSite.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(siteRepository, times(1)).delete(sampleSite);
    }
}
