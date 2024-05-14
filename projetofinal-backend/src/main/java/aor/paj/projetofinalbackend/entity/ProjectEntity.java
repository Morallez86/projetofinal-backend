package aor.paj.projetofinalbackend.entity;

import aor.paj.projetofinalbackend.utils.ProjectStatus;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name="project")
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

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    private ProjectStatus status;

    @Column(name = "max_users", nullable = false)
    private int maxUsers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private UserEntity owner;

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

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "projects")
    private Set<ResourceEntity> resources = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TaskEntity> tasks = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "project_skill",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private Set<SkillEntity> skills = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "project_interest",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "interest_id"))
    private Set<InterestEntity> interests = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectHistoryEntity> historyRecords = new HashSet<>();


    public ProjectEntity() {
        // Default constructor
    }

    public ProjectEntity(String title) {
        this.title = title;
    }

    // Getters and Setters

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

    public Set<TaskEntity> getTasks() {
        return tasks;
    }

    public void setTasks(Set<TaskEntity> tasks) {
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

    public void addUserProject(UserProjectEntity userProject) {
        userProjects.add(userProject);
        userProject.setProject(this);
    }

    public void removeUserProject(UserProjectEntity userProject) {
        userProjects.remove(userProject);
        userProject.setProject(null);
    }

    public void addComponent(ComponentEntity component) {
        components.add(component);
        component.setProject(this);
    }

    public void removeComponent(ComponentEntity component) {
        components.remove(component);
        component.setProject(null);
    }

    public void addTask(TaskEntity task) {
        tasks.add(task);
        task.setProject(this);
    }

    public void removeTask(TaskEntity task) {
        tasks.remove(task);
        task.setProject(null);
    }

    public void addSkill(SkillEntity skill) {
        skills.add(skill);
        skill.getProjects().add(this);
    }

    public void removeSkill(SkillEntity skill) {
        skills.remove(skill);
        skill.getProjects().remove(this);
    }

    public void addInterest(InterestEntity interest) {
        interests.add(interest);
        interest.getProjects().add(this);
    }

    public void removeInterest(InterestEntity interest) {
        interests.remove(interest);
        interest.getProjects().remove(this);
    }

    public Set<ProjectHistoryEntity> getHistoryRecords() {
        return historyRecords;
    }

    public void setHistoryRecords(Set<ProjectHistoryEntity> historyRecords) {
        this.historyRecords = historyRecords;
    }

    public void addHistoryRecord(ProjectHistoryEntity historyRecord) {
        historyRecords.add(historyRecord);
        historyRecord.setProject(this);
    }

    public void removeHistoryRecord(ProjectHistoryEntity historyRecord) {
        historyRecords.remove(historyRecord);
        historyRecord.setProject(null);
    }
}
