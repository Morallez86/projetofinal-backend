package aor.paj.projetofinalbackend.dto;

import aor.paj.projetofinalbackend.entity.ProjectEntity;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserProjectDto {

    private Long id;
    private UserDto user;
    private boolean isAdmin;

    private ProjectDto project;

    public UserProjectDto() {
    }

    @XmlElement
    public UserDto getUser() {
        return user;
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
    public ProjectDto getProject() {
        return project;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProject(ProjectDto project) {
        this.project = project;
    }
}
