package aor.paj.projetofinalbackend.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name="user")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, unique = true)
    private String firstName;

    @Column(name = "last_name", nullable = false, unique = true)
    private String lastName;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false, unique = true)
    private String password;

    @Column(name="email", nullable = false, unique = true, updatable = true)
    private String email;

    /*@Column(name="photoURL", nullable = false, unique = false, updatable = true)
    private String photoURL;*/

    @Column(name="role", nullable = false)
    private String role;

    @Column(name="active", nullable = false, unique = false, updatable = true)
    private Boolean active;

    @Column(name="pending", nullable = false, unique = false, updatable = true)
    private Boolean pending;

    @Column(name="email_token")
    private String emailToken;

    @Column(name="regist_time")
    private LocalDateTime registTime;

    @Column(name = "password_stamp")
    private LocalDateTime passwordRetrieveTime;

    @Column(name="biography", nullable = false, unique = false, updatable = true)
    private Boolean biography;

    @Column(name="visibility", nullable = false, unique = false, updatable = true)
    private Boolean visibility;

    @Column(name="active_project", nullable = false, unique = false, updatable = true)
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
    @JoinColumn(name = "workplace_id", nullable = false)
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

    public UserEntity() {
        // Default constructor
    }

    public UserEntity(String username) {
        this.username = username;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
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

    public Boolean getBiography() {
        return biography;
    }

    public void setBiography(Boolean biography) {
        this.biography = biography;
    }

    public Boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }

    public Set<ProjectEntity> getOwnedProjects() {
        return ownedProjects;
    }

    public void setOwnedProjects(Set<ProjectEntity> ownedProjects) {
        this.ownedProjects = ownedProjects;
    }

    public Boolean getActiveProject() {
        return activeProject;
    }

    public void setActiveProject(Boolean activeProject) {
        this.activeProject = activeProject;
    }

    public void removeUserProject(UserProjectEntity userProject) {
        userProjects.remove(userProject);
        userProject.setUser(null);
    }

    public Set<SkillEntity> getSkills() {
        return skills;
    }

    public void setSkills(Set<SkillEntity> skills) {
        this.skills = skills;
    }

    public void addSkill(SkillEntity skill) {
        this.skills.add(skill);
        skill.getUsers().add(this);
    }

    public void removeSkill(SkillEntity skill) {
        this.skills.remove(skill);
        skill.getUsers().remove(this);
    }

    public void setTokens(Set<TokenEntity> tokens) {
        this.tokens = tokens;
    }

    public void addToken(TokenEntity token) {
        tokens.add(token);
        token.setUser(this);
    }

    public void removeToken(TokenEntity token) {
        tokens.remove(token);
        token.setUser(null);
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

    public void setTasks(Set<TaskEntity> tasks) {
        this.tasks = tasks;
    }

    public void addTask(TaskEntity task) {
        tasks.add(task);
        task.setUser(this);
    }

    public void removeTask(TaskEntity task) {
        tasks.remove(task);
        task.setUser(null);
    }

    public Set<NotificationEntity> getNotifications() {
        return notifications;
    }

    public void setNotifications(Set<NotificationEntity> notifications) {
        this.notifications = notifications;
    }

    public void addNotification(NotificationEntity notification) {
        notifications.add(notification);
        notification.getUsers().add(this);
    }

    public void removeNotification(NotificationEntity notification) {
        notifications.remove(notification);
        notification.getUsers().remove(this);
    }

    public Set<ProjectHistoryEntity> getProjectHistories() {
        return projectHistories;
    }

    public void setProjectHistories(Set<ProjectHistoryEntity> projectHistories) {
        this.projectHistories = projectHistories;
    }

    public void addProjectHistory(ProjectHistoryEntity projectHistory) {
        projectHistories.add(projectHistory);
        projectHistory.setUser(this);
    }

    public void removeProjectHistory(ProjectHistoryEntity projectHistory) {
        projectHistories.remove(projectHistory);
        projectHistory.setUser(null);
    }

    public Set<ChatMessageEntity> getChatmessages() {
        return chatmessages;
    }

    public void setChatmessages(Set<ChatMessageEntity> chatmessages) {
        this.chatmessages = chatmessages;
    }

    public void addChatMessage(ChatMessageEntity chatMessage) {
        chatmessages.add(chatMessage);
        chatMessage.setSender(this);
    }

    public void removeChatMessage(ChatMessageEntity chatMessage) {
        chatmessages.remove(chatMessage);
        chatMessage.setSender(null);
    }
}
