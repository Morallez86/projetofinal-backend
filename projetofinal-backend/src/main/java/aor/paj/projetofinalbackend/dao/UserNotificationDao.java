package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.ProjectEntity;
import aor.paj.projetofinalbackend.entity.UserNotificationEntity;
import aor.paj.projetofinalbackend.entity.UserProjectEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;

@Stateless
public class UserNotificationDao extends AbstractDao<UserNotificationEntity> {

    private static final long serialVersionUID = 1L;

    public UserNotificationDao() {
        super(UserNotificationEntity.class);
    }

    public UserNotificationEntity findUserNotificationById(Long id) {
        try {
            return em.createNamedQuery("UserNotificationEntity.findUserNotificationById", UserNotificationEntity.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

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
