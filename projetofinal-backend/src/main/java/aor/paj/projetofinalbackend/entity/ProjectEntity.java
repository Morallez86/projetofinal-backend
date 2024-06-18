package aor.paj.projetofinalbackend.entity;

import aor.paj.projetofinalbackend.utils.ProjectStatus;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

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
        )


})

public class ProjectEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "motivation", nullable = false)
    private String motivation;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProjectStatus status;

    @Column(name = "max_users", nullable = false)
    private int maxUsers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private UserEntity owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workplace_id", nullable = false)
    private WorkplaceEntity workplace;

    @Column(name="approved")
    private Boolean approved;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "approved_date")
    private LocalDateTime approvedDate;

    @Column(name = "starting_date")
    private LocalDateTime startingDate;

    @Column(name = "planned_end_date")
    private LocalDateTime plannedEndDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserProjectEntity> userProjects = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ComponentEntity> components = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST}, mappedBy = "projects")
    private Set<ResourceEntity> resources = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<TaskEntity> tasks = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "project_skill",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private Set<SkillEntity> skills = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "project_interest",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "interest_id"))
    private Set<InterestEntity> interests = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("timestamp DESC")
    private Set<ProjectHistoryEntity> historyRecords = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChatMessageEntity> chatMessages = new HashSet<>();

    public ProjectEntity() {
    }

    public ProjectEntity(String title) {
        this.title = title;
    }

    // Getters and Setters for all attributes including the new workplace relationship

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public int getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(int maxUsers) {
        this.maxUsers = maxUsers;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public WorkplaceEntity getWorkplace() {
        return workplace;
    }

    public void setWorkplace(WorkplaceEntity workplace) {
        this.workplace = workplace;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(LocalDateTime approvedDate) {
        this.approvedDate = approvedDate;
    }

    public LocalDateTime getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDateTime startingDate) {
        this.startingDate = startingDate;
    }

    public LocalDateTime getPlannedEndDate() {
        return plannedEndDate;
    }

    public void setPlannedEndDate(LocalDateTime plannedEndDate) {
        this.plannedEndDate = plannedEndDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Set<UserProjectEntity> getUserProjects() {
        return userProjects;
    }

    public void setUserProjects(Set<UserProjectEntity> userProjects) {
        this.userProjects = userProjects;
    }

    public Set<ComponentEntity> getComponents() {
        return components;
    }

    public void setComponents(Set<ComponentEntity> components) {
        this.components = components;
    }

    public Set<ResourceEntity> getResources() {
        return resources;
    }

    public void setResources(Set<ResourceEntity> resources) {
        this.resources = resources;
    }

    public List<TaskEntity> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskEntity> tasks) {
        this.tasks = tasks;
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

    public Set<ProjectHistoryEntity> getHistoryRecords() {
        return historyRecords;
    }

    public void setHistoryRecords(Set<ProjectHistoryEntity> historyRecords) {
        this.historyRecords = historyRecords;
    }

    public Set<ChatMessageEntity> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(Set<ChatMessageEntity> chatMessages) {
        this.chatMessages = chatMessages;
    }

    @Override
    public String toString() {
        return "ProjectEntity{" +
                "title='" + title + '\'' +
                '}';
    }
}
