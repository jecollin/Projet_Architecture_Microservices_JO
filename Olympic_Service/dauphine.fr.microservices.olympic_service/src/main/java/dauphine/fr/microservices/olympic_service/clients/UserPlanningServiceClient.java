package dauphine.fr.microservices.olympic_service.clients;

import dauphine.fr.microservices.gestion_users.entities.Planning;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "user-planning-service", url = "${user-planning-service.url}")
public interface UserPlanningServiceClient {
    @GetMapping("/plannings/user/{userId}")
    List<Planning> getAllPlannings(@PathVariable("userId") Integer userId);

    @GetMapping("/plannings/{id}")
    Planning getPlanningById(@PathVariable("id") Integer id);
}
