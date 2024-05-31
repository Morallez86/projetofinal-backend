package aor.paj.projetofinalbackend.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserProjectDto {

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

    public void setUser(UserDto user) {
        this.user = user;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
