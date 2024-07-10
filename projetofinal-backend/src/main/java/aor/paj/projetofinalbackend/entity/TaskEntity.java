package aor.paj.projetofinalbackend.entity;

import aor.paj.projetofinalbackend.utils.TaskStatus;
import aor.paj.projetofinalbackend.utils.TaskPriority;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class representing a task within a project.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
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

    /**
     * Default constructor for TaskEntity.
     */
    public TaskEntity() {
    }

    /**
     * Get the ID of the task.
     *
     * @return The ID of the task.
     */
    public Long getId() {
        return id;
    }

    /**
     * Get the title of the task.
     *
     * @return The title of the task.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the title of the task.
     *
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the description of the task.
     *
     * @return The description of the task.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the task.
     *
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the planned starting date of the task.
     *
     * @return The planned starting date of the task.
     */
    public LocalDateTime getPlannedStartingDate() {
        return plannedStartingDate;
    }

    /**
     * Set the planned starting date of the task.
     *
     * @param plannedStartingDate The planned starting date to set.
     */
    public void setPlannedStartingDate(LocalDateTime plannedStartingDate) {
        this.plannedStartingDate = plannedStartingDate;
    }

    /**
     * Get the actual starting date of the task.
     *
     * @return The actual starting date of the task.
     */
    public LocalDateTime getStartingDate() {
        return startingDate;
    }

    /**
     * Set the actual starting date of the task.
     *
     * @param startingDate The actual starting date to set.
     */
    public void setStartingDate(LocalDateTime startingDate) {
        this.startingDate = startingDate;
    }

    /**
     * Get the planned ending date of the task.
     *
     * @return The planned ending date of the task.
     */
    public LocalDateTime getPlannedEndingDate() {
        return plannedEndingDate;
    }

    /**
     * Set the planned ending date of the task.
     *
     * @param plannedEndingDate The planned ending date to set.
     */
    public void setPlannedEndingDate(LocalDateTime plannedEndingDate) {
        this.plannedEndingDate = plannedEndingDate;
    }

    /**
     * Get the actual ending date of the task.
     *
     * @return The actual ending date of the task.
     */
    public LocalDateTime getEndingDate() {
        return endingDate;
    }

    /**
     * Set the actual ending date of the task.
     *
     * @param endingDate The actual ending date to set.
     */
    public void setEndingDate(LocalDateTime endingDate) {
        this.endingDate = endingDate;
    }

    /**
     * Get the status of the task.
     *
     * @return The status of the task.
     */
    public TaskStatus getStatus() {
        return status;
    }

    /**
     * Set the status of the task.
     *
     * @param status The status to set.
     */
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    /**
     * Get the priority of the task.
     *
     * @return The priority of the task.
     */
    public TaskPriority getPriority() {
        return priority;
    }

    /**
     * Set the priority of the task.
     *
     * @param priority The priority to set.
     */
    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    /**
     * Get the contributors associated with the task.
     *
     * @return The contributors associated with the task.
     */
    public String getContributors() {
        return contributors;
    }

    /**
     * Set the contributors associated with the task.
     *
     * @param contributors The contributors to set.
     */
    public void setContributors(String contributors) {
        this.contributors = contributors;
    }

    /**
     * Get the project to which the task belongs.
     *
     * @return The project to which the task belongs.
     */
    public ProjectEntity getProject() {
        return project;
    }

    /**
     * Set the project to which the task belongs.
     *
     * @param project The project to set.
     */
    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    /**
     * Get the user assigned to the task.
     *
     * @return The user assigned to the task.
     */
    public UserEntity getUser() {
        return user;
    }

    /**
     * Set the user assigned to the task.
     *
     * @param user The user to set.
     */
    public void setUser(UserEntity user) {
        this.user = user;
    }

    /**
     * Get the list of tasks that this task depends on.
     *
     * @return The list of tasks that this task depends on.
     */
    public List<TaskEntity> getDependencies() {
        return dependencies;
    }

    /**
     * Set the list of tasks that this task depends on.
     *
     * @param dependencies The list of tasks to set as dependencies.
     */
    public void setDependencies(List<TaskEntity> dependencies) {
        this.dependencies = dependencies;
    }

    /**
     * Get the list of tasks that depend on this task.
     *
     * @return The list of tasks that depend on this task.
     */
    public List<TaskEntity> getDependentTasks() {
        return dependentTasks;
    }

    /**
     * Set the list of tasks that depend on this task.
     *
     * @param dependentTasks The list of dependent tasks to set.
     */
    public void setDependentTasks(List<TaskEntity> dependentTasks) {
        this.dependentTasks = dependentTasks;
    }

    /**
     * Get the logs or history associated with the task.
     *
     * @return The list of logs or history associated with the task.
     */
    public List<ProjectHistoryEntity> getLogs() {
        return logs;
    }

    /**
     * Set the ID of the task.
     *
     * @param id The ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Set the logs or history associated with the task.
     *
     * @param logs The list of logs or history to set.
     */
    public void setLogs(List<ProjectHistoryEntity> logs) {
        this.logs = logs;
    }
}
