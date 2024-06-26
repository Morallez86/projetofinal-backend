package aor.paj.projetofinalbackend.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name="token")
@NamedQueries({
        @NamedQuery(name = "Token.findTokenByValue", query = "SELECT t FROM TokenEntity t WHERE t.tokenValue = :tokenValue"),
        @NamedQuery(name = "Token.findUserByTokenValue", query = "SELECT t.user FROM TokenEntity t WHERE t.tokenValue = :tokenValue"),
        @NamedQuery(name = "Token.findExpiredTokens", query = "SELECT t FROM TokenEntity t WHERE t.expirationTime < :currentDateTime"),
        @NamedQuery(name = "Token.findTokenByUserId", query = "SELECT t FROM TokenEntity t WHERE t.user.id = :userId")
})
public class TokenEntity implements Serializable{

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

    public TokenEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public boolean isActiveToken() {
        return activeToken;
    }

    public void setActiveToken(boolean activeToken) {
        this.activeToken = activeToken;
    }
}
