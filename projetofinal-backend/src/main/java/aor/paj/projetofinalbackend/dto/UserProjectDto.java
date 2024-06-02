package aor.paj.projetofinalbackend.dto;

import aor.paj.projetofinalbackend.entity.ProjectEntity;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserProjectDto {

    private Long id;
    private UserDto user;
    private boolean isAdmin;

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


    public void setUser(UserDto user) {
        this.user = user;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    public void setId(Long id) {
        this.id = id;
    }


}
