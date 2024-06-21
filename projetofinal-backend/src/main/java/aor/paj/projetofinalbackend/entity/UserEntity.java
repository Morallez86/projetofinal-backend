package aor.paj.projetofinalbackend.entity;

import aor.paj.projetofinalbackend.utils.Role;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name="user")
@NamedQueries({
        @NamedQuery(name = "User.findUserByEmail", query = "SELECT u FROM UserEntity u WHERE u.email = :email"),
        @NamedQuery(name = "User.findAllUsers", query = "SELECT u FROM UserEntity u"),
        @NamedQuery(name = "User.findUserById", query = "SELECT u FROM UserEntity u WHERE u.id = :userId"),
        @NamedQuery(name = "User.findUserByUsername", query = "SELECT u FROM UserEntity u WHERE u.username = :username"),
        @NamedQuery(name = "User.countTotalUsers", query = "SELECT COUNT(u) FROM UserEntity u"),
        @NamedQuery(name = "User.findUserByEmailValidationToken", query = "SELECT u FROM UserEntity u WHERE u.emailToken = :emailToken"),
        @NamedQuery(name = "User.getTotalProjectCount", query = "SELECT COUNT(p) FROM UserEntity u JOIN u.ownedProjects p WHERE u.id = :userId"),
        @NamedQuery(name = "User.findUsersByQuery", query = "SELECT u FROM UserEntity u WHERE LOWER(u.username) LIKE :query OR LOWER(u.email) LIKE :query"),
        @NamedQuery(name = "User.searchUsers", query = "SELECT DISTINCT u FROM UserEntity u " +
                "LEFT JOIN u.skills s " +
                "LEFT JOIN u.interests i " +
                "LEFT JOIN u.workplace w " +
                "WHERE (:searchTerm IS NULL OR LOWER(u.username) LIKE :searchTerm OR LOWER(u.email) LIKE :searchTerm) " +
                "AND (:workplace IS NULL OR w.name = :workplace) " +
                "AND (:skills IS NULL OR s.name IN :skills) " +
                "AND (:interests IS NULL OR i.name IN :interests)"),
})
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name="email", nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name="active", nullable = false)
    private Boolean active;

    @Column(name="pending", nullable = false)
    private Boolean pending;

    @Column(name="email_token")
    private String emailToken;

    @Column(name="regist_time")
    private LocalDateTime registTime;

    @Column(name = "password_stamp")
    private LocalDateTime passwordRetrieveTime;

    @Column(name="biography")
    private String biography;

    @Column(name="visibility", nullable = false, unique = false, updatable = true)
    private Boolean visibility;

    @Column(name="active_project", nullable = true, unique = false, updatable = true)
    private Boolean activeProject;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserProjectEntity> userProjects = new HashSet<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectEntity> ownedProjects = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TokenEntity> tokens = new HashSet<>();

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private Set<NotificationEntity> notifications = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_skill",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private Set<SkillEntity> skills = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_interest",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "interest_id"))
    private Set<InterestEntity> interests = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workplace_id")
    private WorkplaceEntity workplace;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TaskEntity> tasks = new HashSet<>();

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MessageEntity> sentMessages = new HashSet<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MessageEntity> receivedMessages = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectHistoryEntity> projectHistories = new HashSet<>();

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChatMessageEntity> chatmessages = new HashSet<>();

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<NotificationEntity> sentNotifications = new HashSet<>();

    @Column(name="profile_image_type")
    private String profileImageType;

    @Column(name="profile_image_path")
    private String profileImagePath;

    @Column(name = "online")
    private Boolean online = false;

    public UserEntity() {
    }

    public UserEntity(String username) {
        this.username = username;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<UserProjectEntity> getUserProjects() {
        return userProjects;
    }

    public void setUserProjects(Set<UserProjectEntity> userProjects) {
        this.userProjects = userProjects;
    }

    public void addUserProject(UserProjectEntity userProject) {
        userProjects.add(userProject);
        userProject.setUser(this);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getPending() {
        return pending;
    }

    public void setPending(Boolean pending) {
        this.pending = pending;
    }

    public String getEmailToken() {
        return emailToken;
    }

    public void setEmailToken(String emailToken) {
        this.emailToken = emailToken;
    }

    public LocalDateTime getRegistTime() {
        return registTime;
    }

    public void setRegistTime(LocalDateTime registTime) {
        this.registTime = registTime;
    }

    public LocalDateTime getPasswordRetrieveTime() {
        return passwordRetrieveTime;
    }

    public void setPasswordRetrieveTime(LocalDateTime passwordRetrieveTime) {
        this.passwordRetrieveTime = passwordRetrieveTime;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }

    public Boolean getActiveProject() {
        return activeProject;
    }

    public void setActiveProject(Boolean activeProject) {
        this.activeProject = activeProject;
    }

    public Set<ProjectEntity> getOwnedProjects() {
        return ownedProjects;
    }

    public void setOwnedProjects(Set<ProjectEntity> ownedProjects) {
        this.ownedProjects = ownedProjects;
    }

    public Set<TokenEntity> getTokens() {
        return tokens;
    }

    public void setTokens(Set<TokenEntity> tokens) {
        this.tokens = tokens;
    }

    public Set<NotificationEntity> getNotifications() {
        return notifications;
    }

    public void setNotifications(Set<NotificationEntity> notifications) {
        this.notifications = notifications;
    }

    public Set<SkillEntity> getSkills() {
        return skills;
    }

    public void setSkills(Set<SkillEntity> skills) {
        this.skills = skills;
    }

    public Set<InterestEntity> getInterests() {
        return interests;
    }

    public void setInterests(Set<InterestEntity> interests) {
        this.interests = interests;
    }

    public WorkplaceEntity getWorkplace() {
        return workplace;
    }

    public void setWorkplace(WorkplaceEntity workplace) {
        this.workplace = workplace;
    }

    public Set<TaskEntity> getTasks() {
        return tasks;
    }

    public void setTasks(Set<TaskEntity> tasks) {
        this.tasks = tasks;
    }

    public Set<MessageEntity> getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(Set<MessageEntity> sentMessages) {
        this.sentMessages = sentMessages;
    }

    public Set<MessageEntity> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(Set<MessageEntity> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    public Set<ProjectHistoryEntity> getProjectHistories() {
        return projectHistories;
    }

    public void setProjectHistories(Set<ProjectHistoryEntity> projectHistories) {
        this.projectHistories = projectHistories;
    }

    public Set<ChatMessageEntity> getChatmessages() {
        return chatmessages;
    }

    public void setChatmessages(Set<ChatMessageEntity> chatmessages) {
        this.chatmessages = chatmessages;
    }

    public Set<NotificationEntity> getSentNotifications() {
        return sentNotifications;
    }

    public void setSentNotifications(Set<NotificationEntity> sentNotifications) {
        this.sentNotifications = sentNotifications;
    }

    public String getProfileImageType() {
        return profileImageType;
    }

    public void setProfileImageType(String profileImageType) {
        this.profileImageType = profileImageType;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public void setId(Long id) {
        this.id = id;
    }


}