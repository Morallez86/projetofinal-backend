package aor.paj.projetofinalbackend.dto;

import aor.paj.projetofinalbackend.utils.CustomLocalDateTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.xml.bind.annotation.XmlElement;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) representing a notification in the system.
 * This class is used to transfer notification data between different layers of the application.
 *
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public class NotificationDto {

    private Long id;
    private String description;
    private String type;
    private boolean seen;
    private String action;

    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timestamp;

    private Long receiverId;
    private Long senderId;
    private String senderUsername;
    private Long projectId;
    private String projectTitle;
    private boolean approval;

    /**
     * Retrieves the ID of the notification.
     *
     * @return the ID of the notification.
     */
    @XmlElement
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the notification.
     *
     * @param id the ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the description of the notification.
     *
     * @return the description of the notification.
     */
    @XmlElement
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the notification.
     *
     * @param description the description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the type of the notification.
     *
     * @return the type of the notification.
     */
    @XmlElement
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the notification.
     *
     * @param type the type to set.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Checks if the notification has been seen.
     *
     * @return true if the notification has been seen; false otherwise.
     */
    @XmlElement
    public boolean isSeen() {
        return seen;
    }

    /**
     * Sets whether the notification has been seen.
     *
     * @param seen the seen status to set.
     */
    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    /**
     * Retrieves the action associated with the notification.
     *
     * @return the action associated with the notification.
     */
    @XmlElement
    public String getAction() {
        return action;
    }

    /**
     * Sets the action associated with the notification.
     *
     * @param action the action to set.
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Retrieves the timestamp when the notification was created.
     *
     * @return the timestamp of the notification.
     */
    @XmlElement
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp of the notification.
     *
     * @param timestamp the timestamp to set.
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Retrieves the ID of the receiver of the notification.
     *
     * @return the ID of the receiver.
     */
    @XmlElement
    public Long getReceiverId() {
        return receiverId;
    }

    /**
     * Sets the ID of the receiver of the notification.
     *
     * @param receiverId the receiver ID to set.
     */
    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    /**
     * Retrieves the ID of the sender of the notification.
     *
     * @return the ID of the sender.
     */
    @XmlElement
    public Long getSenderId() {
        return senderId;
    }

    /**
     * Sets the ID of the sender of the notification.
     *
     * @param senderId the sender ID to set.
     */
    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    /**
     * Retrieves the username of the sender of the notification.
     *
     * @return the username of the sender.
     */
    @XmlElement
    public String getSenderUsername() {
        return senderUsername;
    }

    /**
     * Sets the username of the sender of the notification.
     *
     * @param senderUsername the sender username to set.
     */
    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    /**
     * Retrieves the ID of the project associated with the notification.
     *
     * @return the ID of the project.
     */
    @XmlElement
    public Long getProjectId() {
        return projectId;
    }

    /**
     * Sets the ID of the project associated with the notification.
     *
     * @param projectId the project ID to set.
     */
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    /**
     * Retrieves the title of the project associated with the notification.
     *
     * @return the title of the project.
     */
    @XmlElement
    public String getProjectTitle() {
        return projectTitle;
    }

    /**
     * Sets the title of the project associated with the notification.
     *
     * @param projectTitle the project title to set.
     */
    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    /**
     * Checks if the notification is an approval notification.
     *
     * @return true if the notification is an approval notification; false otherwise.
     */
    @XmlElement
    public boolean isApproval() {
        return approval;
    }

    /**
     * Sets whether the notification is an approval notification.
     *
     * @param approval the approval status to set.
     */
    public void setApproval(boolean approval) {
        this.approval = approval;
    }

    @Override
    public String toString() {
        return "NotificationDto{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", seen=" + seen +
                ", action='" + action + '\'' +
                ", timestamp=" + timestamp +
                ", receiverId=" + receiverId +
                ", senderId=" + senderId +
                ", senderUsername='" + senderUsername + '\'' +
                ", projectId=" + projectId +
                ", projectTitle='" + projectTitle + '\'' +
                ", approval=" + approval +
                '}';
    }
}
