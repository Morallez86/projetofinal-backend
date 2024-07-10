package aor.paj.projetofinalbackend.entity;

import aor.paj.projetofinalbackend.utils.HistoryType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entity class representing the history records of a project.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Entity
@Table(name="project_history")
public class ProjectHistoryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "old_description")
    private String oldDescription;

    @Column(name = "new_description")
    private String newDescription;

    @Column(name = "title")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private HistoryType type;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private TaskEntity task;

    /**
     * Default constructor for ProjectHistoryEntity.
     */
    public ProjectHistoryEntity() {
    }

    /**
     * Get the unique identifier of the history record.
     *
     * @return The id of the history record.
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the unique identifier of the history record.
     *
     * @param id The id to set for the history record.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the old description before the change.
     *
     * @return The old description of the history record.
     */
    public String getOldDescription() {
        return oldDescription;
    }

    /**
     * Set the old description before the change.
     *
     * @param oldDescription The old description to set for the history record.
     */
    public void setOldDescription(String oldDescription) {
        this.oldDescription = oldDescription;
    }

    /**
     * Get the new description after the change.
     *
     * @return The new description of the history record.
     */
    public String getNewDescription() {
        return newDescription;
    }

    /**
     * Set the new description after the change.
     *
     * @param newDescription The new description to set for the history record.
     */
    public void setNewDescription(String newDescription) {
        this.newDescription = newDescription;
    }

    /**
     * Get the type of history (e.g., description change, status update).
     *
     * @return The type of history.
     */
    public HistoryType getType() {
        return type;
    }

    /**
     * Set the type of history (e.g., description change, status update).
     *
     * @param type The type of history to set.
     */
    public void setType(HistoryType type) {
        this.type = type;
    }

    /**
     * Get the timestamp when the history record was created.
     *
     * @return The timestamp of the history record.
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Set the timestamp when the history record was created.
     *
     * @param timestamp The timestamp to set for the history record.
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Get the project associated with this history record.
     *
     * @return The project associated with the history record.
     */
    public ProjectEntity getProject() {
        return project;
    }

    /**
     * Set the project associated with this history record.
     *
     * @param project The project to set for the history record.
     */
    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    /**
     * Get the user associated with this history record.
     *
     * @return The user associated with the history record.
     */
    public UserEntity getUser() {
        return user;
    }

    /**
     * Set the user associated with this history record.
     *
     * @param user The user to set for the history record.
     */
    public void setUser(UserEntity user) {
        this.user = user;
    }

    /**
     * Get the task associated with this history record.
     *
     * @return The task associated with the history record.
     */
    public TaskEntity getTask() {
        return task;
    }

    /**
     * Set the task associated with this history record.
     *
     * @param task The task to set for the history record.
     */
    public void setTask(TaskEntity task) {
        this.task = task;
    }

    /**
     * Get the title of the history record.
     *
     * @return The title of the history record.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the title of the history record.
     *
     * @param title The title to set for the history record.
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
