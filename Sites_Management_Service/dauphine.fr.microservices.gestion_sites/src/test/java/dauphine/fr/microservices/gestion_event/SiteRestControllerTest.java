package dauphine.fr.microservices.gestion_event;

import dauphine.fr.microservices.gestion_sites.entities.Site;
import dauphine.fr.microservices.gestion_sites.repositories.SiteRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SiteRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SiteRepository siteRepository;

    private Site testSite;

    @Before
    public void setup() {
        // Initialize the test site object
        testSite = new Site("Olympic Stadium", "Somewhere in the city", true);
        testSite.setId(UUID.randomUUID());

        // Mock the repository methods
        given(siteRepository.findAll()).willReturn(Arrays.asList(testSite));
        given(siteRepository.findById(testSite.getId())).willReturn(Optional.of(testSite));
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void getAllSites_ShouldReturnAllSites() throws Exception {
        mockMvc.perform(get("/sites")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(testSite.getId().toString()))
                .andExpect(jsonPath("$[0].name").value(testSite.getName()))
                .andExpect(jsonPath("$[0].geographicalInfo").value(testSite.getGeographicalInfo()))
                .andExpect(jsonPath("$[0].isParalympic").value(testSite.getIsParalympic()));
    }

    @Test
    public void getSiteById_ShouldReturnSite() throws Exception {
        mockMvc.perform(get("/sites/{id}", testSite.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testSite.getId().toString()))
                .andExpect(jsonPath("$.name").value(testSite.getName()))
                .andExpect(jsonPath("$.geographicalInfo").value(testSite.getGeographicalInfo()))
                .andExpect(jsonPath("$.isParalympic").value(testSite.getIsParalympic()));
    }

    @Test
    public void getSiteById_NotFound() throws Exception {
        UUID randomId = UUID.randomUUID();
        given(siteRepository.findById(randomId)).willReturn(Optional.empty());

        mockMvc.perform(get("/sites/{id}", randomId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Site with ID " + randomId + " not found"));
    }

    @Test
    public void createSite_ShouldReturnCreated() throws Exception {
        // Capture the argument passed to save method
        ArgumentCaptor<Site> siteArgumentCaptor = ArgumentCaptor.forClass(Site.class);

        // Mock the save method to return a Site object with an ID
        Site savedSite = new Site("Basketball Arena", "Another place", false);
        savedSite.setId(UUID.randomUUID());
        given(siteRepository.save(siteArgumentCaptor.capture())).willReturn(savedSite);

        mockMvc.perform(post("/sites")
                        .content("{\"name\": \"Basketball Arena\", \"geographicalInfo\": \"Another place\", \"isParalympic\": false}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/sites/" + savedSite.getId().toString()));

        // Verify the save method was called
        verify(siteRepository).save(siteArgumentCaptor.capture());

        // Get the captured argument
        Site capturedSite = siteArgumentCaptor.getValue();

        // Verify the captured site object
        assertThat(capturedSite.getName()).isEqualTo("Basketball Arena");
        assertThat(capturedSite.getGeographicalInfo()).isEqualTo("Another place");
        assertThat(capturedSite.getIsParalympic()).isEqualTo(false);
    }

    @Test
    public void updateSiteById_ShouldReturnUpdatedSite() throws Exception {
        Site updatedSite = new Site("New Name", "New Info", false);
        updatedSite.setId(testSite.getId());
        given(siteRepository.findById(testSite.getId())).willReturn(Optional.of(testSite));
        given(siteRepository.save(updatedSite)).willReturn(updatedSite);

        mockMvc.perform(put("/sites/{id}", testSite.getId())
                        .content("{\"name\": \"New Name\", \"geographicalInfo\": \"New Info\", \"isParalympic\": false}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Name"))
                .andExpect(jsonPath("$.geographicalInfo").value("New Info"))
                .andExpect(jsonPath("$.isParalympic").value(false));

        verify(siteRepository).save(updatedSite);
    }

    @Test
    public void deleteSiteById_ShouldReturnOk() throws Exception {
        given(siteRepository.findById(testSite.getId())).willReturn(Optional.of(testSite));

        mockMvc.perform(delete("/sites/{id}", testSite.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Site with ID " + testSite.getId() + " deleted successfully"));

        verify(siteRepository).deleteById(testSite.getId());
    }

    @Test
    public void deleteSiteById_NotFound() throws Exception {
        UUID randomId = UUID.randomUUID();
        given(siteRepository.findById(randomId)).willReturn(Optional.empty());

        mockMvc.perform(delete("/sites/{id}", randomId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Site with ID " + randomId + " not found"));
    }
}
