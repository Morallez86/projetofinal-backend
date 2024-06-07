package aor.paj.projetofinalbackend.dto;

import aor.paj.projetofinalbackend.entity.ProjectEntity;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserProjectDto {

    private Long id;
    private Long userId;
    private boolean isAdmin;
    private Long projectId;
    private String username;


    public UserProjectDto() {
    }

    @XmlElement
    public Long getUserId() {
        return userId;
    }

    @XmlElement

    public boolean isAdmin() {
        return isAdmin;
    }



    @XmlElement
    public Long getId() {
        return id;
    }

    @XmlElement
    public Long getProjectId() {
        return projectId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
