package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.ProjectEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.entity.UserProjectEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;

import java.util.Collections;
import java.util.List;

/**
 * Data Access Object (DAO) for managing UserProjectEntity entities.
 * Provides methods to perform CRUD operations and retrieve user-project relationships from the database.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Stateless
public class UserProjectDao extends AbstractDao<UserProjectEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs the UserProjectDao initializing with UserProjectEntity class.
     */
    public UserProjectDao() {
        super(UserProjectEntity.class);
    }

    /**
     * Retrieves a user-project relationship entity based on the user ID and project ID where the project is active.
     *
     * @param userId The ID of the user associated with the project.
     * @param projectId The ID of the project associated with the user.
     * @return The UserProjectEntity associated with the specified user ID and project ID. Returns null if no user-project relationship is found with the specified IDs.
     */
    public UserProjectEntity findByUserAndProjectActive(Long userId, Long projectId) {
        try {
            return em.createNamedQuery("UserProjectEntity.findByUserAndProjectActive", UserProjectEntity.class)
                    .setParameter("userId", userId)
                    .setParameter("projectId", projectId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Retrieves a list of active projects associated with a user based on the user ID.
     *
     * @param userId The ID of the user associated with the projects.
     * @return A list of ProjectEntity objects representing active projects associated with the user. Returns an empty list if no active projects are found for the user.
     */
    public List<ProjectEntity> findProjectsByUserIdActive(Long userId) {
        try {
            return em.createNamedQuery("UserProjectEntity.findProjectsByUserIdActive", ProjectEntity.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Counts the number of active projects associated with a user based on the user ID.
     *
     * @param userId The ID of the user associated with the projects.
     * @return The number of active projects associated with the user. Returns 0 if no active projects are found for the user.
     */
    public Long countProjectsByUserIdActive(Long userId) {
        try {
            return em.createNamedQuery("UserProjectEntity.countProjectsByUserIdActive", Long.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return 0L; // Return 0 if no results found
        }
    }

    /**
     * Retrieves a list of user-project relationship entities based on the user ID where the project is active.
     *
     * @param userId The ID of the user associated with the projects.
     * @return A list of UserProjectEntity objects representing user-project relationships where the project is active.
     */
    public List<UserProjectEntity> findByUserIdActive(Long userId) {
        return em.createNamedQuery("UserProjectEntity.findByUserIdActive", UserProjectEntity.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    /**
     * Retrieves a list of administrators (users with admin role) associated with a project based on the project ID.
     *
     * @param projectId The ID of the project associated with the administrators.
     * @return A list of UserEntity objects representing administrators associated with the project. Returns an empty list if no administrators are found for the project.
     */
    public List<UserEntity> findAdminsByProjectIdActive(Long projectId) {
        try {
            return em.createNamedQuery("UserProjectEntity.findAdminsByProjectIdActive", UserEntity.class)
                    .setParameter("projectId", projectId)
                    .getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

    /**
     * Counts the number of active users (users associated with active projects) for a given project ID.
     *
     * @param projectId The ID of the project associated with the active users.
     * @return The number of active users associated with the project. Returns 0 if no active users are found for the project.
     */
    public Long countActiveUsersByProjectId(Long projectId) {
        try {
            return em.createNamedQuery("UserProjectEntity.countActiveUsersByProjectId", Long.class)
                    .setParameter("projectId", projectId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return 0L;
        }
    }
}

