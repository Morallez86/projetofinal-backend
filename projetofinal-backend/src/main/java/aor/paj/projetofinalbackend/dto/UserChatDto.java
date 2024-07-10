package aor.paj.projetofinalbackend.dto;

import jakarta.xml.bind.annotation.XmlElement;

/**
 * UserChatDto is a Data Transfer Object (DTO) class representing a user for chat-related operations.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public class UserChatDto {

    private Long id;
    private String username;
    private Boolean online;

    /**
     * Retrieves the ID of the user.
     *
     * @return the ID of the user.
     */
    @XmlElement
    public Long getId() {
        return id;
    }

    /**
     * Retrieves the username of the user.
     *
     * @return the username of the user.
     */
    @XmlElement
    public String getUsername() {
        return username;
    }

    /**
     * Retrieves the online status of the user.
     *
     * @return true if the user is online, false otherwise.
     */
    @XmlElement
    public Boolean getOnline() {
        return online;
    }

    /**
     * Sets the ID of the user.
     *
     * @param id the ID of the user to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Sets the username of the user.
     *
     * @param username the username of the user to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the online status of the user.
     *
     * @param online the online status of the user to set.
     */
    public void setOnline(Boolean online) {
        this.online = online;
    }
}
