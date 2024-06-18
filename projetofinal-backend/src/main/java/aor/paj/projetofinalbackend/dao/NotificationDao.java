package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.NotificationEntity;
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

    public List<NotificationEntity> findByTypeAndSeen(NotificationType type, Boolean seen, int offset, int limit) {
        try {
            return em.createNamedQuery("Notification.findByTypeAndSeen", NotificationEntity.class)
                    .setParameter("type", type)
                    .setParameter("seen", seen)
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public int countByTypeAndSeen(NotificationType type, Boolean seen) {
        try {
            return ((Number) em.createNamedQuery("Notification.countByTypeAndSeen")
                    .setParameter("type", type)
                    .setParameter("seen", seen)
                    .getSingleResult()).intValue();
        } catch (NoResultException e) {
            return 0;
        }
    }

    public void updateSeenStatus(List<Long> notificationIds, boolean newStatus) {
        em.createNamedQuery("Notification.updateSeenStatusByIds")
                .setParameter("seen", newStatus)
                .setParameter("ids", notificationIds)
                .executeUpdate();
    }
}
