package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.utils.Role;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Data Access Object (DAO) for managing UserEntity entities.
 * Provides methods to perform CRUD operations and retrieve users from the database.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Stateless
public class UserDao extends AbstractDao<UserEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs the UserDao initializing with UserEntity class.
     */
    public UserDao() {
        super(UserEntity.class);
    }

    /**
     * Retrieves a user entity based on the email address.
     *
     * @param email The email address of the user to retrieve.
     * @return The UserEntity associated with the specified email address. Returns null if no user is found with the specified email address.
     */
    public UserEntity findUserByEmail(String email) {
        try {
            return em.createNamedQuery("User.findUserByEmail", UserEntity.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Retrieves a user entity based on the username.
     *
     * @param username The username of the user to retrieve.
     * @return The UserEntity associated with the specified username. Returns null if no user is found with the specified username.
     */
    public UserEntity findUserByUsername(String username) {
        try {
            return (UserEntity) em.createNamedQuery("User.findUserByUsername").setParameter("username", username)
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Retrieves a user entity based on the user ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return The UserEntity associated with the specified user ID. Returns null if no user is found with the specified ID.
     */
    public UserEntity findUserById(Long userId) {
        try {
            return em.createNamedQuery("User.findUserById", UserEntity.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Counts the total number of users in the database.
     *
     * @return The total number of users. Returns 0 if no users are found or an error occurs.
     */
    public long countTotalUsers() {
        try {
            return (long) em.createNamedQuery("User.countTotalUsers")
                    .getSingleResult();
        } catch (NoResultException e) {
            return 0; // Return 0 if no users found
        }
    }

    /**
     * Retrieves a user entity based on the email validation token.
     *
     * @param emailToken The email validation token to retrieve the user for.
     * @return The UserEntity associated with the specified email validation token. Returns null if no user is found with the specified token.
     */
    public UserEntity findByEmailValidationToken(String emailToken) {
        try {
            return em.createNamedQuery("User.findUserByEmailValidationToken", UserEntity.class)
                    .setParameter("emailToken", emailToken)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrieves a user entity based on the user ID, initializing the owned projects collection.
     *
     * @param userId The ID of the user to retrieve.
     * @return The UserEntity associated with the specified user ID, with the owned projects collection initialized. Returns null if no user is found with the specified ID.
     */
    public UserEntity findUserByIdWithProjects(Long userId) {
        UserEntity user = em.find(UserEntity.class, userId);
        if (user != null) {
            // Initialize the collection
            Hibernate.initialize(user.getOwnedProjects());
        }
        return user;
    }

    /**
     * Retrieves the total number of projects associated with a user.
     *
     * @param userId The ID of the user to retrieve project count for.
     * @return The total number of projects associated with the specified user ID. Returns 0 if no projects are found for the user or an error occurs.
     */
    public long getTotalProjectCount(Long userId) {
        try {
            return em.createNamedQuery("User.getTotalProjectCount", Long.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return 0; // Return 0 if no projects found for the user
        }
    }

    /**
     * Retrieves a list of users based on a search query.
     *
     * @param query The search query used to find users (searched in username and email).
     * @return A list of UserEntity objects matching the search query. Returns an empty list if no users match the query.
     */
    public List<UserEntity> findUsersByQuery(String query) {
        return em.createNamedQuery("User.findUsersByQuery", UserEntity.class)
                .setParameter("query", "%" + query.toLowerCase() + "%")
                .getResultList();
    }

    /**
     * Retrieves all users from the database.
     *
     * @return A list of all UserEntity objects. Returns null if no users are found or an error occurs.
     */
    public List<UserEntity> findAllUsers() {
        try{
            return em.createNamedQuery("User.findAllUsers", UserEntity.class).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Searches for users based on various criteria.
     *
     * @param searchTerm The search term used to find users (searched in username and email).
     * @param workplace  The workplace information to filter users.
     * @param skills     The skills information to filter users.
     * @param interests  The interests information to filter users.
     * @return A list of UserEntity objects matching the search criteria. Returns an empty list if no users match the criteria.
     */
    public List<UserEntity> searchUsers(String searchTerm, String workplace, String skills, String interests) {
        try {
            return em.createNamedQuery("User.searchUsers", UserEntity.class)
                    .setParameter("searchTerm", searchTerm != null ? "%" + searchTerm.toLowerCase() + "%" : null)
                    .setParameter("workplace", workplace)
                    .setParameter("skills", skills != null && !skills.isEmpty() ? skills : null)
                    .setParameter("interests", interests != null && !interests.isEmpty() ? interests : null)
                    .getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList(); // Return an empty list instead of null
        }
    }

    /**
     * Retrieves users who have non-null password stamps (indicating recently changed passwords).
     *
     * @param cutoffTime The cutoff time used to determine recent password changes.
     * @return A list of UserEntity objects with non-null password stamps. Returns an empty list if no users have recent password changes.
     */
    public List<UserEntity> findAllUsersWithNonNullPasswordStamps(LocalDateTime cutoffTime) {
        try {
            return em.createNamedQuery("User.findAllUsersWithNonNullPasswordStamps", UserEntity.class)
                    .setParameter("cutoffTime", cutoffTime)
                    .getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList(); // Return an empty list if no results
        }
    }

    /**
     * Retrieves users who have the role of admin.
     *
     * @return A list of UserEntity objects with the role of admin. Returns an empty list if no admin users are found.
     */
    public List<UserEntity> findAdmins() {
        try {
            return em.createNamedQuery("User.findAdmins", UserEntity.class)
                    .setParameter("role", Role.ADMIN)
                    .getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }
}
