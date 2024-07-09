package aor.paj.projetofinalbackend.dto;

import aor.paj.projetofinalbackend.utils.CustomLocalDateTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.xml.bind.annotation.XmlElement;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) representing a message between users.
 * This class facilitates transferring message data between different layers of the application.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public class MessageDto {

    private Long id;

    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timestamp;

    private String content;
    private Long senderId;
    private String senderUsername;
    private Long receiverId;
    private String receiverUsername;
    private Boolean seen;

    /**
     * Retrieves the ID of the message.
     *
     * @return the ID of the message.
     */
    @XmlElement
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the message.
     *
     * @param id the ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the timestamp when the message was sent.
     *
     * @return the timestamp of the message.
     */
    @XmlElement
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp of the message.
     *
     * @param timestamp the timestamp to set.
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Retrieves the content of the message.
     *
     * @return the content of the message.
     */
    @XmlElement
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the message.
     *
     * @param content the content to set.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Retrieves the ID of the sender of the message.
     *
     * @return the ID of the sender.
     */
    @XmlElement
    public Long getSenderId() {
        return senderId;
    }

    /**
     * Sets the ID of the sender of the message.
     *
     * @param senderId the sender ID to set.
     */
    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    /**
     * Retrieves the username of the sender of the message.
     *
     * @return the username of the sender.
     */
    @XmlElement
    public String getSenderUsername() {
        return senderUsername;
    }

    /**
     * Sets the username of the sender of the message.
     *
     * @param senderUsername the sender username to set.
     */
    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    /**
     * Retrieves the ID of the receiver of the message.
     *
     * @return the ID of the receiver.
     */
    @XmlElement
    public Long getReceiverId() {
        return receiverId;
    }

    /**
     * Sets the ID of the receiver of the message.
     *
     * @param receiverId the receiver ID to set.
     */
    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    /**
     * Retrieves the username of the receiver of the message.
     *
     * @return the username of the receiver.
     */
    @XmlElement
    public String getReceiverUsername() {
        return receiverUsername;
    }

    /**
     * Sets the username of the receiver of the message.
     *
     * @param receiverUsername the receiver username to set.
     */
    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    /**
     * Checks if the message has been seen by the receiver.
     *
     * @return true if the message has been seen; false otherwise.
     */
    @XmlElement
    public Boolean getSeen() {
        return seen;
    }

    /**
     * Sets whether the message has been seen by the receiver.
     *
     * @param seen the seen status to set.
     */
    public void setSeen(Boolean seen) {
        this.seen = seen;
    }
}
