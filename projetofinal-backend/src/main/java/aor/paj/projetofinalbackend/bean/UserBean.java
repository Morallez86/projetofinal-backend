package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.TokenDao;
import aor.paj.projetofinalbackend.dao.UserDao;
import aor.paj.projetofinalbackend.dao.WorkplaceDao;
import aor.paj.projetofinalbackend.dto.ProfileDto;
import aor.paj.projetofinalbackend.dto.UserDto;
import aor.paj.projetofinalbackend.entity.TokenEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.mapper.ProfileMapper;
import aor.paj.projetofinalbackend.mapper.UserMapper;
import aor.paj.projetofinalbackend.security.JwtUtil;
import aor.paj.projetofinalbackend.utils.EmailSender;
import aor.paj.projetofinalbackend.utils.EncryptHelper;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@ApplicationScoped
public class UserBean {

    @EJB
    UserDao userDao;

    @EJB
    WorkplaceDao workplaceDao;

    @EJB
    TokenDao tokenDao;

    @Inject
    EmailSender emailSender;

    public String generateToken() {
        String token = "";
        for (int i = 0; i < 10; i++) {
            token += (char) (Math.random() * 26 + 'a');
        }
        return token;
    }

    public UserEntity findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    public UserEntity findUserById(Long userId) {
        return userDao.findUserById(userId);
    }

    public UserEntity findUserByToken(String tokenValue) {
        TokenEntity tokenEntity = tokenDao.findTokenByValue(tokenValue);
        if (tokenEntity == null) {
            return null;
        }
        return tokenEntity.getUser();
    }

    public ProfileDto getProfileDtoById(Long userId) {
        UserEntity userEntity = findUserById(userId);
        if (userEntity == null) {
            return null;
        }
        return ProfileMapper.toDto(userEntity);
    }

    public String createAndSaveToken(UserEntity user) {
        String tokenValue = JwtUtil.generateToken(user.getEmail(), user.getRole(), user.getId());
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

    public void validateUserDto(UserDto userDto) {
        if (userDto.getEmail() == null || userDto.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        if (userDto.getWorkplace() == null || userDto.getWorkplace().isEmpty()) {
            throw new IllegalArgumentException("Workplace is required");
        }
        if (userDto.getFirstName() == null || userDto.getFirstName().isEmpty()) {
            throw new IllegalArgumentException("First name is required");
        }
        if (userDto.getLastName() == null || userDto.getLastName().isEmpty()) {
            throw new IllegalArgumentException("Last name is required");
        }
    }

    public void registerUser(UserDto userDto) {
        if (userDao.findUserByEmail(userDto.getEmail()) != null) {
            throw new IllegalArgumentException("Email is already in use");
        }

        if (userDao.findUserByUsername(userDto.getUsername()) != null) {
            throw new IllegalArgumentException("Username is already in use");
        }
        UserEntity user = UserMapper.toEntity(userDto);
        String emailToken = generateToken();
        user.setPassword(EncryptHelper.encryptPassword(userDto.getPassword()));
        user.setRegistTime(LocalDateTime.now());
        user.setActive(false);
        user.setPending(true);
        user.setRole('C');
        user.setWorkplace(workplaceDao.findWorkplaceByName(userDto.getWorkplace()));
        user.setEmailToken(emailToken);
        userDao.persist(user);

        emailSender.sendConfirmationEmail("testeAor@hotmail.com", user.getEmailToken());
    }

    public void forgotPassword (UserEntity user, String password){
        user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        user.setEmailToken(null);
        user.setPasswordRetrieveTime(null);
        userDao.merge(user);
    }

    public void updateUserProfile(Long userId, ProfileDto profileDto) {
        UserEntity userEntity = userDao.find(userId);

        userEntity.setFirstName(profileDto.getFirstName());
        userEntity.setLastName(profileDto.getLastName());
        userEntity.setUsername(profileDto.getUsername());
        userEntity.setEmail(profileDto.getEmail());
        userEntity.setBiography(profileDto.getBiography());
        userEntity.setVisibility(profileDto.getVisibility());
        userDao.merge(userEntity);
    }

    public String emailTokenCreationForLink (UserEntity user) {
        try {
            String token = generateToken();
            user.setEmailToken(token);
            user.setPasswordRetrieveTime(LocalDateTime.now());
            userDao.merge(user);
            return token;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create email token for user", e);
        }
    }

    public UserEntity getUserByEmailToken(String emailValidationToken) {
        return userDao.findByEmailValidationToken(emailValidationToken);
    }

    public void confirmRegistration (UserEntity user){
        user.setPending(false);
        user.setActive(true);
        user.setEmailToken(null);
        user.setRegistTime(LocalDateTime.now());
        userDao.merge(user);
    }
}
