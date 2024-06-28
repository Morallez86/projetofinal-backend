package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.*;
import aor.paj.projetofinalbackend.dto.NotificationDto;
import aor.paj.projetofinalbackend.entity.*;
import aor.paj.projetofinalbackend.mapper.NotificationMapper;
import aor.paj.projetofinalbackend.utils.NotificationType;
import aor.paj.projetofinalbackend.websocket.ApplicationSocket;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Stateless
public class NotificationBean {

    @EJB
    ProjectDao projectDao;

    @EJB
    UserNotificationDao userNotificationDao;

    @EJB
    UserProjectDao userProjectDao;

    @EJB
    NotificationDao notificationDao;

    @EJB
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


    public void updateSeenStatus(Long userId, List<Long> notificationIds, boolean seen, UserEntity sender) {
        notificationDao.updateSeenStatusByUserIdAndIds(userId, notificationIds, seen);

        List<TokenEntity> activeTokens = sender.getTokens().stream()
                .filter(TokenEntity::isActiveToken)
                .collect(Collectors.toList());

        activeTokens.forEach(token -> ApplicationSocket.sendNotification(token.getTokenValue(), "refresh"));
    }

    @Transactional
    public void sendRequestInvitationProject(UserEntity sender, NotificationDto notificationDto) {
        try {
            // Validate the project ID and retrieve the project
            ProjectEntity project = projectDao.findProjectById(notificationDto.getProjectId());
            if (project == null) {
                throw new IllegalArgumentException("Invalid project ID");
            }

            // Create a new NotificationEntity
            NotificationEntity notification = new NotificationEntity();
            notification.setDescription(sender.getUsername() + " would like to join the project: " + project.getTitle());
            notification.setType(NotificationType.fromValue(Integer.parseInt(notificationDto.getType())));
            notification.setTimestamp(LocalDateTime.now());
            notification.setSender(sender);
            notification.setProject(project);

            // Persist the notification first to generate the ID
            notificationDao.persist(notification);

            // Create UserNotificationEntity entries for each user associated with the project
            List<UserEntity> projectAdmins = userProjectDao.findAdminsByProjectId(notificationDto.getProjectId());
            for (UserEntity admin : projectAdmins) {
                UserNotificationEntity userNotification = new UserNotificationEntity();
                userNotification.setUser(admin);
                userNotification.setNotification(notification);
                userNotification.setSeen(false);

                // Persist the userNotification
                userNotificationDao.persist(userNotification);

                List<TokenEntity> activeTokens = admin.getTokens().stream()
                        .filter(TokenEntity::isActiveToken)
                        .collect(Collectors.toList());

                activeTokens.forEach(token -> ApplicationSocket.sendNotification(token.getTokenValue(), "notification"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Transactional
    public void approveOrRejectNotification(NotificationDto notificationDto, UserEntity sender) {
        NotificationEntity notification = notificationDao.find(notificationDto.getId());
        System.out.println(notification);
        ProjectEntity project = projectDao.findProjectById(notificationDto.getProjectId());
        System.out.println(project);
        UserEntity receiver = userDao.findUserById(notificationDto.getReceiverId());
        System.out.println(notificationDto.getReceiverId());
        System.out.println(receiver);
        boolean approval = notificationDto.isApproval();
        System.out.println("4");

        if (notification != null && project != null && receiver != null) {
            // Update the existing notification's approval status
            notification.setApproval(approval);
            System.out.println("5");
            notificationDao.merge(notification);

            // Create a new response notification
            NotificationEntity responseNotification = new NotificationEntity();
            System.out.println("6");
            responseNotification.setTimestamp(LocalDateTime.now());
            responseNotification.setProject(project);
            responseNotification.setSender(sender);
            responseNotification.setType(NotificationType.INVITATION);
            responseNotification.setDescription(approval
                    ? "Congratulations, your request was approved for project: " + project.getTitle()
                    : "We are sorry, but your request was refused for project: " + project.getTitle());

            notificationDao.persist(responseNotification);
            System.out.println("7");

            // Create and persist the user notification entity for the receiver
            UserNotificationEntity userNotification = new UserNotificationEntity();
            userNotification.setUser(receiver);
            userNotification.setNotification(responseNotification);
            userNotification.setSeen(false);

            userNotificationDao.persist(userNotification);
            System.out.println("1");

            List<TokenEntity> activeTokens = receiver.getTokens().stream()
                    .filter(TokenEntity::isActiveToken)
                    .collect(Collectors.toList());

            activeTokens.forEach(token -> ApplicationSocket.sendNotification(token.getTokenValue(), "notification"));
        } else {
            throw new IllegalArgumentException("Notification, project, or receiver not found.");
        }
    }
}
