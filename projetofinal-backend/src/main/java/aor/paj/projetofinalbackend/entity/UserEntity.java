package aor.paj.projetofinalbackend.entity;

import aor.paj.projetofinalbackend.utils.Role;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Entity class representing a user in the system.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
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
        @NamedQuery(
                name = "User.findAllUsersWithNonNullPasswordStamps",
                query = "SELECT u FROM UserEntity u WHERE u.passwordRetrieveTime IS NOT NULL AND u.passwordRetrieveTime <= :cutoffTime"
        ),
        @NamedQuery(name = "User.findAdmins", query = "SELECT u FROM UserEntity u WHERE u.role = :role")
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

    @Column(name="visibility", nullable = false)
    private Boolean visibility;

    @Column(name="active_project")
    private Boolean activeProject;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserProjectEntity> userProjects = new HashSet<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectEntity> ownedProjects = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TokenEntity> tokens = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserNotificationEntity> userNotifications = new HashSet<>();

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

    /**
     * Default constructor for UserEntity.
     */
    public UserEntity() {
    }

    /**
     * Constructor with username parameter.
     *
     * @param username The username of the user.
     */
    public UserEntity(String username) {
        this.username = username;
    }

    // Getters and Setters

    /**
     * Get the ID of the user.
     *
     * @return The ID of the user.
     */
    public Long getId() {
        return id;
    }

    /**
     * Get the username of the user.
     *
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username of the user.
     *
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the projects associated with the user.
     *
     * @return The set of user projects associated with the user.
     */
    public Set<UserProjectEntity> getUserProjects() {
        return userProjects;
    }

    /**
     * Set the projects associated with the user.
     *
     * @param userProjects The set of user projects to set.
     */
    public void setUserProjects(Set<UserProjectEntity> userProjects) {
        this.userProjects = userProjects;
    }

    /**
     * Add a user project to the user's list of projects.
     *
     * @param userProject The user project to add.
     */
    public void addUserProject(UserProjectEntity userProject) {
        userProjects.add(userProject);
        userProject.setUser(this);
    }

    /**
     * Get the first name of the user.
     *
     * @return The first name of the user.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set the first name of the user.
     *
     * @param firstName The first name to set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get the last name of the user.
     *
     * @return The last name of the user.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the last name of the user.
     *
     * @param lastName The last name to set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password of the user.
     *
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the email address of the user.
     *
     * @return The email address of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the email address of the user.
     *
     * @param email The email address to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the role of the user.
     *
     * @return The role of the user.
     */
    public Role getRole() {
        return role;
    }

    /**
     * Set the role of the user.
     *
     * @param role The role to set.
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Check if the user account is active.
     *
     * @return True if the user account is active, false otherwise.
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * Set whether the user account is active.
     *
     * @param active True to activate the user account, false to deactivate.
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * Check if the user account is pending.
     *
     * @return True if the user account is pending, false otherwise.
     */
    public Boolean getPending() {
        return pending;
    }

    /**
     * Set whether the user account is pending.
     *
     * @param pending True if the user account is pending, false otherwise.
     */
    public void setPending(Boolean pending) {
        this.pending = pending;
    }

    /**
     * Get the email validation token of the user.
     *
     * @return The email validation token of the user.
     */
    public String getEmailToken() {
        return emailToken;
    }

    /**
     * Set the email validation token of the user.
     *
     * @param emailToken The email validation token to set.
     */
    public void setEmailToken(String emailToken) {
        this.emailToken = emailToken;
    }

    /**
     * Get the registration time of the user.
     *
     * @return The registration time of the user.
     */
    public LocalDateTime getRegistTime() {
        return registTime;
    }

    /**
     * Set the registration time of the user.
     *
     * @param registTime The registration time to set.
     */
    public void setRegistTime(LocalDateTime registTime) {
        this.registTime = registTime;
    }

    /**
     * Get the password retrieval time of the user.
     *
     * @return The password retrieval time of the user.
     */
    public LocalDateTime getPasswordRetrieveTime() {
        return passwordRetrieveTime;
    }

    /**
     * Set the password retrieval time of the user.
     *
     * @param passwordRetrieveTime The password retrieval time to set.
     */
    public void setPasswordRetrieveTime(LocalDateTime passwordRetrieveTime) {
        this.passwordRetrieveTime = passwordRetrieveTime;
    }

    /**
     * Get the biography of the user.
     *
     * @return The biography of the user.
     */
    public String getBiography() {
        return biography;
    }

    /**
     * Set the biography of the user.
     *
     * @param biography The biography to set.
     */
    public void setBiography(String biography) {
        this.biography = biography;
    }

    /**
     * Check if the user's profile is visible.
     *
     * @return True if the user's profile is visible, false otherwise.
     */
    public Boolean getVisibility() {
        return visibility;
    }

    /**
     * Set whether the user's profile is visible.
     *
     * @param visibility True to make the user's profile visible, false to hide it.
     */
    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }

    /**
     * Check if the user has an active project.
     *
     * @return True if the user has an active project, false otherwise.
     */
    public Boolean getActiveProject() {
        return activeProject;
    }

    /**
     * Set whether the user has an active project.
     *
     * @param activeProject True if the user has an active project, false otherwise.
     */
    public void setActiveProject(Boolean activeProject) {
        this.activeProject = activeProject;
    }

    /**
     * Get the set of projects owned by the user.
     *
     * @return The set of projects owned by the user.
     */
    public Set<ProjectEntity> getOwnedProjects() {
        return ownedProjects;
    }

    /**
     * Set the set of projects owned by the user.
     *
     * @param ownedProjects The set of projects to set as owned by the user.
     */
    public void setOwnedProjects(Set<ProjectEntity> ownedProjects) {
        this.ownedProjects = ownedProjects;
    }

    /**
     * Get the set of tokens associated with the user.
     *
     * @return The set of tokens associated with the user.
     */
    public Set<TokenEntity> getTokens() {
        return tokens;
    }

    /**
     * Set the set of tokens associated with the user.
     *
     * @param tokens The set of tokens to set for the user.
     */
    public void setTokens(Set<TokenEntity> tokens) {
        this.tokens = tokens;
    }

    /**
     * Get the set of user notifications for the user.
     *
     * @return The set of user notifications for the user.
     */
    public Set<UserNotificationEntity> getUserNotifications() {
        return userNotifications;
    }

    /**
     * Set the set of user notifications for the user.
     *
     * @param userNotifications The set of user notifications to set for the user.
     */
    public void setUserNotifications(Set<UserNotificationEntity> userNotifications) {
        this.userNotifications = userNotifications;
    }

    /**
     * Get the set of skills associated with the user.
     *
     * @return The set of skills associated with the user.
     */
    public Set<SkillEntity> getSkills() {
        return skills;
    }

    /**
     * Set the set of skills associated with the user.
     *
     * @param skills The set of skills to set for the user.
     */
    public void setSkills(Set<SkillEntity> skills) {
        this.skills = skills;
    }

    /**
     * Get the set of interests associated with the user.
     *
     * @return The set of interests associated with the user.
     */
    public Set<InterestEntity> getInterests() {
        return interests;
    }

    /**
     * Set the set of interests associated with the user.
     *
     * @param interests The set of interests to set for the user.
     */
    public void setInterests(Set<InterestEntity> interests) {
        this.interests = interests;
    }

    /**
     * Get the workplace of the user.
     *
     * @return The workplace of the user.
     */
    public WorkplaceEntity getWorkplace() {
        return workplace;
    }

    /**
     * Set the workplace of the user.
     *
     * @param workplace The workplace to set for the user.
     */
    public void setWorkplace(WorkplaceEntity workplace) {
        this.workplace = workplace;
    }

    /**
     * Get the set of tasks associated with the user.
     *
     * @return The set of tasks associated with the user.
     */
    public Set<TaskEntity> getTasks() {
        return tasks;
    }

    /**
     * Set the set of tasks associated with the user.
     *
     * @param tasks The set of tasks to set for the user.
     */
    public void setTasks(Set<TaskEntity> tasks) {
        this.tasks = tasks;
    }

    /**
     * Get the set of messages sent by the user.
     *
     * @return The set of messages sent by the user.
     */
    public Set<MessageEntity> getSentMessages() {
        return sentMessages;
    }

    /**
     * Set the set of messages sent by the user.
     *
     * @param sentMessages The set of messages to set as sent by the user.
     */
    public void setSentMessages(Set<MessageEntity> sentMessages) {
        this.sentMessages = sentMessages;
    }

    /**
     * Get the set of messages received by the user.
     *
     * @return The set of messages received by the user.
     */
    public Set<MessageEntity> getReceivedMessages() {
        return receivedMessages;
    }

    /**
     * Set the set of messages received by the user.
     *
     * @param receivedMessages The set of messages to set as received by the user.
     */
    public void setReceivedMessages(Set<MessageEntity> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    /**
     * Get the set of project histories associated with the user.
     *
     * @return The set of project histories associated with the user.
     */
    public Set<ProjectHistoryEntity> getProjectHistories() {
        return projectHistories;
    }

    /**
     * Set the set of project histories associated with the user.
     *
     * @param projectHistories The set of project histories to set for the user.
     */
    public void setProjectHistories(Set<ProjectHistoryEntity> projectHistories) {
        this.projectHistories = projectHistories;
    }

    /**
     * Get the set of chat messages sent by the user.
     *
     * @return The set of chat messages sent by the user.
     */
    public Set<ChatMessageEntity> getChatmessages() {
        return chatmessages;
    }

    /**
     * Set the set of chat messages sent by the user.
     *
     * @param chatmessages The set of chat messages to set as sent by the user.
     */
    public void setChatmessages(Set<ChatMessageEntity> chatmessages) {
        this.chatmessages = chatmessages;
    }

    /**
     * Get the set of notifications sent by the user.
     *
     * @return The set of notifications sent by the user.
     */
    public Set<NotificationEntity> getSentNotifications() {
        return sentNotifications;
    }

    /**
     * Set the set of notifications sent by the user.
     *
     * @param sentNotifications The set of notifications to set as sent by the user.
     */
    public void setSentNotifications(Set<NotificationEntity> sentNotifications) {
        this.sentNotifications = sentNotifications;
    }

    /**
     * Get the type of the profile image associated with the user.
     *
     * @return The type of the profile image associated with the user.
     */
    public String getProfileImageType() {
        return profileImageType;
    }

    /**
     * Set the type of the profile image associated with the user.
     *
     * @param profileImageType The type of the profile image to set for the user.
     */
    public void setProfileImageType(String profileImageType) {
        this.profileImageType = profileImageType;
    }

    /**
     * Get the file path of the profile image associated with the user.
     *
     * @return The file path of the profile image associated with the user.
     */
    public String getProfileImagePath() {
        return profileImagePath;
    }

    /**
     * Set the file path of the profile image associated with the user.
     *
     * @param profileImagePath The file path of the profile image to set for the user.
     */
    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }

    /**
     * Check if the user is currently online.
     *
     * @return True if the user is online, false otherwise.
     */
    public Boolean getOnline() {
        return online;
    }

    /**
     * Set whether the user is online.
     *
     * @param online True to set the user as online, false otherwise.
     */
    public void setOnline(Boolean online) {
        this.online = online;
    }

    /**
     * Set the unique identifier of the user.
     *
     * @param id The unique identifier to set for the user.
     */
    public void setId(Long id) {
        this.id = id;
    }
}
