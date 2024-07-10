package aor.paj.projetofinalbackend.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entity class representing a token entity stored in the database.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Entity
@Table(name="token")
@NamedQueries({
        @NamedQuery(name = "Token.findTokenByValue", query = "SELECT t FROM TokenEntity t WHERE t.tokenValue = :tokenValue"),
        @NamedQuery(name = "Token.findUserByTokenValue", query = "SELECT t.user FROM TokenEntity t WHERE t.tokenValue = :tokenValue"),
        @NamedQuery(name = "Token.findExpiredTokens", query = "SELECT t FROM TokenEntity t WHERE t.expirationTime < :currentDateTime"),
        @NamedQuery(name = "Token.findTokenByUserId", query = "SELECT t FROM TokenEntity t WHERE t.user.id = :userId")
})
public class TokenEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token_value", nullable = false, unique = true)
    private String tokenValue;

    @Column(name = "expiration_time", nullable = false)
    private LocalDateTime expirationTime;

    @Column(name = "active_token", nullable = false)
    private boolean activeToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    /**
     * Default constructor for TokenEntity.
     */
    public TokenEntity() {
    }

    /**
     * Get the unique identifier of the token.
     *
     * @return The unique identifier of the token.
     */
    public Long getId() {
        return id;
    }

    /**
     * Get the token value.
     *
     * @return The token value.
     */
    public String getTokenValue() {
        return tokenValue;
    }

    /**
     * Set the token value.
     *
     * @param tokenValue The token value to set.
     */
    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    /**
     * Get the expiration time of the token.
     *
     * @return The expiration time of the token.
     */
    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    /**
     * Set the expiration time of the token.
     *
     * @param expirationTime The expiration time to set.
     */
    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }

    /**
     * Get the user associated with the token.
     *
     * @return The user associated with the token.
     */
    public UserEntity getUser() {
        return user;
    }

    /**
     * Set the user associated with the token.
     *
     * @param user The user to set for the token.
     */
    public void setUser(UserEntity user) {
        this.user = user;
    }

    /**
     * Check if the token is active.
     *
     * @return True if the token is active, false otherwise.
     */
    public boolean isActiveToken() {
        return activeToken;
    }

    /**
     * Set whether the token is active.
     *
     * @param activeToken True to activate the token, false to deactivate.
     */
    public void setActiveToken(boolean activeToken) {
        this.activeToken = activeToken;
    }
}
