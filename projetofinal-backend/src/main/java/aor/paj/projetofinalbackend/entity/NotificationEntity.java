package aor.paj.projetofinalbackend.entity;

import aor.paj.projetofinalbackend.utils.NotificationType;
import aor.paj.projetofinalbackend.utils.SkillType;
import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="notification")
public class NotificationEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", nullable = false, unique = false, updatable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private NotificationType type;

    @Column(name = "seen", nullable = false, unique = false, updatable = true)
    private boolean seen;

    @Column(name = "time", nullable = false, unique = false, updatable = false)
    private Instant time;

    @Column(name = "owner_id", nullable = false, unique = false, updatable = false)
    private long owner_id;

    @ManyToOne
    @JoinColumn (name = "user")
    private UserEntity user;

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public NotificationType getType() {
        return type;
    }

    public boolean isSeen() {
        return seen;
    }

    public Instant getTime() {
        return time;
    }

    public long getOwner_id() {
        return owner_id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public void setOwner_id(long owner_id) {
        this.owner_id = owner_id;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
