package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.NotificationEntity;
import aor.paj.projetofinalbackend.entity.ProjectEntity;
import aor.paj.projetofinalbackend.utils.NotificationType;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;

import java.util.List;

@Stateless
public class NotificationDao extends AbstractDao<NotificationEntity> {
    private static final long serialVersionUID = 1L;

    public NotificationDao() {
        super(NotificationEntity.class);
    }

    public NotificationEntity findNotificationById(Long id) {
        try {
            return em.createNamedQuery("NotificationEntity.findNotificationById", NotificationEntity.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

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

    public void updateSeenStatusByUserIdAndIds(Long userId, List<Long> notificationIds, boolean newStatus) {
        em.createNamedQuery("Notification.updateSeenStatusByUserIdAndIds")
                .setParameter("seen", newStatus)
                .setParameter("ids", notificationIds)
                .setParameter("userId", userId)
                .executeUpdate();
    }
}
