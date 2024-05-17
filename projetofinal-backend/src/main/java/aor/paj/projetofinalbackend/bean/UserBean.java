package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.TokenDao;
import aor.paj.projetofinalbackend.dao.UserDao;
import aor.paj.projetofinalbackend.entity.TokenEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.security.JwtUtil;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@ApplicationScoped
public class UserBean {

    @EJB
    UserDao userDao;

    @EJB
    TokenDao tokenDao;

    public UserEntity findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    public String createAndSaveToken(UserEntity user) {
        String tokenValue = JwtUtil.generateToken(user.getEmail(), user.getRole());
        LocalDateTime expirationTime = LocalDateTime.ofInstant(
                new Date(System.currentTimeMillis() + JwtUtil.EXPIRATION_TIME).toInstant(),
                ZoneId.systemDefault()
        );

        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setTokenValue(tokenValue);
        tokenEntity.setExpirationTime(expirationTime);
        tokenEntity.setUser(user);

        tokenDao.persist(tokenEntity);

        return tokenValue;
    }
}
