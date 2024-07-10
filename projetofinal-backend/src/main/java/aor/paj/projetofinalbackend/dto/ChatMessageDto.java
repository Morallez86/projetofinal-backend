package aor.paj.projetofinalbackend.dto;

import aor.paj.projetofinalbackend.utils.CustomLocalDateTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.xml.bind.annotation.XmlElement;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) representing a chat message.
 * This class is used to transfer chat message data between different layers of the application.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public class ChatMessageDto {
    private Long id;

    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timestamp;

    private String content;

    private Long senderId;

    private String senderUsername;

    private Boolean senderOnline;

    private Long projectId;

    /**
     * Default constructor for ChatMessageDto.
     */
    public ChatMessageDto() {
    }

    /**
     * Gets the ID of the chat message.
     *
     * @return The ID of the chat message.
     */
    @XmlElement
    public Long getId() {
        return id;
    }

    /**
     * Gets the timestamp of the chat message.
     *
     * @return The timestamp of the chat message.
     */
    @XmlElement
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Gets the content of the chat message.
     *
     * @return The content of the chat message.
     */
    @XmlElement
    public String getContent() {
        return content;
    }

    /**
     * Gets the ID of the sender of the chat message.
     *
     * @return The ID of the sender.
     */
    @XmlElement
    public Long getSenderId() {
        return senderId;
    }

    /**
     * Gets the username of the sender of the chat message.
     *
     * @return The username of the sender.
     */
    @XmlElement
    public String getSenderUsername() {
        return senderUsername;
    }

    /**
     * Gets the online status of the sender of the chat message.
     *
     * @return True if the sender is online, false otherwise.
     */
    @XmlElement
    public Boolean getSenderOnline() {
        return senderOnline;
    }

    /**
     * Gets the project ID associated with the chat message.
     *
     * @return The project ID.
     */
    @XmlElement
    public Long getProjectId() {
        return projectId;
    }

    /**
     * Sets the ID of the chat message.
     *
     * @param id The ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Sets the timestamp of the chat message.
     *
     * @param timestamp The timestamp to set.
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Sets the content of the chat message.
     *
     * @param content The content to set.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Sets the ID of the sender of the chat message.
     *
     * @param senderId The sender ID to set.
     */
    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    /**
     * Sets the username of the sender of the chat message.
     *
     * @param senderUsername The sender username to set.
     */
    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    /**
     * Sets the online status of the sender of the chat message.
     *
     * @param senderOnline The sender online status to set.
     */
    public void setSenderOnline(Boolean senderOnline) {
        this.senderOnline = senderOnline;
    }

    /**
     * Sets the project ID associated with the chat message.
     *
     * @param projectId The project ID to set.
     */
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
