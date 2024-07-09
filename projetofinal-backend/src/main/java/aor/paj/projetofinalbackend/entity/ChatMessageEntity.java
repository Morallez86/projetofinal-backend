package aor.paj.projetofinalbackend.entity;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * ChatMessageEntity is an entity class representing a chat message within a project.
 * It extends the {@link BaseMessageEntity} to inherit common message properties and adds a relationship to a project.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Entity
@Table(name = "chat_message")
@NamedQuery(
        name = "ChatMessageEntity.findByProjectIdTimestampSenderId",
        query = "SELECT c FROM ChatMessageEntity c WHERE c.project.id = :projectId AND c.timestamp = :timestamp AND c.sender.id = :senderId"
)
public class ChatMessageEntity extends BaseMessageEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The project associated with this chat message.
     * This is a many-to-one relationship, and the project is fetched lazily.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    /**
     * Default constructor for ChatMessageEntity.
     */
    public ChatMessageEntity() {
    }

    /**
     * Retrieves the project associated with this chat message.
     *
     * @return the associated project.
     */
    public ProjectEntity getProject() {
        return project;
    }

    /**
     * Sets the project associated with this chat message.
     *
     * @param project the project to associate with this chat message.
     */
    public void setProject(ProjectEntity project) {
        this.project = project;
    }

}
