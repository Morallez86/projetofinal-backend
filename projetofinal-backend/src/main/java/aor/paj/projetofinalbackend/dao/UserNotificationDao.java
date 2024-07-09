package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.UserNotificationEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;

/**
 * Data Access Object (DAO) for managing UserNotificationEntity entities.
 * Provides methods to perform CRUD operations and retrieve user notifications from the database.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Stateless
public class UserNotificationDao extends AbstractDao<UserNotificationEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs the UserNotificationDao initializing with UserNotificationEntity class.
     */
    public UserNotificationDao() {
        super(UserNotificationEntity.class);
    }

    /**
     * Retrieves a user notification entity based on the ID.
     *
     * @param id The ID of the user notification to retrieve.
     * @return The UserNotificationEntity associated with the specified ID. Returns null if no user notification is found with the specified ID.
     */
    public UserNotificationEntity findUserNotificationById(Long id) {
        try {
            return em.createNamedQuery("UserNotificationEntity.findUserNotificationById", UserNotificationEntity.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Retrieves a user notification entity based on the user ID and notification ID.
     *
     * @param userId The ID of the user associated with the notification.
     * @param notificationId The ID of the notification associated with the user.
     * @return The UserNotificationEntity associated with the specified user ID and notification ID. Returns null if no user notification is found with the specified IDs.
     */
    public UserNotificationEntity findByUserAndNotification(Long userId, Long notificationId) {
        try {
            return em.createNamedQuery("UserNotificationEntity.findByUserAndNotification", UserNotificationEntity.class)
                    .setParameter("userId", userId)
                    .setParameter("notificationId", notificationId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
