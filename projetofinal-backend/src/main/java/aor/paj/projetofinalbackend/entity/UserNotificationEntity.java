package aor.paj.projetofinalbackend.entity;

import jakarta.persistence.*;

import java.io.Serializable;

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

    public UserNotificationEntity() {
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public NotificationEntity getNotification() {
        return notification;
    }

    public void setNotification(NotificationEntity notification) {
        this.notification = notification;
    }

    public Boolean isSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

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
