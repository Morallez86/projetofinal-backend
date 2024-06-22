package aor.paj.projetofinalbackend.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "chat_message")
@NamedQuery(
        name = "ChatMessageEntity.findByProjectIdTimestampSenderId",
        query = "SELECT c FROM ChatMessageEntity c WHERE c.project.id = :projectId AND c.timestamp = :timestamp AND c.sender.id = :senderId"
)
public class ChatMessageEntity extends BaseMessageEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;


    public ChatMessageEntity() {
    }

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

}
