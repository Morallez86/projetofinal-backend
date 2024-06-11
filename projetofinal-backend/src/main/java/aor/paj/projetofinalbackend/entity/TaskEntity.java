package aor.paj.projetofinalbackend.entity;

import aor.paj.projetofinalbackend.utils.TaskStatus;
import aor.paj.projetofinalbackend.utils.TaskPriority;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "task")
@NamedQueries({
        @NamedQuery(name = "Task.findAllTasks", query = "SELECT t FROM TaskEntity t")
})
public class TaskEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "planned_starting_date", nullable = false)
    private LocalDateTime plannedStartingDate;

    @Column(name = "starting_date", nullable = true)
    private LocalDateTime startingDate;

    @Column(name = "planned_ending_date", nullable = false)
    private LocalDateTime plannedEndingDate;

    @Column(name = "ending_date", nullable = true)
    private LocalDateTime endingDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private TaskPriority priority;

    @Column(name = "contributors", nullable = true)
    private String contributors;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToMany
    @JoinTable(
            name = "task_dependencies",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "dependency_id")
    )
    private List<TaskEntity> dependencies = new ArrayList<>();

    @ManyToMany(mappedBy = "dependencies")
    private List<TaskEntity> dependentTasks = new ArrayList<>();

    @OneToMany(mappedBy = "task")
    private List<ProjectHistoryEntity> logs = new ArrayList<>();


    public TaskEntity() {
    }

    public Long getId() {
        return id;
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

    public LocalDateTime getPlannedStartingDate() {
        return plannedStartingDate;
    }

    public void setPlannedStartingDate(LocalDateTime plannedStartingDate) {
        this.plannedStartingDate = plannedStartingDate;
    }

    public LocalDateTime getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDateTime startingDate) {
        this.startingDate = startingDate;
    }

    public LocalDateTime getPlannedEndingDate() {
        return plannedEndingDate;
    }

    public void setPlannedEndingDate(LocalDateTime plannedEndingDate) {
        this.plannedEndingDate = plannedEndingDate;
    }

    public LocalDateTime getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(LocalDateTime endingDate) {
        this.endingDate = endingDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public String getContributors() {
        return contributors;
    }

    public void setContributors(String contributors) {
        this.contributors = contributors;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<TaskEntity> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<TaskEntity> dependencies) {
        this.dependencies = dependencies;
    }

    public List<TaskEntity> getDependentTasks() {
        return dependentTasks;
    }

    public void setDependentTasks(List<TaskEntity> dependentTasks) {
        this.dependentTasks = dependentTasks;
    }

    public List<ProjectHistoryEntity> getLogs() {
        return logs;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLogs(List<ProjectHistoryEntity> logs) {
        this.logs = logs;
    }
}
