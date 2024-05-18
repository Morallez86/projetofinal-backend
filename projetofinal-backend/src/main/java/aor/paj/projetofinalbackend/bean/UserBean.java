package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.TokenDao;
import aor.paj.projetofinalbackend.dao.UserDao;
import aor.paj.projetofinalbackend.dto.UserDto;
import aor.paj.projetofinalbackend.entity.TokenEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.mapper.UserMapper;
import aor.paj.projetofinalbackend.security.JwtUtil;
import aor.paj.projetofinalbackend.utils.EncryptHelper;
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

    public void registerUser(UserDto userDto) {
        if (userDao.findUserByEmail(userDto.getEmail()) != null) {
            throw new IllegalArgumentException("Email is already in use");
        }

        if (userDao.findUserByUsername(userDto.getUsername()) != null) {
            throw new IllegalArgumentException("Username is already in use");
        }
        UserEntity user = UserMapper.toEntity(userDto);
        user.setPassword(EncryptHelper.encryptPassword(userDto.getPassword()));
        user.setRegistTime(LocalDateTime.now());
        userDao.persist(user);
    }
}
