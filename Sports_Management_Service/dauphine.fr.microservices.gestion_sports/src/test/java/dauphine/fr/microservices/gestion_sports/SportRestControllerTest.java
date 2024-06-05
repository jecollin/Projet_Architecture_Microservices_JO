package dauphine.fr.microservices.gestion_sports;

import dauphine.fr.microservices.gestion_sports.entities.Sport;
import dauphine.fr.microservices.gestion_sports.repositories.SportRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SportRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SportRepository sportRepository;

	private Sport testSport;

	@Before
	public void setup() {
		// Initialize the test sport object
		UUID sportId = UUID.randomUUID();
		testSport = new Sport(sportId, "Football");

		// Mock the repository methods
		given(sportRepository.findAll()).willReturn(Arrays.asList(testSport));
		given(sportRepository.findById(sportId)).willReturn(Optional.of(testSport));
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void getAllSports_ShouldReturnAllSports() throws Exception {
		mockMvc.perform(get("/sports")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(testSport.getId().toString()))
				.andExpect(jsonPath("$[0].name").value("Football"));
	}

	@Test
	public void getSportById_ShouldReturnSport() throws Exception {
		mockMvc.perform(get("/sports/{id}", testSport.getId())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(testSport.getId().toString()))
				.andExpect(jsonPath("$.name").value("Football"));
	}

	@Test
	public void getSportById_NotFound() throws Exception {
		// Given an ID that does not exist in the repository
		UUID randomId = UUID.randomUUID();
		given(sportRepository.findById(randomId)).willReturn(Optional.empty());

		mockMvc.perform(get("/sports/{id}", randomId)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void createSport_ShouldReturnCreated() throws Exception {
		// Mock the behavior of save to return the sport
		given(sportRepository.save(testSport)).willReturn(testSport);

		mockMvc.perform(post("/sports")
						.param("name", "Football")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(header().string("Location", "http://localhost/sports/" + testSport.getId()));
	}

	@Test
	public void deleteSportById_ShouldReturnOk() throws Exception {
		mockMvc.perform(delete("/sports/{id}", testSport.getId())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
}
