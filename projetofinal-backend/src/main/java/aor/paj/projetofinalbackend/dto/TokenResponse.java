package aor.paj.projetofinalbackend.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * TokenResponse is a Data Transfer Object (DTO) class representing the value of a token.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@XmlRootElement
public class TokenResponse {
    private String token;

    /**
     * Constructs a TokenResponse object with the provided token string.
     *
     * @param token the token string to encapsulate.
     */
    public TokenResponse(String token) {
        this.token = token;
    }

    /**
     * Retrieves the token string encapsulated in this TokenResponse object.
     *
     * @return the token string.
     */
    @XmlElement
    public String getToken() {
        return token;
    }

    /**
     * Sets the token string for this TokenResponse object.
     *
     * @param token the token string to set.
     */
    public void setToken(String token) {
        this.token = token;
    }
}