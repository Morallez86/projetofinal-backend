package aor.paj.projetofinalbackend.entity;

import jakarta.persistence.*;

import java.io.Serializable;

/**
 * Entity class representing a user notification entity stored in the database.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Entity
@Table(name = "user_notification")
@NamedQueries({
        @NamedQuery(name = "UserNotificationEntity.findUserNotificationById", query = "SELECT un FROM UserNotificationEntity un WHERE un.id = :id"),
        @NamedQuery(name = "UserNotificationEntity.findByUserAndNotification",
                query = "SELECT un FROM UserNotificationEntity un WHERE un.user.id = :userId AND un.notification.id = :notificationId")
})
public class UserNotificationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id")
    private NotificationEntity notification;

    @Column(name = "seen", nullable = false)
    private Boolean seen;

    /**
     * Default constructor for UserNotificationEntity.
     */
    public UserNotificationEntity() {
    }

    /**
     * Get the unique identifier of the user notification.
     *
     * @return The unique identifier of the user notification.
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the unique identifier of the user notification.
     *
     * @param id The unique identifier to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the user associated with the notification.
     *
     * @return The user associated with the notification.
     */
    public UserEntity getUser() {
        return user;
    }

    /**
     * Set the user associated with the notification.
     *
     * @param user The user to set for the notification.
     */
    public void setUser(UserEntity user) {
        this.user = user;
    }

    /**
     * Get the notification associated with the user notification.
     *
     * @return The notification associated with the user notification.
     */
    public NotificationEntity getNotification() {
        return notification;
    }

    /**
     * Set the notification associated with the user notification.
     *
     * @param notification The notification to set for the user notification.
     */
    public void setNotification(NotificationEntity notification) {
        this.notification = notification;
    }

    /**
     * Check if the notification has been seen by the user.
     *
     * @return True if the notification has been seen, false otherwise.
     */
    public Boolean isSeen() {
        return seen;
    }

    /**
     * Set whether the notification has been seen by the user.
     *
     * @param seen True to mark the notification as seen, false otherwise.
     */
    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    /**
     * Generates a string representation of the UserNotificationEntity object.
     *
     * @return String representation of the UserNotificationEntity object.
     */
    @Override
    public String toString() {
        return "UserNotificationEntity{" +
                "id=" + id +
                ", user=" + user.getId() +
                ", notification=" + notification.getId() +
                ", seen=" + seen +
                '}';
    }
}
