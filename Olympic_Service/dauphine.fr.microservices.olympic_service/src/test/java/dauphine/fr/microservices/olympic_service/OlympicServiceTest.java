package dauphine.fr.microservices.olympic_service;

import dauphine.fr.microservices.gestion_event.entities.Event;
import dauphine.fr.microservices.gestion_sites.entities.Site;
import dauphine.fr.microservices.gestion_sports.entities.Sport;
import dauphine.fr.microservices.gestion_users.entities.Planning;
import dauphine.fr.microservices.olympic_service.controllers.OlympicRestController;
import dauphine.fr.microservices.olympic_service.services.OlympicService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OlympicServiceTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OlympicService olympicService;

	private Event testEvent;
	private Site testSite;
	private Sport testSport;
	private Planning testPlanning;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);

		// Initialize test objects
		testEvent = new Event();
		testEvent.setId(UUID.randomUUID());

		testSite = new Site("Olympic Stadium", "Somewhere in the city", true);
		testSite.setId(UUID.randomUUID());

		testSport = new Sport();
		testSport.setId(UUID.randomUUID());

		testPlanning = new Planning();
		testPlanning.setId(1);
	}

	@Test
	public void getAllEvents_ShouldReturnAllEvents() throws Exception {
		given(olympicService.getAllEvents()).willReturn(Arrays.asList(testEvent));

		mockMvc.perform(get("/olympic/events")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(testEvent.getId().toString()));
	}

	@Test
	public void getAllSites_ShouldReturnAllSites() throws Exception {
		given(olympicService.getAllSites()).willReturn(Arrays.asList(testSite));

		mockMvc.perform(get("/olympic/sites")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(testSite.getId().toString()))
				.andExpect(jsonPath("$[0].name").value(testSite.getName()));
	}

	@Test
	public void getAllSports_ShouldReturnAllSports() throws Exception {
		given(olympicService.getAllSports()).willReturn(Arrays.asList(testSport));

		mockMvc.perform(get("/olympic/sports")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(testSport.getId().toString()));
	}

	@Test
	public void getUserPlannings_ShouldReturnUserPlannings() throws Exception {
		given(olympicService.getUserPlannings(1)).willReturn(Arrays.asList(testPlanning));

		mockMvc.perform(get("/olympic/plannings/user/1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(testPlanning.getId()));
	}

	@Test
	public void getSportsByDateAndSite_ShouldReturnSports() throws Exception {
		LocalDate date = LocalDate.now();
		UUID siteId = UUID.randomUUID();

		given(olympicService.getSportsByDateAndSite(date, siteId)).willReturn(Arrays.asList(testSport));

		mockMvc.perform(get("/olympic/sports/date/{date}/site/{siteId}", date, siteId)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(testSport.getId().toString()));
	}

	@Test
	public void getSitesBySportAndDate_ShouldReturnSites() throws Exception {
		LocalDate date = LocalDate.now();
		UUID sportId = UUID.randomUUID();
		given(olympicService.getSitesBySportAndDate(sportId, date)).willReturn(Arrays.asList(testSite));

		mockMvc.perform(get("/olympic/sites/sport/{sportId}/date/{date}", sportId, date)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(testSite.getId().toString()));
	}
}