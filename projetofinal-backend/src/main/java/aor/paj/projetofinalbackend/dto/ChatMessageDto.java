package aor.paj.projetofinalbackend.dto;

import aor.paj.projetofinalbackend.utils.CustomLocalDateTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.xml.bind.annotation.XmlElement;

import java.time.LocalDateTime;

public class ChatMessageDto {
    private Long id;

    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timestamp;

    private String content;

    private UserDto sender;

    private Long projectId;

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
    public Long getProjectId() {
        return projectId;
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

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
