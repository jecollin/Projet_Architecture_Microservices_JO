package dauphine.fr.microservices.gestion_sites.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sites")
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "geographical_info", nullable = false, length = 255)
    private String geographicalInfo;

    @Column(name = "is_paralympic", nullable = false)
    private Boolean isParalympic;

    public Site(String name, String geographicalInfo, Boolean isParalympic) {
        this.name = name;
        this.geographicalInfo = geographicalInfo;
        this.isParalympic = isParalympic;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGeographicalInfo() {
        return geographicalInfo;
    }

    public void setGeographicalInfo(String geographicalInfo) {
        this.geographicalInfo = geographicalInfo;
    }

    public Boolean getIsParalympic() {
        return isParalympic;
    }

    public void setIsParalympic(Boolean isParalympic) {
        this.isParalympic = isParalympic;
    }
}
