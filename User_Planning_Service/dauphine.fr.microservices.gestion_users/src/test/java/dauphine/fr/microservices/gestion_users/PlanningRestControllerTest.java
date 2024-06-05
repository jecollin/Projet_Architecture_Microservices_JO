package dauphine.fr.microservices.gestion_users;

import dauphine.fr.microservices.gestion_users.controllers.PlanningRestController;
import dauphine.fr.microservices.gestion_users.entities.Planning;
import dauphine.fr.microservices.gestion_users.repositories.PlanningRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class PlanningRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PlanningRepository planningRepository;

    @InjectMocks
    private PlanningRestController planningRestController;

    private Planning planning1;
    private Planning planning2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this); // Utilisation de initMocks
        this.mockMvc = MockMvcBuilders.standaloneSetup(planningRestController).build();
        planning1 = new Planning(1, 101, "2024-07-26T10:00:00");
        planning2 = new Planning(2, 102, "2024-07-27T15:00:00");
    }

    @Test
    void testGetAllPlannings() throws Exception {
        List<Planning> plannings = Arrays.asList(planning1, planning2);
        when(planningRepository.findByUserId(1)).thenReturn(plannings);

        mockMvc.perform(get("/plannings/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andDo(print());

        verify(planningRepository, times(1)).findByUserId(1);
    }

    @Test
    void testGetPlanningById() throws Exception {
        when(planningRepository.findById(1)).thenReturn(Optional.of(planning1));

        mockMvc.perform(get("/plannings/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1))
                .andDo(print());

        verify(planningRepository, times(1)).findById(1);
    }

    @Test
    void testCreatePlanning() throws Exception {
        when(planningRepository.save(any(Planning.class))).thenReturn(planning1);

        mockMvc.perform(post("/plannings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1,\"eventId\":101,\"scheduledTime\":\"2024-07-26T10:00:00\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(1))
                .andDo(print());

        verify(planningRepository, times(1)).save(any(Planning.class));
    }

    @Test
    void testUpdatePlanning() throws Exception {
        when(planningRepository.findById(1)).thenReturn(Optional.of(planning1));
        when(planningRepository.save(any(Planning.class))).thenReturn(planning1);

        mockMvc.perform(put("/plannings/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1,\"eventId\":103,\"scheduledTime\":\"2024-07-28T12:00:00\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventId").value(103))
                .andDo(print());

        verify(planningRepository, times(1)).findById(1);
        verify(planningRepository, times(1)).save(any(Planning.class));
    }

    @Test
    void testDeletePlanning() throws Exception {
        when(planningRepository.findById(1)).thenReturn(Optional.of(planning1));

        mockMvc.perform(delete("/plannings/1"))
                .andExpect(status().isOk())
                .andDo(print());

        verify(planningRepository, times(1)).findById(1);
        verify(planningRepository, times(1)).deleteById(1);
    }
}
