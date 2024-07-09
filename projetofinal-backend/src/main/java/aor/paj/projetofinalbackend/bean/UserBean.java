package aor.paj.projetofinalbackend.bean;

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
import aor.paj.projetofinalbackend.utils.EncryptHelper;
import aor.paj.projetofinalbackend.utils.Role;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Application-scoped bean responsible for managing user-related operations.
 * @see UserDao
 * @see WorkplaceDao
 * @see TokenDao
 * @see UserProjectDao
 * @see EmailSender
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@ApplicationScoped
public class UserBean {

    @EJB
    UserDao userDao;

    @EJB
    WorkplaceDao workplaceDao;

    @EJB
    TokenDao tokenDao;

    @EJB
    UserProjectDao userProjectDao;

    @Inject
    EmailSender emailSender;

    /**
     * Generates a random token for emailToken.
     *
     * @return A randomly generated token.
     */
    public String generateToken() {
        String token = "";
        for (int i = 0; i < 10; i++) {
            token += (char) (Math.random() * 26 + 'a');
        }
        return token;
    }

    /**
     * Retrieves a user entity based on their email.
     *
     * @param email The email of the user to find.
     * @return The UserEntity corresponding to the provided email.
     */
    public UserEntity findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    /**
     * Retrieves a user entity based on their ID.
     *
     * @param userId The ID of the user to find.
     * @return The UserEntity corresponding to the provided ID.
     */
    public UserEntity findUserById(Long userId) {
        return userDao.findUserById(userId);
    }

    /**
     * Retrieves the user associated with a token.
     *
     * @param tokenValue The token value to search for.
     * @return The UserEntity associated with the provided token value.
     */
    public UserEntity findUserByToken(String tokenValue) {
        TokenEntity tokenEntity = tokenDao.findTokenByValue(tokenValue);
        if (tokenEntity == null) {
            return null;
        }
        return tokenEntity.getUser();
    }

    /**
     * Retrieves a profile DTO for a user based on their ID.
     *
     * @param userId The ID of the user.
     * @return The ProfileDto for the user.
     */
    @Transactional
    public ProfileDto getProfileDtoById(Long userId) {
        UserEntity userEntity = findUserById(userId);

        if (userEntity == null) {
            return null;
        }
        Hibernate.initialize(userEntity.getInterests());
        Hibernate.initialize(userEntity.getSkills());
        return ProfileMapper.toDto(userEntity);
    }

    /**
     * Creates and saves a token for a user.
     *
     * @param user The user entity for which to create the token.
     * @return The generated token value.
     */
    @Transactional
    public String createAndSaveToken(UserEntity user) {

        Hibernate.initialize(user.getUserProjects());
        // Criar o HashMap para armazenar os timestamps mais recentes dos projetos
        Map<Long, LocalDateTime> projectTimestamps = new HashMap<>();

        for (UserProjectEntity userProject : user.getUserProjects()) {

            Long projectId = userProject.getProject().getId();
            ProjectTimerChat projectTimerChat = userProject.getProjectTimerChat();

            if (projectTimerChat != null) {
                LocalDateTime mostRecentTimestamp = projectTimerChat.getMostRecentTimestamp();
                if (mostRecentTimestamp != null) {
                    projectTimestamps.put(projectId, mostRecentTimestamp);
                }
            }
        }


        String tokenValue = JwtUtil.generateToken(user.getEmail(), user.getRole().getValue(), user.getId(), user.getUsername(),projectTimestamps);
        LocalDateTime expirationTime = LocalDateTime.ofInstant(
                new Date(System.currentTimeMillis() + JwtUtil.EXPIRATION_TIME).toInstant(),
                ZoneId.systemDefault()
        );

        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setTokenValue(tokenValue);
        tokenEntity.setExpirationTime(expirationTime);
        tokenEntity.setActiveToken(true);
        tokenEntity.setUser(user);

        tokenDao.persist(tokenEntity);

        List<UserProjectEntity> userProjectEntities = userProjectDao.findByUserIdActive(user.getId());
        for (UserProjectEntity userProject : userProjectEntities) {
            UserEntity userEntity = userDao.findUserById(userProject.getUser().getId());
            userEntity.setOnline(true);
            userProject.setOnline(true);
            userProjectDao.merge(userProject);
            userDao.merge(userEntity);
        }

        return tokenValue;
    }

