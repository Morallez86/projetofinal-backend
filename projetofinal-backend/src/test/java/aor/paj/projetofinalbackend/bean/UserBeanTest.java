package aor.paj.projetofinalbackend.bean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import aor.paj.projetofinalbackend.dao.TokenDao;
import aor.paj.projetofinalbackend.dao.UserDao;
import aor.paj.projetofinalbackend.dao.UserProjectDao;
import aor.paj.projetofinalbackend.dao.WorkplaceDao;
import aor.paj.projetofinalbackend.dto.ProfileDto;
import aor.paj.projetofinalbackend.dto.ProjectDto;
import aor.paj.projetofinalbackend.dto.UserDto;
import aor.paj.projetofinalbackend.dto.UserPasswordUpdateDto;
import aor.paj.projetofinalbackend.entity.*;
import aor.paj.projetofinalbackend.mapper.ProfileMapper;
import aor.paj.projetofinalbackend.mapper.ProjectMapper;
import aor.paj.projetofinalbackend.mapper.UserMapper;
import aor.paj.projetofinalbackend.security.JwtUtil;
import aor.paj.projetofinalbackend.utils.EmailSender;
import aor.paj.projetofinalbackend.utils.ProjectStatus;
import aor.paj.projetofinalbackend.utils.Role;
import jakarta.ejb.EJB;
import jakarta.ejb.Local;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDateTime;
import java.util.*;


public class UserBeanTest {

    private UserDao userDao;
    private WorkplaceDao workplaceDao;
    private TokenDao tokenDao;
    private UserProjectDao userProjectDao;
    private EmailSender emailSender;


    private UserBean userBean;

    @BeforeEach
    public void setUp() {
        userDao = mock(UserDao.class);
        workplaceDao = mock(WorkplaceDao.class);
        tokenDao = mock(TokenDao.class);
        userProjectDao = mock(UserProjectDao.class);
        emailSender = mock(EmailSender.class);

        userBean = new UserBean();
        userBean.userDao = userDao;
        userBean.workplaceDao = workplaceDao;
        userBean.tokenDao = tokenDao;
        userBean.userProjectDao = userProjectDao;
        userBean.emailSender = emailSender;
    }

    @Test
    public void testGenerateToken() {
        String token = userBean.generateToken();
        assertNotNull(token);
        assertEquals(10, token.length());
    }

    @Test
    public void testFindUserByEmail() {
        String email = "test@example.com";
        UserEntity mockUser = new UserEntity();
        mockUser.setEmail(email);
        when(userDao.findUserByEmail(email)).thenReturn(mockUser);

        UserEntity result = userBean.findUserByEmail(email);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(userDao, times(1)).findUserByEmail(email);
    }

