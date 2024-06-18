package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.NotificationDao;
import aor.paj.projetofinalbackend.dao.UserDao;
import aor.paj.projetofinalbackend.dto.NotificationDto;
import aor.paj.projetofinalbackend.entity.NotificationEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.mapper.NotificationMapper;
import aor.paj.projetofinalbackend.utils.NotificationType;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class NotificationBean {

    @Inject
    NotificationDao notificationDao;

    @Inject
    UserDao userDao;

    public List<NotificationDto> getNotificationsByTypeAndSeen(NotificationType type, Boolean seen, int page, int limit) {
        int offset = (page - 1) * limit;
        List<NotificationEntity> notifications = notificationDao.findByTypeAndSeen(type, seen, offset, limit);
        return NotificationMapper.listToDto(notifications);
    }

    public int getTotalNotificationsByTypeAndSeen(NotificationType type, Boolean seen) {
        return notificationDao.countByTypeAndSeen(type, seen);
    }

    public NotificationEntity addNotification(NotificationDto notificationDto) {
        try {
            // Fetch sender and project from database
            UserEntity sender = userDao.findUserById(notificationDto.getSenderId());
            if (sender == null) {
                throw new IllegalArgumentException("Sender not found");
            }

            NotificationEntity notificationEntity = NotificationMapper.dtoToEntity(notificationDto);
            notificationEntity.setSender(sender);
            notificationEntity.setTimestamp(LocalDateTime.now());

            notificationDao.persist(notificationEntity);

            return notificationEntity;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void updateSeenStatus(List<Long> notificationIds, boolean seen) {
        notificationDao.updateSeenStatus(notificationIds, seen);
    }
}
