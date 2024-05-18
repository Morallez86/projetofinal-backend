package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.TokenDao;
import aor.paj.projetofinalbackend.entity.TokenEntity;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TokenBean {

    @EJB
    TokenDao tokenDao;

    public TokenEntity findTokenByValue(String tokenValue) {
        return tokenDao.findTokenByValue(tokenValue);
    }
    public void deleteToken(String tokenValue) {
        TokenEntity tokenEntity = tokenDao.findTokenByValue(tokenValue);
        if (tokenEntity != null) {
            tokenDao.remove(tokenEntity);
        }
    }

}
