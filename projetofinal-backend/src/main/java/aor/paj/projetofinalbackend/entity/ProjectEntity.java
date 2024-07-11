package aor.paj.projetofinalbackend.entity;

import aor.paj.projetofinalbackend.utils.ProjectStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * ProjectEntity represents a project in the application.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Entity
@Table(name="project")
@NamedQueries({
        @NamedQuery(name = "ProjectEntity.findAll", query = "SELECT p FROM ProjectEntity p ORDER BY p.creationDate DESC"),
        @NamedQuery(name = "ProjectEntity.findProjectById", query = "SELECT p FROM ProjectEntity p WHERE p.id = :id"),
        @NamedQuery(name = "ProjectEntity.getTotalProjectCount", query = "SELECT COUNT(p) FROM ProjectEntity p"),
        @NamedQuery(name = "ProjectEntity.findTasksByProjectId",
                query = "SELECT t FROM TaskEntity t WHERE t.project.id = :projectId ORDER BY CASE WHEN t.status = 'DONE' THEN 1 ELSE 0 END, t.plannedEndingDate ASC"),
        @NamedQuery(name = "ProjectEntity.findUserProjectsByProjectId",
                query = "SELECT up FROM UserProjectEntity up WHERE up.project.id = :projectId"),
        @NamedQuery(
                name = "ProjectEntity.findTasksByProjectIdAndEndingDate",
                query = "SELECT t FROM TaskEntity t WHERE t.project.id = :projectId AND t.plannedEndingDate <= :plannedStartingDate AND t.status != 'DONE' ORDER BY t.plannedEndingDate ASC"
        ),
        @NamedQuery(name = "ProjectEntity.getTotalUserCount", query = "SELECT COUNT(up) FROM UserProjectEntity up"),
        @NamedQuery(name = "ProjectEntity.getApprovedProjectCount", query = "SELECT COUNT(p) FROM ProjectEntity p WHERE p.approved = TRUE"),
        @NamedQuery(name = "ProjectEntity.getFinishedProjectCount", query = "SELECT COUNT(p) FROM ProjectEntity p WHERE p.status = 'FINISHED'"),
        @NamedQuery(name = "ProjectEntity.getCanceledProjectCount", query = "SELECT COUNT(p) FROM ProjectEntity p WHERE p.status = 'CANCELLED'"),
        @NamedQuery(name = "ProjectEntity.getAverageExecutionTime", query = "SELECT AVG(FUNCTION('DATEDIFF', p.endDate, p.startingDate)) FROM ProjectEntity p WHERE p.endDate IS NOT NULL"),
        @NamedQuery(name = "ProjectEntity.searchProjects",
                query = "SELECT p FROM ProjectEntity p " +
                        "WHERE (:searchTerm IS NULL OR " +
                        "       LOWER(p.title) LIKE CONCAT('%', LOWER(:searchTerm), '%')) " +
                        "  AND (:skillString IS NULL OR EXISTS (SELECT s FROM p.skills s WHERE LOWER(s.name) LIKE CONCAT('%', LOWER(:skillString), '%'))) " +
                        "  AND (:interestString IS NULL OR EXISTS (SELECT i FROM p.interests i WHERE LOWER(i.name) LIKE CONCAT('%', LOWER(:interestString), '%'))) " +
                        "  AND (:status IS NULL OR p.status = :status) " +
                        "ORDER BY p.creationDate DESC"),
        @NamedQuery(name = "ProjectEntity.findTaskByTitleAndProjectId",
                query = "SELECT t FROM TaskEntity t WHERE t.project.id = :projectId AND LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%'))")
})
public class ProjectEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The unique identifier for the project.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The title of the project.
     */
    @Column(name = "title", nullable = false, unique = true)
    private String title;

    /**
     * The description of the project.
     */
    @Column(name = "description", nullable = false)
    private String description;

    /**
     * The motivation behind the project.
     */
    @Column(name = "motivation")
    private String motivation;

    /**
     * The status of the project.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProjectStatus status;

    /**
     * The maximum number of users allowed in the project.
     */
    @Column(name = "max_users", nullable = false)
    private int maxUsers;

    /**
     * The owner of the project.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private UserEntity owner;

    /**
     * The workplace associated with the project.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workplace_id", nullable = false)
    private WorkplaceEntity workplace;

    /**
     * Indicates whether the project is approved.
     */
    @Column(name="approved")
    private Boolean approved;

    /**
     * The creation date of the project.
     */
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    /**
     * The date when the project was approved.
     */
    @Column(name = "approved_date")
    private LocalDateTime approvedDate;

    /**
     * The starting date of the project.
     */
    @Column(name = "starting_date")
    private LocalDateTime startingDate;

    /**
     * The planned end date of the project.
     */
    @Column(name = "planned_end_date")
    private LocalDateTime plannedEndDate;

    /**
     * The actual end date of the project.
     */
    @Column(name = "end_date")
    private LocalDateTime endDate;

    /**
     * The user projects associated with this project.
     */
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserProjectEntity> userProjects = new HashSet<>();

    /**
     * The components associated with this project.
     */
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ComponentEntity> components = new HashSet<>();

    /**
     * The resources associated with this project.
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST}, mappedBy = "projects")
    private Set<ResourceEntity> resources = new HashSet<>();

    /**
     * The tasks associated with this project.
     */
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<TaskEntity> tasks = new ArrayList<>();

    /**
     * The skills required for this project.
     */
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "project_skill",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private Set<SkillEntity> skills = new HashSet<>();

    /**
     * The interests related to this project.
     */
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "project_interest",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "interest_id"))
    private Set<InterestEntity> interests = new HashSet<>();

    /**
     * The history records associated with this project.
     */
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("timestamp DESC")
    private Set<ProjectHistoryEntity> historyRecords = new HashSet<>();

    /**
     * The chat messages associated with this project.
     */
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("timestamp ASC")
    private Set<ChatMessageEntity> chatMessages = new HashSet<>();

    /**
     * The notifications associated with this project.
     */
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<NotificationEntity> notifications = new HashSet<>();

    /**
     * Default constructor for ProjectEntity.
     */
    public ProjectEntity() {
    }

    /**
     * Constructor for initializing the project with a title.
     *
     * @param title the title of the project.
     */
    public ProjectEntity(String title) {
        this.title = title;
    }

    /**
     * Get the unique identifier for the project.
     *
     * @return The id of the project.
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the unique identifier for the project.
     *
     * @param id The id to set for the project.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the title of the project.
     *
     * @return The title of the project.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the title of the project.
     *
     * @param title The title to set for the project.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the description of the project.
     *
     * @return The description of the project.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the project.
     *
     * @param description The description to set for the project.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the motivation behind the project.
     *
     * @return The motivation behind the project.
     */
    public String getMotivation() {
        return motivation;
    }

    /**
     * Set the motivation behind the project.
     *
     * @param motivation The motivation to set for the project.
     */
    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    /**
     * Get the status of the project.
     *
     * @return The status of the project.
     */
    public ProjectStatus getStatus() {
        return status;
    }

    /**
     * Set the status of the project.
     *
     * @param status The status to set for the project.
     */
    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    /**
     * Get the maximum number of users allowed in the project.
     *
     * @return The maximum number of users allowed in the project.
     */
    public int getMaxUsers() {
        return maxUsers;
    }

    /**
     * Set the maximum number of users allowed in the project.
     *
     * @param maxUsers The maximum number of users to set for the project.
     */
    public void setMaxUsers(int maxUsers) {
        this.maxUsers = maxUsers;
    }

    /**
     * Get the owner of the project.
     *
     * @return The owner of the project.
     */
    public UserEntity getOwner() {
        return owner;
    }

    /**
     * Set the owner of the project.
     *
     * @param owner The owner to set for the project.
     */
    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    /**
     * Get the workplace associated with the project.
     *
     * @return The workplace associated with the project.
     */
    public WorkplaceEntity getWorkplace() {
        return workplace;
    }

    /**
     * Set the workplace associated with the project.
     *
     * @param workplace The workplace to set for the project.
     */
    public void setWorkplace(WorkplaceEntity workplace) {
        this.workplace = workplace;
    }

    /**
     * Check if the project is approved.
     *
     * @return True if the project is approved, false otherwise.
     */
    public Boolean getApproved() {
        return approved;
    }

    /**
     * Set whether the project is approved.
     *
     * @param approved The approval status to set for the project.
     */
    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    /**
     * Get the creation date of the project.
     *
     * @return The creation date of the project.
     */
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Set the creation date of the project.
     *
     * @param creationDate The creation date to set for the project.
     */
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Get the date when the project was approved.
     *
     * @return The approved date of the project.
     */
    public LocalDateTime getApprovedDate() {
        return approvedDate;
    }

    /**
     * Set the date when the project was approved.
     *
     * @param approvedDate The approved date to set for the project.
     */
    public void setApprovedDate(LocalDateTime approvedDate) {
        this.approvedDate = approvedDate;
    }

    /**
     * Get the starting date of the project.
     *
     * @return The starting date of the project.
     */
    public LocalDateTime getStartingDate() {
        return startingDate;
    }

    /**
     * Set the starting date of the project.
     *
     * @param startingDate The starting date to set for the project.
     */
    public void setStartingDate(LocalDateTime startingDate) {
        this.startingDate = startingDate;
    }

    /**
     * Get the planned end date of the project.
     *
     * @return The planned end date of the project.
     */
    public LocalDateTime getPlannedEndDate() {
        return plannedEndDate;
    }

    /**
     * Set the planned end date of the project.
     *
     * @param plannedEndDate The planned end date to set for the project.
     */
    public void setPlannedEndDate(LocalDateTime plannedEndDate) {
        this.plannedEndDate = plannedEndDate;
    }

    /**
     * Get the actual end date of the project.
     *
     * @return The actual end date of the project.
     */
    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * Set the actual end date of the project.
     *
     * @param endDate The actual end date to set for the project.
     */
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * Get the user projects associated with this project.
     *
     * @return The set of user projects associated with this project.
     */
    public Set<UserProjectEntity> getUserProjects() {
        return userProjects;
    }

    /**
     * Set the user projects associated with this project.
     *
     * @param userProjects The set of user projects to set for this project.
     */
    public void setUserProjects(Set<UserProjectEntity> userProjects) {
        this.userProjects = userProjects;
    }

    /**
     * Get the components associated with this project.
     *
     * @return The set of components associated with this project.
     */
    public Set<ComponentEntity> getComponents() {
        return components;
    }

    /**
     * Set the components associated with this project.
     *
     * @param components The set of components to set for this project.
     */
    public void setComponents(Set<ComponentEntity> components) {
        this.components = components;
    }

    /**
     * Get the resources associated with this project.
     *
     * @return The set of resources associated with this project.
     */
    public Set<ResourceEntity> getResources() {
        return resources;
    }

    /**
     * Set the resources associated with this project.
     *
     * @param resources The set of resources to set for this project.
     */
    public void setResources(Set<ResourceEntity> resources) {
        this.resources = resources;
    }

    /**
     * Get the tasks associated with this project.
     *
     * @return The list of tasks associated with this project.
     */
    public List<TaskEntity> getTasks() {
        return tasks;
    }

    /**
     * Set the tasks associated with this project.
     *
     * @param tasks The list of tasks to set for this project.
     */
    public void setTasks(List<TaskEntity> tasks) {
        this.tasks = tasks;
    }

    /**
     * Get the skills required for this project.
     *
     * @return The set of skills required for this project.
     */
    public Set<SkillEntity> getSkills() {
        return skills;
    }

    /**
     * Set the skills required for this project.
     *
     * @param skills The set of skills to set for this project.
     */
    public void setSkills(Set<SkillEntity> skills) {
        this.skills = skills;
    }

    /**
     * Get the interests related to this project.
     *
     * @return The set of interests related to this project.
     */
    public Set<InterestEntity> getInterests() {
        return interests;
    }

    /**
     * Set the interests related to this project.
     *
     * @param interests The set of interests to set for this project.
     */
    public void setInterests(Set<InterestEntity> interests) {
        this.interests = interests;
    }

    /**
     * Get the history records associated with this project.
     *
     * @return The set of history records associated with this project.
     */
    public Set<ProjectHistoryEntity> getHistoryRecords() {
        return historyRecords;
    }

    /**
     * Set the history records associated with this project.
     *
     * @param historyRecords The set of history records to set for this project.
     */
    public void setHistoryRecords(Set<ProjectHistoryEntity> historyRecords) {
        this.historyRecords = historyRecords;
    }

    /**
     * Get the chat messages associated with this project.
     *
     * @return The set of chat messages associated with this project.
     */
    public Set<ChatMessageEntity> getChatMessages() {
        return chatMessages;
    }

    /**
     * Set the chat messages associated with this project.
     *
     * @param chatMessages The set of chat messages to set for this project.
     */
    public void setChatMessages(Set<ChatMessageEntity> chatMessages) {
        this.chatMessages = chatMessages;
    }

    /**
     * Get the notifications associated with this project.
     *
     * @return The set of notifications associated with this project.
     */
    public Set<NotificationEntity> getNotifications() {
        return notifications;
    }

    /**
     * Set the notifications associated with this project.
     *
     * @param notifications The set of notifications to set for this project.
     */
    public void setNotifications(Set<NotificationEntity> notifications) {
        this.notifications = notifications;
    }

    /**
     * Override of the toString method to provide a string representation of the ProjectEntity object.
     *
     * @return String representation of the ProjectEntity object.
     */
    @Override
    public String toString() {
        return "ProjectEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", motivation='" + motivation + '\'' +
                ", status=" + status +
                ", maxUsers=" + maxUsers +
                ", owner=" + (owner != null ? owner.getId() : null) +
                ", workplace=" + (workplace != null ? workplace.getId() : null) +
                ", approved=" + approved +
                ", creationDate=" + creationDate +
                ", approvedDate=" + approvedDate +
                ", startingDate=" + startingDate +
                ", plannedEndDate=" + plannedEndDate +
                ", endDate=" + endDate +
                ", userProjects=" + userProjects.size() +
                ", components=" + components.size() +
                ", resources=" + resources.size() +
                ", tasks=" + tasks.size() +
                ", skills=" + skills.size() +
                ", interests=" + interests.size() +
                ", historyRecords=" + historyRecords.size() +
                ", chatMessages=" + chatMessages.size() +
                ", notifications=" + notifications.size() +
                '}';
    }
}
