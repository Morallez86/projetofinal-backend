package aor.paj.projetofinalbackend.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * UserPasswordUpdateDto is a Data Transfer Object (DTO) class used for updating a user's password.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@XmlRootElement
public class UserPasswordUpdateDto {

    private String oldPassword;
    private String newPassword;

    /**
     * Default constructor for UserPasswordUpdateDto.
     */
    public UserPasswordUpdateDto() {
    }

    /**
     * Constructor for UserPasswordUpdateDto with parameters for old and new passwords.
     *
     * @param oldPassword the current (old) password of the user.
     * @param newPassword the new password to set for the user.
     */
    public UserPasswordUpdateDto(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    /**
     * Retrieves the old password of the user.
     *
     * @return the old password.
     */
    @XmlElement
    public String getOldPassword() {
        return oldPassword;
    }

    /**
     * Sets the old password of the user.
     *
     * @param oldPassword the old password to set.
     */
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    /**
     * Retrieves the new password that will be set for the user.
     *
     * @return the new password.
     */
    @XmlElement
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * Sets the new password for the user.
     *
     * @param newPassword the new password to set.
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
