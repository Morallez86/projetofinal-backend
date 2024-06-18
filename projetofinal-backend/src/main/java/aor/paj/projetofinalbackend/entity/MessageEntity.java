package aor.paj.projetofinalbackend.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "message")
@NamedQueries({
        @NamedQuery(name = "Message.findReceivedMessagesByUserId", query = "SELECT m FROM MessageEntity m WHERE m.receiver.id = :userId ORDER BY m.timestamp DESC"),
        @NamedQuery(name = "Message.findSentMessagesByUserId", query = "SELECT m FROM MessageEntity m WHERE m.sender.id = :userId ORDER BY m.timestamp DESC"),
        @NamedQuery(name = "Message.countReceivedMessagesByUserId", query = "SELECT COUNT(m) FROM MessageEntity m WHERE m.receiver.id = :userId"),
        @NamedQuery(name = "Message.countSentMessagesByUserId", query = "SELECT COUNT(m) FROM MessageEntity m WHERE m.sender.id = :userId"),
        @NamedQuery(name = "Message.updateSeenStatusByIds",
                query = "UPDATE MessageEntity m SET m.seen = :seen WHERE m.id IN :ids"),
        @NamedQuery(name = "Message.findUnreadMessagesByUserId", query = "SELECT m FROM MessageEntity m WHERE m.receiver.id = :userId AND m.seen = false ORDER BY m.timestamp DESC"),
        @NamedQuery(name = "Message.countUnreadMessagesByUserId", query = "SELECT COUNT(m) FROM MessageEntity m WHERE m.receiver.id = :userId AND m.seen = false"),
        @NamedQuery(name = "Message.findReceivedMessagesByUserIdAndUsernameAndContent",
                query = "SELECT m FROM MessageEntity m WHERE m.receiver.id = :userId " +
                        "AND (:username IS NULL OR m.sender.username LIKE CONCAT('%', :username, '%')) " +
                        "AND (:content IS NULL OR m.content LIKE CONCAT('%', :content, '%')) " +
                        "ORDER BY m.timestamp DESC"),
        @NamedQuery(name = "Message.findSentMessagesByUserIdAndUsernameAndContent",
                query = "SELECT m FROM MessageEntity m WHERE m.sender.id = :userId " +
                        "AND (:username IS NULL OR m.receiver.username LIKE CONCAT('%', :username, '%')) " +
                        "AND (:content IS NULL OR m.content LIKE CONCAT('%', :content, '%')) " +
                        "ORDER BY m.timestamp DESC"),
        @NamedQuery(name = "Message.findUnreadMessagesByUserIdAndUsernameAndContent",
                query = "SELECT m FROM MessageEntity m WHERE m.receiver.id = :userId " +
                        "AND (:username IS NULL OR m.sender.username LIKE CONCAT('%', :username, '%')) " +
                        "AND (:content IS NULL OR m.content LIKE CONCAT('%', :content, '%')) " +
                        "AND m.seen = false ORDER BY m.timestamp DESC"),
        @NamedQuery(name = "Message.countReceivedMessagesByUserIdAndUsernameAndContent",
                query = "SELECT COUNT(m) FROM MessageEntity m WHERE m.receiver.id = :userId " +
                        "AND (:username IS NULL OR m.sender.username LIKE CONCAT('%', :username, '%')) " +
                        "AND (:content IS NULL OR m.content LIKE CONCAT('%', :content, '%'))"),
        @NamedQuery(name = "Message.countSentMessagesByUserIdAndUsernameAndContent",
                query = "SELECT COUNT(m) FROM MessageEntity m WHERE m.sender.id = :userId " +
                        "AND (:username IS NULL OR m.receiver.username LIKE CONCAT('%', :username, '%')) " +
                        "AND (:content IS NULL OR m.content LIKE CONCAT('%', :content, '%'))"),
        @NamedQuery(name = "Message.countUnreadMessagesByUserIdAndUsernameAndContent",
                query = "SELECT COUNT(m) FROM MessageEntity m WHERE m.receiver.id = :userId " +
                        "AND (:username IS NULL OR m.sender.username LIKE CONCAT('%', :username, '%')) " +
                        "AND (:content IS NULL OR m.content LIKE CONCAT('%', :content, '%')) " +
                        "AND m.seen = false")
})
public class MessageEntity extends BaseMessageEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "seen", nullable = false)
    private Boolean seen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private UserEntity receiver;

    public MessageEntity() {
    }

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    public UserEntity getReceiver() {
        return receiver;
    }

    public void setReceiver(UserEntity receiver) {
        this.receiver = receiver;
    }
}
