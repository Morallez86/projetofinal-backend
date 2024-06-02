package aor.paj.projetofinalbackend.dto;

import jakarta.xml.bind.annotation.XmlElement;

import java.time.LocalDateTime;

public class ProjectHistoryDto {
    private Long id;
    private String newDescription;

    private int type;

    private LocalDateTime timestamp;

    private UserDto user;

    public ProjectHistoryDto() {
    }

    @XmlElement
    public Long getId() {
        return id;
    }
    @XmlElement
    public String getNewDescription() {
        return newDescription;
    }
    @XmlElement
    public int getType() {
        return type;
    }
    @XmlElement
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    @XmlElement
    public UserDto getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNewDescription(String newDescription) {
        this.newDescription = newDescription;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

}