    @Test
    public void testFindUserById() {
        Long userId = 1L;
        UserEntity mockUser = new UserEntity();
        mockUser.setId(userId);
        when(userDao.findUserById(userId)).thenReturn(mockUser);

        UserEntity result = userBean.findUserById(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        verify(userDao, times(1)).findUserById(userId);
    }

    @Test
    public void testFindUserByToken() {
        String tokenValue = "abc123";
        TokenEntity mockToken = new TokenEntity();
        mockToken.setTokenValue(tokenValue);
        UserEntity mockUser = new UserEntity();
        mockUser.setId(1L);
        mockUser.setEmail("elias@gmail.com");
        mockToken.setUser(mockUser);
        when(tokenDao.findTokenByValue(tokenValue)).thenReturn(mockToken);

        UserEntity result = userBean.findUserByToken(tokenValue);

        assertNotNull(result);
        assertEquals(mockUser.getEmail(), result.getEmail());
        verify(tokenDao, times(1)).findTokenByValue(tokenValue);
    }

    @Test
    public void testGetProfileDtoById() {
        Long userId = 1L;
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setRole(Role.ADMIN); // Defina um papel válido para evitar NullPointerException

        ProfileDto mockProfileDto = ProfileMapper.toDto(userEntity);

        when(userDao.findUserById(userId)).thenReturn(userEntity);

        ProfileDto result = userBean.getProfileDtoById(userId);

        assertEquals(mockProfileDto.getId(), result.getId());
        assertEquals(mockProfileDto.getUsername(), result.getUsername());
        assertEquals(mockProfileDto.getFirstName(), result.getFirstName());
        assertEquals(mockProfileDto.getLastName(), result.getLastName());
        assertEquals(mockProfileDto.getEmail(), result.getEmail());
        assertEquals(mockProfileDto.getBiography(), result.getBiography());
        assertEquals(mockProfileDto.getVisibility(), result.getVisibility());
        assertEquals(mockProfileDto.getWorkplace(), result.getWorkplace());
        assertEquals(mockProfileDto.getInterests(), result.getInterests());
        assertEquals(mockProfileDto.getSkills(), result.getSkills());
        verify(userDao, times(1)).findUserById(userId);
    }

    @Test
    public void testCreateAndSaveToken() {
        UserEntity user = new UserEntity();
        String tokenValue = "abc123";
        when(tokenDao.findTokenByValue(tokenValue)).thenReturn(null);
        doNothing().when(tokenDao).persist(any(TokenEntity.class));
        user.setRole(Role.ADMIN);
        String tokenValueFinal = JwtUtil.generateToken(user.getEmail(), user.getRole().getValue(), user.getId(), user.getUsername(), null);

        String result = userBean.createAndSaveToken(user);

        assertNotNull(result);

        // Verifica se ambos os tokens começam com a mesma sequência inicial
        assertTrue(result.startsWith("eyJhbGciOiJIUzI1NiJ9.eyJ"));
        assertTrue(tokenValueFinal.startsWith("eyJhbGciOiJIUzI1NiJ9.eyJ"));

        verify(tokenDao, times(1)).persist(any(TokenEntity.class));
        verify(userProjectDao, times(1)).findByUserIdActive(user.getId());
    }


    @Test
    public void testValidateUserDto_ValidDto() {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setPassword("password");
        userDto.setWorkplace("Workplace");
        userDto.setFirstName("John");
        userDto.setLastName("Doe");

        assertDoesNotThrow(() -> userBean.validateUserDto(userDto));
    }

    @Test
    public void testValidateUserDto_InvalidDto() {
        UserDto userDto = new UserDto();
        userDto.setEmail("");
        userDto.setPassword("");
        userDto.setWorkplace("");
        userDto.setFirstName("");
        userDto.setLastName("");

        assertThrows(IllegalArgumentException.class, () -> userBean.validateUserDto(userDto));
    }

    @Test
    public void testRegisterUser() {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setPassword("password");
        userDto.setWorkplace("Workplace");
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        when(userDao.findUserByEmail(userDto.getEmail())).thenReturn(null);
        when(userDao.findUserByUsername(userDto.getUsername())).thenReturn(null);
        when(workplaceDao.findWorkplaceByName(userDto.getWorkplace())).thenReturn(new WorkplaceEntity());

        assertDoesNotThrow(() -> userBean.registerUser(userDto));
        verify(userDao, times(1)).persist(any(UserEntity.class));
        verify(emailSender, times(1)).sendConfirmationEmail(anyString(), anyString());
    }

    @Test
    public void testForgotPassword() {
        UserEntity user = new UserEntity();
        String newPassword = "newpassword";
        user.setPassword("oldpassword");

        userBean.forgotPassword(user, newPassword);

        assertNotEquals("oldpassword", user.getPassword());
        verify(userDao, times(1)).merge(user);
    }

    @Test
    public void testUpdatePassword_CorrectOldPassword() {
        String token = "abc123";
        UserPasswordUpdateDto userPasswordUpdateDto = new UserPasswordUpdateDto();
        userPasswordUpdateDto.setOldPassword("oldpassword");
        userPasswordUpdateDto.setNewPassword("newpassword");
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword(BCrypt.hashpw("oldpassword", BCrypt.gensalt()));
        when(tokenDao.findUserByTokenValue(token)).thenReturn(userEntity);

        boolean result = userBean.updatePassword(userPasswordUpdateDto, token);

        assertTrue(result);
        assertNotEquals("oldpassword", userEntity.getPassword());
        verify(userDao, times(1)).merge(userEntity);
    }

    @Test
    public void testUpdatePassword_IncorrectOldPassword() {
        String token = "abc123";
        UserPasswordUpdateDto userPasswordUpdateDto = new UserPasswordUpdateDto();
        userPasswordUpdateDto.setOldPassword("wrongpassword");
        userPasswordUpdateDto.setNewPassword("newpassword");
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword(BCrypt.hashpw("oldpassword", BCrypt.gensalt()));
        when(tokenDao.findUserByTokenValue(token)).thenReturn(userEntity);

        boolean result = userBean.updatePassword(userPasswordUpdateDto, token);

        assertFalse(result);
        verify(userDao, never()).merge(userEntity);
    }

    @Test
    public void testUpdateUserProfile() {
        Long userId = 1L;
        ProfileDto profileDto = new ProfileDto();
        profileDto.setFirstName("John");
        profileDto.setLastName("Doe");
        profileDto.setUsername("johndoe");
        profileDto.setBiography("Lorem ipsum");
        profileDto.setVisibility(true);
        profileDto.setWorkplace("Workplace");

        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        when(userDao.find(userId)).thenReturn(userEntity);
        when(workplaceDao.findWorkplaceByName(profileDto.getWorkplace())).thenReturn(new WorkplaceEntity());

        userBean.updateUserProfile(userId, profileDto);

        assertEquals("John", userEntity.getFirstName());
        assertEquals("Doe", userEntity.getLastName());
        assertEquals("johndoe", userEntity.getUsername());
        assertEquals("Lorem ipsum", userEntity.getBiography());
        assertTrue(userEntity.getVisibility());
        assertNotNull(userEntity.getWorkplace());
        verify(userDao, times(1)).merge(userEntity);
    }

    @Test
    public void testEmailTokenCreationForLink() {
        UserEntity user = new UserEntity();

        String result = userBean.emailTokenCreationForLink(user);

        assertNotNull(user.getEmailToken());
        assertNotNull(user.getPasswordRetrieveTime());
        assertEquals(user.getEmailToken(), result);
        verify(userDao, times(1)).merge(user);
    }

    @Test
    public void testGetUserByEmailToken() {
        String emailValidationToken = "abc123";
        UserEntity mockUser = new UserEntity();
        when(userDao.findByEmailValidationToken(emailValidationToken)).thenReturn(mockUser);

        UserEntity result = userBean.getUserByEmailToken(emailValidationToken);

        assertNotNull(result);
        assertEquals(mockUser, result);
        verify(userDao, times(1)).findByEmailValidationToken(emailValidationToken);
    }

    @Test
    public void testConfirmRegistration() {
        UserEntity user = new UserEntity();

        userBean.confirmRegistration(user);

        assertFalse(user.getPending());
        assertTrue(user.getActive());
        assertNull(user.getEmailToken());
        assertNotNull(user.getRegistTime());
        verify(userDao, times(1)).merge(user);
    }

    @Test
    public void testGetUserProjects() {
        Long userId = 1L;
        int limit = 5;
        List<ProjectEntity> projects = new ArrayList<>();
        ProjectEntity project = new ProjectEntity();
        project.setStatus(ProjectStatus.READY);
        project.setOwner(new UserEntity());
        projects.add(project);
        when(userProjectDao.findProjectsByUserIdActive(userId)).thenReturn(projects);

        Set<ProjectDto> result = userBean.getUserProjects(userId, limit);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testGetTotalProjectCount() {
        Long userId = 1L;
        when(userProjectDao.countProjectsByUserIdActive(userId)).thenReturn(5L);

        Long result = userBean.getTotalProjectCount(userId);

        assertNotNull(result);
        assertEquals(5L, result);
    }

    @Test
    public void testSearchUsersByQuery() {
        String query = "John";
        List<UserEntity> users = new ArrayList<>();
        UserEntity user = new UserEntity();
        user.setRole(Role.ADMIN);
        users.add(user);
        when(userDao.findUsersByQuery(query)).thenReturn(users);

        List<UserDto> result = userBean.searchUsersByQuery(query);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testGetAllUsers() {
        List<UserEntity> users = new ArrayList<>();
        UserEntity user = new UserEntity();
        user.setRole(Role.ADMIN);
        users.add(user);
        when(userDao.findAllUsers()).thenReturn(users);

        List<UserDto> result = userBean.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testSearchUsers() {
        String searchTerm = "John";
        String workplace = "Workplace";
        String skills = "Java";
        String interests = "Programming";
        List<UserEntity> users = new ArrayList<>();
        UserEntity user = new UserEntity();
        user.setRole(Role.ADMIN);
        users.add(user);
        when(userDao.searchUsers(searchTerm, workplace, skills, interests)).thenReturn(users);

        List<UserDto> result = userBean.searchUsers(searchTerm, workplace, skills, interests);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testRemoveEmailToken() {
        UserEntity user = new UserEntity();
        List<UserEntity> expiredEmailTokens = new ArrayList<>();
        expiredEmailTokens.add(user);
        when(userDao.findAllUsersWithNonNullPasswordStamps(any(LocalDateTime.class))).thenReturn(expiredEmailTokens);

        userBean.removeEmailToken();

        assertNull(user.getEmailToken());
        verify(userDao, times(1)).merge(user);
    }

    @Test
    public void testUpdateUserRole() {
        Long userId = 1L;
        UserDto userDto = new UserDto();
        userDto.setRole(200);
        UserEntity user = new UserEntity();
        user.setId(userId);

        when(userDao.findUserById(userId)).thenReturn(user);

        userBean.updateUserRole(userId, userDto);

        assertEquals(Role.ADMIN, user.getRole());
        verify(userDao, times(1)).merge(user);
    }
}
