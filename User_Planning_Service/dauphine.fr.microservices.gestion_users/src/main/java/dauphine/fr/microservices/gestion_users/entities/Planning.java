package dauphine.fr.microservices.gestion_users.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "plannings")
public class Planning {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @NotNull(message = "User ID is mandatory")
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @NotNull(message = "Event ID is mandatory")
    @Column(name = "event_id", nullable = false)
    private Integer eventId;

    @NotNull(message = "Scheduled time is mandatory")
    @Column(name = "scheduled_time", nullable = false)
    private String scheduledTime;

    public Planning() {
    }

    public Planning(Integer userId, Integer eventId, String scheduledTime) {
        this.userId = userId;
        this.eventId = eventId;
        this.scheduledTime = scheduledTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }
}
