package aor.paj.projetofinalbackend.pojo;

/**
 * POJO class representing a confirmation request containing a token and a password.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public class ConfirmationRequest {
    private String token;
    private String password;

    /**
     * Get the token associated with the confirmation request.
     *
     * @return The token string.
     */
    public String getToken() {
        return token;
    }

    /**
     * Set the token for the confirmation request.
     *
     * @param token The token string to set.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Get the password associated with the confirmation request.
     *
     * @return The password string.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password for the confirmation request.
     *
     * @param password The password string to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}


