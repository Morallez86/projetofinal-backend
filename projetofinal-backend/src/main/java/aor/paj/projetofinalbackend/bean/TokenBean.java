package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.TokenDao;
import aor.paj.projetofinalbackend.entity.TokenEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.NoResultException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Application-scoped bean responsible for managing tokens and their lifecycle.
 * @see TokenDao
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@ApplicationScoped
public class TokenBean {

    @EJB
    TokenDao tokenDao;

    /**
     * Retrieves a token entity based on its value.
     *
     * @param tokenValue The token value to search for.
     * @return The TokenEntity corresponding to the provided token value.
     */
    public TokenEntity findTokenByValue(String tokenValue) {
        return tokenDao.findTokenByValue(tokenValue);
    }

    /**
     * Retrieves the user associated with a token.
     *
     * @param tokenValue The token value to search for.
     * @return The UserEntity associated with the provided token value.
     */
    public UserEntity findUserByToken(String tokenValue) {return tokenDao.findUserByTokenValue(tokenValue);}

    /**
     * Checks if a token is currently active.
     *
     * @param token The token to check.
     * @return true if the token is active, false otherwise.
     */
    public boolean isTokenActive(String token) {
        try {
            TokenEntity tokenEntity = findTokenByValue(token);
            return tokenEntity.isActiveToken();
        } catch (NoResultException e) {
            return false;
        }
    }

    /**
     * Deactivates a token.
     *
     * @param token The token to deactivate.
     * @return true if deactivation was successful, false if the token was not found.
     */
    public boolean deactivateToken(String token) {

        try {
            TokenEntity tokenEntity = findTokenByValue(token);
            tokenEntity.setActiveToken(false);
            tokenDao.merge(tokenEntity);

            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    /**
     * Removes expired tokens from the database.
     * Tokens that have expired are marked as inactive.
     */
    public void removeExpiredTokens() {
        List<TokenEntity> expiredTokens = tokenDao.findExpiredTokens(LocalDateTime.now());
        if (!expiredTokens.isEmpty()) {
            for (TokenEntity token : expiredTokens) {
                token.setActiveToken(false);
                tokenDao.merge(token);
            }
        }
    }
}
