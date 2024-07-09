package aor.paj.projetofinalbackend.entity;

import aor.paj.projetofinalbackend.utils.NotificationManagingActions;
import aor.paj.projetofinalbackend.utils.NotificationType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * NotificationEntity represents a notification in the application.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Entity
@Table(name = "notification")
@NamedQueries({
        @NamedQuery(name = "NotificationEntity.findNotificationById", query = "SELECT n FROM NotificationEntity n WHERE n.id = :id"),
        @NamedQuery(
                name = "Notification.findByUserIdAndTypeAndSeen",
                query = "SELECT n FROM NotificationEntity n " +
                        "JOIN UserNotificationEntity un ON un.notification = n " +
                        "WHERE un.user.id = :userId " +
                        "AND (:type IS NULL OR n.type = :type) " +
                        "AND (:seen IS NULL OR un.seen = :seen) ORDER BY n.timestamp DESC"
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

    /**
     * The unique identifier for the notification.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The description of the notification.
     */
    @Column(name = "description", nullable = false, updatable = false)
    private String description;

    /**
     * Indicates whether the notification requires approval.
     */
    @Column(name = "approval")
    private Boolean approval;

    /**
     * The type of the notification.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private NotificationType type;

    /**
     * The timestamp when the notification was created.
     */
    @Column(name = "timestamp", nullable = false, updatable = false)
    private LocalDateTime timestamp;

    /**
     * The user who sent the notification.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private UserEntity sender;

    /**
     * The project associated with the notification.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    /**
     * The user notifications associated with this notification.
     */
    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserNotificationEntity> userNotifications = new HashSet<>();

    /**
     * The action associated with the notification.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "action")
    private NotificationManagingActions action;

    /**
     * Default constructor for NotificationEntity.
     */
    public NotificationEntity() {
    }

    /**
     * Retrieves the notification ID.
     *
     * @return the notification ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Retrieves the description of the notification.
     *
     * @return the description of the notification.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the notification.
     *
     * @param description the new description of the notification.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the type of the notification.
     *
     * @return the type of the notification.
     */
    public NotificationType getType() {
        return type;
    }

    /**
     * Sets the type of the notification.
     *
     * @param type the new type of the notification.
     */
    public void setType(NotificationType type) {
        this.type = type;
    }

    /**
     * Retrieves the timestamp when the notification was created.
     *
     * @return the timestamp when the notification was created.
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp when the notification was created.
     *
     * @param timestamp the new timestamp when the notification was created.
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Retrieves the project associated with the notification.
     *
     * @return the project associated with the notification.
     */
    public ProjectEntity getProject() {
        return project;
    }

    /**
     * Sets the project associated with the notification.
     *
     * @param project the new project associated with the notification.
     */
    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    /**
     * Retrieves the user who sent the notification.
     *
     * @return the user who sent the notification.
     */
    public UserEntity getSender() {
        return sender;
    }

    /**
     * Sets the user who sent the notification.
     *
     * @param sender the new user who sent the notification.
     */
    public void setSender(UserEntity sender) {
        this.sender = sender;
    }

    /**
     * Retrieves the user notifications associated with this notification.
     *
     * @return the user notifications associated with this notification.
     */
    public Set<UserNotificationEntity> getUserNotifications() {
        return userNotifications;
    }

    /**
     * Sets the user notifications associated with this notification.
     *
     * @param userNotifications the new user notifications associated with this notification.
     */
    public void setUserNotifications(Set<UserNotificationEntity> userNotifications) {
        this.userNotifications = userNotifications;
    }

    /**
     * Retrieves whether the notification requires approval.
     *
     * @return true if the notification requires approval, false otherwise.
     */
    public Boolean isApproval() {
        return approval;
    }

    /**
     * Sets whether the notification requires approval.
     *
     * @param approval true if the notification requires approval, false otherwise.
     */
    public void setApproval(Boolean approval) {
        this.approval = approval;
    }

    /**
     * Retrieves the action associated with the notification.
     *
     * @return the action associated with the notification.
     */
    public NotificationManagingActions getAction() {
        return action;
    }

    /**
     * Sets the action associated with the notification.
     *
     * @param action the new action associated with the notification.
     */
    public void setAction(NotificationManagingActions action) {
        this.action = action;
    }

    /**
     * Sets the notification ID.
     *
     * @param id the new notification ID.
     */
    public void setId(Long id) {
        this.id = id;
    }
}
