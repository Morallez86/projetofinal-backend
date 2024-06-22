package aor.paj.projetofinalbackend.dto;

import jakarta.xml.bind.annotation.XmlElement;

public class UserChatDto {

    private Long id;

    private String username;

    private Boolean online;

    public UserChatDto() {
    }

    @XmlElement
    public Long getId() {
        return id;
    }

    @XmlElement
    public String getUsername() {
        return username;
    }

    @XmlElement
    public Boolean getOnline() {
        return online;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }
}
