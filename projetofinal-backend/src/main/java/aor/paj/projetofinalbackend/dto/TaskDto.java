package aor.paj.projetofinalbackend.dto;

import aor.paj.projetofinalbackend.utils.CustomLocalDateTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.xml.bind.annotation.XmlElement;

import java.time.LocalDateTime;
import java.util.List;

/**
 * TaskDto is a Data Transfer Object (DTO) class representing a task within a project.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public class TaskDto {

    private Long id;
    private String title;
    private String description;

    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime plannedStartingDate;

    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime startingDate;

    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime plannedEndingDate;

    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime endingDate;

    private int status;
    private int priority;
    private String contributors;
    private Long userId;
    private List<Long> dependencies;
    private List<Long> dependentTasks;
    private Long projectId;
    private String userName;

    /**
     * Default constructor for TaskDto.
     */
    public TaskDto() {
    }

    /**
     * Retrieves the ID of the task.
     *
     * @return the ID of the task.
     */
    @XmlElement
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the task.
     *
     * @param id the ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the title of the task.
     *
     * @return the title of the task.
     */
    @XmlElement
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the task.
     *
     * @param title the title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Retrieves the description of the task.
     *
     * @return the description of the task.
     */
    @XmlElement
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the task.
     *
     * @param description the description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the planned starting date of the task.
     *
     * @return the planned starting date of the task.
     */
    @XmlElement
    public LocalDateTime getPlannedStartingDate() {
        return plannedStartingDate;
    }

    /**
     * Sets the planned starting date of the task.
     *
     * @param plannedStartingDate the planned starting date to set.
     */
    public void setPlannedStartingDate(LocalDateTime plannedStartingDate) {
        this.plannedStartingDate = plannedStartingDate;
    }

    /**
     * Retrieves the actual starting date of the task.
     *
     * @return the actual starting date of the task.
     */
    @XmlElement
    public LocalDateTime getStartingDate() {
        return startingDate;
    }

    /**
     * Sets the actual starting date of the task.
     *
     * @param startingDate the actual starting date to set.
     */
    public void setStartingDate(LocalDateTime startingDate) {
        this.startingDate = startingDate;
    }

    /**
     * Retrieves the planned ending date of the task.
     *
     * @return the planned ending date of the task.
     */
    @XmlElement
    public LocalDateTime getPlannedEndingDate() {
        return plannedEndingDate;
    }

    /**
     * Sets the planned ending date of the task.
     *
     * @param plannedEndingDate the planned ending date to set.
     */
    public void setPlannedEndingDate(LocalDateTime plannedEndingDate) {
        this.plannedEndingDate = plannedEndingDate;
    }

    /**
     * Retrieves the actual ending date of the task.
     *
     * @return the actual ending date of the task.
     */
    @XmlElement
    public LocalDateTime getEndingDate() {
        return endingDate;
    }

    /**
     * Sets the actual ending date of the task.
     *
     * @param endingDate the actual ending date to set.
     */
    public void setEndingDate(LocalDateTime endingDate) {
        this.endingDate = endingDate;
    }

    /**
     * Retrieves the status of the task.
     *
     * @return the status of the task.
     */
    @XmlElement
    public int getStatus() {
        return status;
    }

    /**
     * Sets the status of the task.
     *
     * @param status the status to set.
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Retrieves the priority of the task.
     *
     * @return the priority of the task.
     */
    @XmlElement
    public int getPriority() {
        return priority;
    }

    /**
     * Sets the priority of the task.
     *
     * @param priority the priority to set.
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * Retrieves the contributors of the task.
     *
     * @return the contributors of the task.
     */
    @XmlElement
    public String getContributors() {
        return contributors;
    }

    /**
     * Sets the contributors of the task.
     *
     * @param contributors the contributors to set.
     */
    public void setContributors(String contributors) {
        this.contributors = contributors;
    }

    /**
     * Retrieves the ID of the user assigned to the task.
     *
     * @return the ID of the user assigned to the task.
     */
    @XmlElement
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the ID of the user assigned to the task.
     *
     * @param userId the ID of the user to set.
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Retrieves the IDs of the tasks that this task depends on.
     *
     * @return the IDs of the tasks that this task depends on.
     */
    @XmlElement
    public List<Long> getDependencies() {
        return dependencies;
    }

    /**
     * Sets the IDs of the tasks that this task depends on.
     *
     * @param dependencies the IDs of the tasks to set as dependencies.
     */
    public void setDependencies(List<Long> dependencies) {
        this.dependencies = dependencies;
    }

    /**
     * Retrieves the IDs of the tasks that depend on this task.
     *
     * @return the IDs of the tasks that depend on this task.
     */
    @XmlElement
    public List<Long> getDependentTasks() {
        return dependentTasks;
    }

    /**
     * Sets the IDs of the tasks that depend on this task.
     *
     * @param dependentTasks the IDs of the tasks to set as dependent tasks.
     */
    public void setDependentTasks(List<Long> dependentTasks) {
        this.dependentTasks = dependentTasks;
    }

    /**
     * Retrieves the ID of the project to which the task belongs.
     *
     * @return the ID of the project to which the task belongs.
     */
    @XmlElement
    public Long getProjectId() {
        return projectId;
    }

    /**
     * Sets the ID of the project to which the task belongs.
     *
     * @param projectId the ID of the project to set.
     */
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    /**
     * Retrieves the name of the user assigned to the task.
     *
     * @return the name of the user assigned to the task.
     */
    @XmlElement
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the name of the user assigned to the task.
     *
     * @param userName the name of the user to set.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
