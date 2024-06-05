package dauphine.fr.microservices.gestion_sports.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Table(name = "sports")
public class Sport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    public Sport() {
    }

    public Sport(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}
