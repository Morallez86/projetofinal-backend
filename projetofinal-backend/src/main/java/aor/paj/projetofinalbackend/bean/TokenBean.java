package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.TokenDao;
import aor.paj.projetofinalbackend.entity.TokenEntity;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.NoResultException;

@ApplicationScoped
public class TokenBean {

    @EJB
    TokenDao tokenDao;

    public TokenEntity findTokenByValue(String tokenValue) {
        return tokenDao.findTokenByValue(tokenValue);
    }
    public boolean isTokenActive(String token) {
        try {
            System.out.println("111111111111111");
            TokenEntity tokenEntity = findTokenByValue(token);
            System.out.println("2222222222222");
            System.out.println(tokenEntity.isActiveToken());
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

}
