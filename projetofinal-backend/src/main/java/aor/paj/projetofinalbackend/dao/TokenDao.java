package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.TokenEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for managing TokenEntity entities.
 * Provides methods to perform CRUD operations and retrieve tokens from the database.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Stateless
public class TokenDao extends AbstractDao<TokenEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs the TokenDao initializing with TokenEntity class.
     */
    public TokenDao() {
        super(TokenEntity.class);
    }

    /**
     * Retrieves a token entity based on its value.
     *
     * @param tokenValue The value of the token to retrieve.
     * @return The TokenEntity associated with the specified token value. Returns null if no token is found with the specified value.
     */
    public TokenEntity findTokenByValue(String tokenValue) {
        try {
            return em.createNamedQuery("Token.findTokenByValue", TokenEntity.class)
                    .setParameter("tokenValue", tokenValue)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Retrieves the user associated with a token value.
     *
     * @param tokenValue The value of the token to retrieve the user for.
     * @return The UserEntity associated with the specified token value. Returns null if no user is found with the specified token value.
     */
    public UserEntity findUserByTokenValue(String tokenValue) {
        try {
            return em.createNamedQuery("Token.findUserByTokenValue", UserEntity.class)
                    .setParameter("tokenValue", tokenValue)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // Token not found
        }
    }

    /**
     * Retrieves all expired tokens based on the current date and time.
     *
     * @param currentDateTime The current date and time used to determine expired tokens.
     * @return A list of TokenEntity objects representing expired tokens.
     */
    public List<TokenEntity> findExpiredTokens(LocalDateTime currentDateTime) {
        return em.createNamedQuery("Token.findExpiredTokens", TokenEntity.class)
                .setParameter("currentDateTime", currentDateTime)
                .getResultList();
    }

    /**
     * Retrieves all tokens associated with a specific user ID.
     *
     * @param userId The ID of the user to retrieve tokens for.
     * @return A list of TokenEntity objects associated with the specified user ID. Returns an empty list if no tokens are found or an error occurs.
     */
    public List<TokenEntity> findTokensByUserId(Long userId) {
        try {
            return em.createNamedQuery("Token.findTokenByUserId", TokenEntity.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
