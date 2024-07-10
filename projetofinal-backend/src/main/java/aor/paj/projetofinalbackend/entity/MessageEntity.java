package aor.paj.projetofinalbackend.entity;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * MessageEntity is an entity class representing a message exchanged between users.
 * It extends the {@link BaseMessageEntity} to inherit common message properties and adds attributes specific to messages, such as the seen status and the receiver.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Entity
@Table(name = "message")
@NamedQueries({
        @NamedQuery(name = "Message.findReceivedMessagesByUserId", query = "SELECT m FROM MessageEntity m WHERE m.receiver.id = :userId ORDER BY m.timestamp DESC"),
        @NamedQuery(name = "Message.findSentMessagesByUserId", query = "SELECT m FROM MessageEntity m WHERE m.sender.id = :userId ORDER BY m.timestamp DESC"),
        @NamedQuery(name = "Message.countReceivedMessagesByUserId", query = "SELECT COUNT(m) FROM MessageEntity m WHERE m.receiver.id = :userId"),
        @NamedQuery(name = "Message.countSentMessagesByUserId", query = "SELECT COUNT(m) FROM MessageEntity m WHERE m.sender.id = :userId"),
        @NamedQuery(name = "Message.updateSeenStatusByIds", query = "UPDATE MessageEntity m SET m.seen = :seen WHERE m.id IN :ids"),
        @NamedQuery(name = "Message.findUnreadMessagesByUserId", query = "SELECT m FROM MessageEntity m WHERE m.receiver.id = :userId AND m.seen = false ORDER BY m.timestamp DESC"),
        @NamedQuery(name = "Message.countUnreadMessagesByUserId", query = "SELECT COUNT(m) FROM MessageEntity m WHERE m.receiver.id = :userId AND m.seen = false"),
        @NamedQuery(name = "Message.findReceivedMessagesByUserIdAndUsernameAndContent", query = "SELECT m FROM MessageEntity m WHERE m.receiver.id = :userId " +
                "AND (:username IS NULL OR m.sender.username LIKE CONCAT('%', :username, '%')) " +
                "AND (:content IS NULL OR m.content LIKE CONCAT('%', :content, '%')) " +
                "ORDER BY m.timestamp DESC"),
        @NamedQuery(name = "Message.findSentMessagesByUserIdAndUsernameAndContent", query = "SELECT m FROM MessageEntity m WHERE m.sender.id = :userId " +
                "AND (:username IS NULL OR m.receiver.username LIKE CONCAT('%', :username, '%')) " +
                "AND (:content IS NULL OR m.content LIKE CONCAT('%', :content, '%')) " +
                "ORDER BY m.timestamp DESC"),
        @NamedQuery(name = "Message.findUnreadMessagesByUserIdAndUsernameAndContent", query = "SELECT m FROM MessageEntity m WHERE m.receiver.id = :userId " +
                "AND (:username IS NULL OR m.sender.username LIKE CONCAT('%', :username, '%')) " +
                "AND (:content IS NULL OR m.content LIKE CONCAT('%', :content, '%')) " +
                "AND m.seen = false ORDER BY m.timestamp DESC"),
        @NamedQuery(name = "Message.countReceivedMessagesByUserIdAndUsernameAndContent", query = "SELECT COUNT(m) FROM MessageEntity m WHERE m.receiver.id = :userId " +
                "AND (:username IS NULL OR m.sender.username LIKE CONCAT('%', :username, '%')) " +
                "AND (:content IS NULL OR m.content LIKE CONCAT('%', :content, '%'))"),
        @NamedQuery(name = "Message.countSentMessagesByUserIdAndUsernameAndContent", query = "SELECT COUNT(m) FROM MessageEntity m WHERE m.sender.id = :userId " +
                "AND (:username IS NULL OR m.receiver.username LIKE CONCAT('%', :username, '%')) " +
                "AND (:content IS NULL OR m.content LIKE CONCAT('%', :content, '%'))"),
        @NamedQuery(name = "Message.countUnreadMessagesByUserIdAndUsernameAndContent", query = "SELECT COUNT(m) FROM MessageEntity m WHERE m.receiver.id = :userId " +
                "AND (:username IS NULL OR m.sender.username LIKE CONCAT('%', :username, '%')) " +
                "AND (:content IS NULL OR m.content LIKE CONCAT('%', :content, '%')) " +
                "AND m.seen = false")
})
public class MessageEntity extends BaseMessageEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Indicates whether the message has been seen by the receiver.
     */
    @Column(name = "seen", nullable = false)
    private Boolean seen;

    /**
     * The user who received the message.
     * This is a many-to-one relationship, and the receiver is fetched lazily.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private UserEntity receiver;

    /**
     * Default constructor for MessageEntity.
     */
    public MessageEntity() {
    }

    /**
     * Retrieves the seen status of the message.
     *
     * @return true if the message has been seen, false otherwise.
     */
    public Boolean getSeen() {
        return seen;
    }

    /**
     * Sets the seen status of the message.
     *
     * @param seen true if the message has been seen, false otherwise.
     */
    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    /**
     * Retrieves the receiver of the message.
     *
     * @return the user who received the message.
     */
    public UserEntity getReceiver() {
        return receiver;
    }

    /**
     * Sets the receiver of the message.
     *
     * @param receiver the user to set as the receiver of the message.
     */
    public void setReceiver(UserEntity receiver) {
        this.receiver = receiver;
    }
}
