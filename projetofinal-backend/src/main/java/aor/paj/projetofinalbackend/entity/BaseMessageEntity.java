package aor.paj.projetofinalbackend.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * BaseMessageEntity is an abstract base class for message and chat message entities, providing common fields and
 * mappings for messages in the application.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@MappedSuperclass
public abstract class BaseMessageEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The unique identifier for the message.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The timestamp indicating when the message was created or sent.
     */
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    /**
     * The content of the message.
     */
    @Column(name = "content", nullable = false)
    private String content;

    /**
     * The sender of the message, represented as a {@link UserEntity}.
     * This is a many-to-one relationship, and the sender is fetched lazily.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private UserEntity sender;

    /**
     * Retrieves the unique identifier for the message.
     *
     * @return the message id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the message.
     *
     * @param id the message id to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the timestamp indicating when the message was created or sent.
     *
     * @return the message timestamp.
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp indicating when the message was created or sent.
     *
     * @param timestamp the timestamp to set.
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Retrieves the sender of the message.
     *
     * @return the sender of the message.
     */
    public UserEntity getSender() {
        return sender;
    }

    /**
     * Sets the sender of the message.
     *
     * @param sender the sender to set.
     */
    public void setSender(UserEntity sender) {
        this.sender = sender;
    }

    /**
     * Retrieves the content of the message.
     *
     * @return the message content.
     */
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
}
