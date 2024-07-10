package aor.paj.projetofinalbackend.dto;

import aor.paj.projetofinalbackend.utils.CustomLocalDateTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.xml.bind.annotation.XmlElement;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) representing project history within the system.
 * This class is used to transfer project history data between different layers of the application.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public class ProjectHistoryDto {

    private Long id;
    private String newDescription;
    private int type;

    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timestamp;

    private long userId;
    private long projectId;
    private long taskId;
    private String taskName;
    private String userName;
    private String title;

    /**
     * Default constructor.
     */
    public ProjectHistoryDto() {
    }

    /**
     * Retrieves the ID of the project history entry.
     *
     * @return the ID of the project history entry.
     */
    @XmlElement
    public Long getId() {
        return id;
    }

    /**
     * Retrieves the new description associated with the project history entry.
     *
     * @return the new description associated with the project history entry.
     */
    @XmlElement
    public String getNewDescription() {
        return newDescription;
    }

    /**
     * Retrieves the type of action performed in the project history entry.
     *
     * @return the type of action performed in the project history entry.
     */
    @XmlElement
    public int getType() {
        return type;
    }

    /**
     * Retrieves the timestamp when the action occurred in the project history entry.
     *
     * @return the timestamp when the action occurred in the project history entry.
     */
    @XmlElement
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Retrieves the ID of the user associated with the project history entry.
     *
     * @return the ID of the user associated with the project history entry.
     */
    @XmlElement
    public long getUserId() {
        return userId;
    }

    /**
     * Retrieves the ID of the project associated with the project history entry.
     *
     * @return the ID of the project associated with the project history entry.
     */
    @XmlElement
    public long getProjectId() {
        return projectId;
    }

    /**
     * Retrieves the ID of the task associated with the project history entry.
     *
     * @return the ID of the task associated with the project history entry.
     */
    @XmlElement
    public long getTaskId() {
        return taskId;
    }

    /**
     * Retrieves the name of the task associated with the project history entry.
     *
     * @return the name of the task associated with the project history entry.
     */
    @XmlElement
    public String getTaskName() {
        return taskName;
    }

    /**
     * Retrieves the name of the user associated with the project history entry.
     *
     * @return the name of the user associated with the project history entry.
     */
    @XmlElement
    public String getUserName() {
        return userName;
    }

    /**
     * Retrieves the title of the project associated with the project history entry.
     *
     * @return the title of the project associated with the project history entry.
     */
    @XmlElement
    public String getTitle() {
        return title;
    }

    /**
     * Sets the ID of the project history entry.
     *
     * @param id the ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Sets the new description associated with the project history entry.
     *
     * @param newDescription the new description to set.
     */
    public void setNewDescription(String newDescription) {
        this.newDescription = newDescription;
    }

    /**
     * Sets the type of action performed in the project history entry.
     *
     * @param type the type of action to set.
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * Sets the timestamp when the action occurred in the project history entry.
     *
     * @param timestamp the timestamp to set.
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Sets the ID of the user associated with the project history entry.
     *
     * @param userId the user ID to set.
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Sets the ID of the project associated with the project history entry.
     *
     * @param projectId the project ID to set.
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * Sets the ID of the task associated with the project history entry.
     *
     * @param taskId the task ID to set.
     */
    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    /**
     * Sets the name of the task associated with the project history entry.
     *
     * @param taskName the task name to set.
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * Sets the name of the user associated with the project history entry.
     *
     * @param userName the user name to set.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Sets the title of the project associated with the project history entry.
     *
     * @param title the project title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
