package aor.paj.projetofinalbackend.entity;

import aor.paj.projetofinalbackend.utils.NotificationType;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "notification")
@NamedQueries({
        @NamedQuery(
                name = "Notification.findByUserIdAndTypeAndSeen",
                query = "SELECT n FROM NotificationEntity n " +
                        "JOIN n.users u " +
                        "WHERE u.id = :userId " +
                        "AND (:type IS NULL OR n.type = :type) " +
                        "AND (:seen IS NULL OR n.seen = :seen)"
        ),
        @NamedQuery(
                name = "Notification.countByUserIdAndTypeAndSeen",
                query = "SELECT COUNT(n) FROM NotificationEntity n " +
                        "JOIN n.users u " +
                        "WHERE u.id = :userId " +
                        "AND (:type IS NULL OR n.type = :type) " +
                        "AND (:seen IS NULL OR n.seen = :seen)"
        ),
        @NamedQuery(
                name = "Notification.updateSeenStatusByUserIdAndIds",
                query = "UPDATE NotificationEntity n " +
                        "SET n.seen = :seen " +
                        "WHERE n.id IN :ids " +
                        "AND EXISTS (SELECT 1 FROM n.users u WHERE u.id = :userId)"
        ),
})
public class NotificationEntity implements Serializable {
    private static final long serialVersionUID = 1L;

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

    @Column(name = "timestamp", nullable = false, unique = false, updatable = false)
    private LocalDateTime timestamp;

    @ManyToMany
    @JoinTable(
            name = "user_notification",
            joinColumns = @JoinColumn(name = "notification_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserEntity> users = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private UserEntity sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    public NotificationEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }

    public UserEntity getSender() {
        return sender;
    }

    public void setSender(UserEntity sender) {
        this.sender = sender;
    }

}