    /**
     * Validates a user DTO to ensure all required fields are present.
     *
     * @param userDto The UserDto to validate.
     * @throws IllegalArgumentException if any required field is missing.
     */
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

    /**
     * Registers a new user based on the provided UserDto.
     *
     * @param userDto The UserDto containing user information for registration.
     * @throws IllegalArgumentException if the email or username is already in use.
     */
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
        user.setVisibility(false);
        user.setRole(Role.USER);
        user.setWorkplace(workplaceDao.findWorkplaceByName(userDto.getWorkplace()));
        user.setEmailToken(emailToken);
        userDao.persist(user);

        emailSender.sendConfirmationEmail("testeAor@hotmail.com", user.getEmailToken());
    }

    /**
     * Updates the password for a user who has forgotten their password.
     *
     * @param user     The UserEntity whose password is being updated.
     * @param password The new password.
     */
    public void forgotPassword (UserEntity user, String password){
        user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        user.setEmailToken(null);
        user.setPasswordRetrieveTime(null);
        userDao.merge(user);
    }

    /**
     * Updates the password for a user based on the provided UserPasswordUpdateDto.
     *
     * @param userPasswordUpdateDto The UserPasswordUpdateDto containing old and new passwords.
     * @param token The token associated with the user for authentication.
     * @return true if the password update was successful, false otherwise.
     */
    public boolean updatePassword(UserPasswordUpdateDto userPasswordUpdateDto, String token) {
        // Find the user associated with the token
        UserEntity userEntity = tokenDao.findUserByTokenValue(token);

        if (userEntity != null) {
            // Check if the provided old password matches the current password
            if (BCrypt.checkpw(userPasswordUpdateDto.getOldPassword(), userEntity.getPassword())) {
                // Encrypt the new password
                String encryptedPassword = BCrypt.hashpw(userPasswordUpdateDto.getNewPassword(), BCrypt.gensalt());
                // Set the new encrypted password on the user entity
                userEntity.setPassword(encryptedPassword);
                // Merge the updated user entity
                userDao.merge(userEntity);
                return true;
            } else {
                // If the old password provided does not match the user's current password
                return false;
            }
        }
        // If the user associated with the token is not found
        return false;
    }

    /**
     * Updates the profile information for a user.
     *
     * @param userId The ID of the user whose profile is being updated.
     * @param profileDto The ProfileDto containing updated profile information.
     */
    public void updateUserProfile(Long userId, ProfileDto profileDto) {
        UserEntity userEntity = userDao.find(userId);
        String workplaceName = profileDto.getWorkplace();
        WorkplaceEntity workplaceEntity = workplaceDao.findWorkplaceByName(workplaceName);

        userEntity.setFirstName(profileDto.getFirstName());
        userEntity.setLastName(profileDto.getLastName());
        userEntity.setUsername(profileDto.getUsername());
        userEntity.setBiography(profileDto.getBiography());
        userEntity.setVisibility(profileDto.getVisibility());
        userEntity.setWorkplace(workplaceEntity);
        userDao.merge(userEntity);
    }

    /**
     * Creates an email validation token for a user.
     *
     * @param user The UserEntity for which to create the email validation token.
     * @return The generated email validation token.
     * @throws RuntimeException if failed to create the email token for the user.
     */
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

    /**
     * Retrieves a user entity based on their email validation token.
     *
     * @param emailValidationToken The email validation token to search for.
     * @return The UserEntity associated with the provided email validation token.
     */
    public UserEntity getUserByEmailToken(String emailValidationToken) {
        return userDao.findByEmailValidationToken(emailValidationToken);
    }

    /**
     * Confirms the registration of a user.
     *
     * @param user The UserEntity whose registration is being confirmed.
     */
    public void confirmRegistration (UserEntity user){
        user.setPending(false);
        user.setActive(true);
        user.setEmailToken(null);
        user.setRegistTime(LocalDateTime.now());
        userDao.merge(user);
    }

    /**
     * Retrieves projects associated with a user.
     *
     * @param userId The ID of the user.
     * @param limit  The maximum number of projects to retrieve.
     * @return A set of ProjectDto representing the user's projects.
     */
    @Transactional
    public Set<ProjectDto> getUserProjects(Long userId, int limit) {
        List<ProjectEntity> projects = userProjectDao.findProjectsByUserIdActive(userId);
        if (projects == null || projects.isEmpty()) {
            return null;
        }

        // Always return the first page of projects based on the given limit
        List<ProjectEntity> projectsPerPage = projects.stream()
                .limit(limit)
                .collect(Collectors.toList());

        return projects.stream()
                .map(ProjectMapper::toDto)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * Retrieves the total count of projects associated with a user.
     *
     * @param userId The ID of the user.
     * @return The total number of projects associated with the user.
     */
    public Long getTotalProjectCount(Long userId) {
        return userProjectDao.countProjectsByUserIdActive(userId);
    }

    /**
     * Searches for users based on a query string.
     *
     * @param query The search query.
     * @return A list of UserDto matching the search query.
     */
    public List<UserDto> searchUsersByQuery(String query) {
        List<UserEntity> userEntities = userDao.findUsersByQuery(query);
        if (userEntities == null || userEntities.isEmpty()) {
            return Collections.emptyList();
        }
        return userEntities.stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Retrieves all users.
     *
     * @return A list of all UserDto representing all users.
     */
    public List<UserDto> getAllUsers() {
        List<UserEntity> users = userDao.findAllUsers();
        return users.stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Searches for users based on search criteria.
     *
     * @param searchTerm The search term.
     * @param workplace  The workplace filter.
     * @param skills     The skills filter.
     * @param interests  The interests filter.
     * @return A list of UserDto matching the search criteria.
     */
    public List<UserDto> searchUsers(String searchTerm, String workplace, String skills, String interests) {

        List<UserEntity> users = userDao.searchUsers(searchTerm, workplace, skills, interests);
        if (users == null || users.isEmpty()) {
            return Collections.emptyList();
        }
        return users.stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Removes email tokens that are no longer needed.
     * Email tokens are removed for users whose password stamps are older than an hour.
     */
    public void removeEmailToken () {
        List<UserEntity> expiredEmailTokens = userDao.findAllUsersWithNonNullPasswordStamps(LocalDateTime.now().minusHours(1));
        if(!expiredEmailTokens.isEmpty()){
            for(UserEntity user: expiredEmailTokens){
                user.setEmailToken(null);
                userDao.merge(user);
            }
        }
    }

    /**
     * Updates timers and chats associated with projects for a user.
     *
     * @param token The token associated with the user for authentication.
     * @param mapTimersChat The map of project IDs to their updated timestamps.
     */
    public void updateTimersChat (String token, HashMap<Long, LocalDateTime>mapTimersChat) {
        UserEntity user = tokenDao.findUserByTokenValue(token);
        Set<UserProjectEntity> userProjectEntities = user.getUserProjects();


        for (UserProjectEntity userProject : userProjectEntities) {
            Long projectId = userProject.getProject().getId();


            if (mapTimersChat.containsKey(projectId)) {
                LocalDateTime newTimestamp = mapTimersChat.get(projectId);
                ProjectTimerChat projectTimerChat = userProject.getProjectTimerChat();

                // Adicionar apenas se for diferente do último timestamp na lista
                if (projectTimerChat != null && !projectTimerChat.getTimestamps().isEmpty()) {
                    LocalDateTime lastTimestamp = projectTimerChat.getTimestamps().get(projectTimerChat.getTimestamps().size() - 1);
                    if (!lastTimestamp.equals(newTimestamp)) {
                        projectTimerChat.getTimestamps().add(newTimestamp);
                        userProjectDao.merge(userProject); // Atualiza o UserProjectEntity no banco de dados
                    }
                }
            }
        }
    }

    /**
     * Updates the role of a user.
     *
     * @param userId  The ID of the user whose role is being updated.
     * @param userDto The UserDto containing updated user information including the role.
     */
    public void updateUserRole(Long userId, UserDto userDto) {
        UserEntity user = userDao.findUserById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        user.setRole(Role.fromValue(userDto.getRole()));
        userDao.merge(user);
    }
}
