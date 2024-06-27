package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.TokenDao;
import aor.paj.projetofinalbackend.dao.UserDao;
import aor.paj.projetofinalbackend.entity.TokenEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.NoResultException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@ApplicationScoped
public class TokenBean {

    @EJB
    TokenDao tokenDao;

    @EJB
    UserDao userDao;

    public TokenEntity findTokenByValue(String tokenValue) {
        return tokenDao.findTokenByValue(tokenValue);
    }

    public UserEntity findUserByToken(String tokenValue) {return tokenDao.findUserByTokenValue(tokenValue);}

    public boolean isTokenActive(String token) {
        try {
            TokenEntity tokenEntity = findTokenByValue(token);
            return tokenEntity.isActiveToken();
        } catch (NoResultException e) {
            return false;
        }
    }

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
