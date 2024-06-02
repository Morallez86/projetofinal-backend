package aor.paj.projetofinalbackend.dto;

import jakarta.xml.bind.annotation.XmlElement;

import java.time.LocalDateTime;

public class ChatMessageDto {
    private Long id;

    private LocalDateTime timestamp;

    private String content;

    private UserDto sender;

    private ProjectDto project;

    public ChatMessageDto() {
    }


    @XmlElement
    public Long getId() {
        return id;
    }
    @XmlElement
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    @XmlElement
    public String getContent() {
        return content;
    }
    @XmlElement
    public UserDto getSender() {
        return sender;
    }
    @XmlElement
    public ProjectDto getProject() {
        return project;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSender(UserDto sender) {
        this.sender = sender;
    }

    public void setProject(ProjectDto project) {
        this.project = project;
    }
}
