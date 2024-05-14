package aor.paj.projetofinalbackend.entity;

import aor.paj.projetofinalbackend.utils.NotificationType;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="notification")
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

    @Column(name = "time", nullable = false, unique = false, updatable = false)
    private Instant time;

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

    public NotificationEntity() {
    }

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

    public Set<UserEntity> getUsers() {
        return users;
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

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }

    public void addUser(UserEntity user) {
        this.users.add(user);
        user.getNotifications().add(this);
    }

    public void removeUser(UserEntity user) {
        this.users.remove(user);
        user.getNotifications().remove(this);
    }

    public UserEntity getSender() {
        return sender;
    }
    public void setSender(UserEntity sender) {
        this.sender = sender;
    }
}
