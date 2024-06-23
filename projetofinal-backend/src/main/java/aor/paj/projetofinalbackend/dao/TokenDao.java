package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.TokenEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class TokenDao extends AbstractDao<TokenEntity> {

    private static final long serialVersionUID = 1L;

    public TokenDao() {
        super(TokenEntity.class);
    }

    public TokenEntity findTokenByValue(String tokenValue) {
        try {
            return em.createNamedQuery("Token.findTokenByValue", TokenEntity.class)
                    .setParameter("tokenValue", tokenValue)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public UserEntity findUserByTokenValue(String tokenValue) {
        try {
            return em.createNamedQuery("Token.findUserByTokenValue", UserEntity.class)
                    .setParameter("tokenValue", tokenValue)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // Token not found
        }
    }

    public List<TokenEntity> findExpiredTokens(LocalDateTime currentDateTime) {
        return em.createNamedQuery("Token.findExpiredTokens", TokenEntity.class)
                .setParameter("currentDateTime", currentDateTime)
                .getResultList();
    }

    public TokenEntity findTokenByUserId(Long userId) {
        try {
            return em.createNamedQuery("Token.findTokenByUserId", TokenEntity.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
