package aor.paj.projetofinalbackend.entity;

import aor.paj.projetofinalbackend.utils.NotificationType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "notification")
@NamedQueries({
        @NamedQuery(
                name = "Notification.findByUserIdAndTypeAndSeen",
                query = "SELECT n FROM NotificationEntity n " +
                        "JOIN UserNotificationEntity un ON un.notification = n " +
                        "WHERE un.user.id = :userId " +
                        "AND (:type IS NULL OR n.type = :type) " +
                        "AND (:seen IS NULL OR un.seen = :seen)"
        ),
        @NamedQuery(
                name = "Notification.countByUserIdAndTypeAndSeen",
                query = "SELECT COUNT(n) FROM NotificationEntity n " +
                        "JOIN UserNotificationEntity un ON un.notification = n " +
                        "WHERE un.user.id = :userId " +
                        "AND (:type IS NULL OR n.type = :type) " +
                        "AND (:seen IS NULL OR un.seen = :seen)"
        ),
        @NamedQuery(
                name = "Notification.updateSeenStatusByUserIdAndIds",
                query = "UPDATE UserNotificationEntity un " +
                        "SET un.seen = :seen " +
                        "WHERE un.notification.id IN :ids " +
                        "AND un.user.id = :userId"
        ),
})
public class NotificationEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", nullable = false, updatable = false)
    private String description;

    @Column(name = "approval")
    private Boolean approval;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private NotificationType type;

    @Column(name = "timestamp", nullable = false, updatable = false)
    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private UserEntity sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserNotificationEntity> userNotifications = new HashSet<>();

    public NotificationEntity() {
    }

    // Getters and Setters
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

    public UserEntity getSender() {
        return sender;
    }

    public void setSender(UserEntity sender) {
        this.sender = sender;
    }

    public Set<UserNotificationEntity> getUserNotifications() {
        return userNotifications;
    }

    public void setUserNotifications(Set<UserNotificationEntity> userNotifications) {
        this.userNotifications = userNotifications;
    }

    public Boolean isApproval() {
        return approval;
    }

    public void setApproval(Boolean approval) {
        this.approval = approval;
    }
}
