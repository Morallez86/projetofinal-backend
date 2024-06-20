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

    public List<NotificationDto> getNotificationsByUserIdAndTypeAndSeen(Long userId, String type, Boolean seen, int page, int limit) {
        int offset = (page - 1) * limit;
        NotificationType notificationType = type != null ? NotificationType.valueOf(type) : null;
        List<NotificationEntity> notifications = notificationDao.findByUserIdAndTypeAndSeen(userId, notificationType, seen, offset, limit);
        return NotificationMapper.listToDto(notifications);
    }

    public int getTotalNotificationsByUserIdAndTypeAndSeen(Long userId, String type, Boolean seen) {
        NotificationType notificationType = type != null ? NotificationType.valueOf(type) : null;
        return notificationDao.countByUserIdAndTypeAndSeen(userId, notificationType, seen);
    }

    public List<NotificationDto> getNotificationsByUserIdAndSeen(Long userId, Boolean seen, int page, int limit) {
        int offset = (page - 1) * limit;
        List<NotificationEntity> notifications = notificationDao.findByUserIdAndTypeAndSeen(userId, null, seen, offset, limit);
        return NotificationMapper.listToDto(notifications);
    }

    public int getTotalNotificationsByUserIdAndSeen(Long userId, Boolean seen) {
        return notificationDao.countByUserIdAndTypeAndSeen(userId, null, seen);
    }


    public NotificationEntity addNotification(NotificationDto notificationDto) {
        try {
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

    public void updateSeenStatus(Long userId, List<Long> notificationIds, boolean seen) {
        notificationDao.updateSeenStatusByUserIdAndIds(userId, notificationIds, seen);
    }
}
