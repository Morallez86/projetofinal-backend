package aor.paj.projetofinalbackend.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * UserCredentials is a Data Transfer Object (DTO) class representing user authentication credentials.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@XmlRootElement
public class UserCredentials {
    private String email;
    private String password;

    /**
     * Retrieves the email address associated with the user credentials.
     *
     * @return the email address.
     */
    @XmlElement
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address for the user credentials.
     *
     * @param email the email address to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the password associated with the user credentials.
     *
     * @return the password.
     */
    @XmlElement
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for the user credentials.
     *
     * @param password the password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
