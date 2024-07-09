package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.NotificationEntity;
import aor.paj.projetofinalbackend.utils.NotificationType;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;

import java.util.List;

/**
 * Data Access Object (DAO) for managing NotificationEntity entities.
 * Provides methods to perform CRUD operations and retrieve notifications from the database.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Stateless
public class NotificationDao extends AbstractDao<NotificationEntity> {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs the NotificationDao initializing with NotificationEntity class.
     */
    public NotificationDao() {
        super(NotificationEntity.class);
    }

    /**
     * Retrieves a notification by its ID.
     *
     * @param id The ID of the notification to retrieve.
     * @return The NotificationEntity object with the specified ID, or null if not found.
     */
    public NotificationEntity findNotificationById(Long id) {
        try {
            return em.createNamedQuery("NotificationEntity.findNotificationById", NotificationEntity.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Retrieves notifications by user ID, type, and 'seen' status with pagination.
     *
     * @param userId The ID of the user whose notifications are to be retrieved.
     * @param type The type of notifications to retrieve (e.g., MESSAGE, ALERT).
     * @param seen The 'seen' status of notifications (true for seen, false for unseen).
     * @param offset The offset for pagination.
     * @param limit The maximum number of notifications to retrieve.
     * @return A list of NotificationEntity objects that match the criteria.
     */
    public List<NotificationEntity> findByUserIdAndTypeAndSeen(Long userId, NotificationType type, Boolean seen, int offset, int limit) {
        try {
            return em.createNamedQuery("Notification.findByUserIdAndTypeAndSeen", NotificationEntity.class)
                    .setParameter("userId", userId)
                    .setParameter("type", type)
                    .setParameter("seen", seen)
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Counts notifications by user ID, type, and 'seen' status.
     *
     * @param userId The ID of the user whose notifications are to be counted.
     * @param type The type of notifications to count (e.g., MESSAGE, ALERT).
     * @param seen The 'seen' status of notifications (true for seen, false for unseen).
     * @return The number of notifications that match the criteria.
     */
    public int countByUserIdAndTypeAndSeen(Long userId, NotificationType type, Boolean seen) {
        try {
            return ((Number) em.createNamedQuery("Notification.countByUserIdAndTypeAndSeen")
                    .setParameter("userId", userId)
                    .setParameter("type", type)
                    .setParameter("seen", seen)
                    .getSingleResult()).intValue();
        } catch (NoResultException e) {
            return 0;
        }
    }

    /**
     * Updates the 'seen' status of notifications by user ID and notification IDs.
     *
     * @param userId The ID of the user whose notifications are to be updated.
     * @param notificationIds  The list of notification IDs to update.
     * @param newStatus The new 'seen' status to set for the notifications.
     */
    public void updateSeenStatusByUserIdAndIds(Long userId, List<Long> notificationIds, boolean newStatus) {
        em.createNamedQuery("Notification.updateSeenStatusByUserIdAndIds")
                .setParameter("seen", newStatus)
                .setParameter("ids", notificationIds)
                .setParameter("userId", userId)
                .executeUpdate();
    }
}
