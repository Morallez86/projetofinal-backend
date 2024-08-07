package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.*;
import aor.paj.projetofinalbackend.dto.NotificationDto;
import aor.paj.projetofinalbackend.entity.*;
import aor.paj.projetofinalbackend.mapper.NotificationMapper;
import aor.paj.projetofinalbackend.utils.HistoryType;
import aor.paj.projetofinalbackend.utils.NotificationManagingActions;
import aor.paj.projetofinalbackend.utils.NotificationType;
import aor.paj.projetofinalbackend.utils.ProjectStatus;
import aor.paj.projetofinalbackend.websocket.ApplicationSocket;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Stateless bean for handling notifications.
 * @see UserProjectBean
 * @see ProjectDao
 * @see UserNotificationDao
 * @see UserProjectDao
 * @see NotificationDao
 * @see UserDao
 * @see ProjectHistoryDao
 *
 * @author João Morais
 * @author Ricardo Elias
 */
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

    @EJB
    ProjectHistoryDao projectHistoryDao;

    @Inject
    private UserProjectBean userProjectBean;

    /**
     * Finds a notification by its ID.
     *
     * @param notificationId the ID of the notification
     * @return the notification entity
     */
    public NotificationEntity findNotificationById(Long notificationId) {
        return notificationDao.findNotificationById(notificationId);
    }

    /**
     * Retrieves notifications by user ID, type, and seen status with pagination.
     *
     * @param userId the user ID
     * @param type the notification type
     * @param seen the seen status
     * @param page the page number
     * @param limit the number of notifications per page
     * @return a list of notification DTOs
     */
    public List<NotificationDto> getNotificationsByUserIdAndTypeAndSeen(Long userId, String type, Boolean seen, int page, int limit) {
        int offset = (page - 1) * limit;
        NotificationType notificationType = type != null ? NotificationType.valueOf(type) : null;
        List<NotificationEntity> notifications = notificationDao.findByUserIdAndTypeAndSeen(userId, notificationType, seen, offset, limit);
        return NotificationMapper.listToDto(notifications);
    }

    /**
     * Retrieves the total number of notifications by user ID, type, and seen status.
     *
     * @param userId the user ID
     * @param type the notification type
     * @param seen the seen status
     * @return the total number of notifications
     */
    public int getTotalNotificationsByUserIdAndTypeAndSeen(Long userId, String type, Boolean seen) {
        NotificationType notificationType = type != null ? NotificationType.valueOf(type) : null;
        return notificationDao.countByUserIdAndTypeAndSeen(userId, notificationType, seen);
    }

    /**
     * Retrieves notifications by user ID and seen status with pagination.
     *
     * @param userId the user ID
     * @param seen the seen status
     * @param page the page number
     * @param limit the number of notifications per page
     * @return a list of notification DTOs
     */
    public List<NotificationDto> getNotificationsByUserIdAndSeen(Long userId, Boolean seen, int page, int limit) {
        int offset = (page - 1) * limit;
        List<NotificationEntity> notifications = notificationDao.findByUserIdAndTypeAndSeen(userId, null, seen, offset, limit);
        return NotificationMapper.listToDto(notifications);
    }

    /**
     * Retrieves the total number of notifications by user ID and seen status.
     *
     * @param userId the user ID
     * @param seen the seen status
     * @return the total number of notifications
     */
    public int getTotalNotificationsByUserIdAndSeen(Long userId, Boolean seen) {
        return notificationDao.countByUserIdAndTypeAndSeen(userId, null, seen);
    }

    /**
     * Updates the seen status of notifications and sends a refresh signal via websocket.
     *
     * @param userId the user ID
     * @param notificationIds the list of notification IDs
     * @param seen the seen status
     * @param sender the sender user entity
     */
    public void updateSeenStatus(Long userId, List<Long> notificationIds, boolean seen, UserEntity sender) {
        notificationDao.updateSeenStatusByUserIdAndIds(userId, notificationIds, seen);

        List<TokenEntity> activeTokens = sender.getTokens().stream()
                .filter(TokenEntity::isActiveToken)
                .collect(Collectors.toList());

        activeTokens.forEach(token -> ApplicationSocket.sendNotification(token.getTokenValue(), "refresh"));
    }

    /**
     * Sends a request or invitation to join a project.
     *
     * @param sender the sender user entity
     * @param notificationDto the notification DTO
     */
    @Transactional
    public void sendRequestInvitationProject(UserEntity sender, NotificationDto notificationDto) {
        try {
            // Validate the project ID and retrieve the project
            ProjectEntity project = projectDao.findProjectById(notificationDto.getProjectId());
            if (project == null) {
                throw new IllegalArgumentException("Invalid project ID");
            }

            // Check if the project has reached the maximum number of users
            if (userProjectBean.isProjectAtMaxUsers(notificationDto.getProjectId())) {
                throw new IllegalArgumentException("The project has reached the maximum number of users");
            }

            // Check if the user is already in the project based on the notification type
            if (Objects.equals(notificationDto.getType(), "300")) { // Managing type
                if (userProjectBean.isUserInProject(notificationDto.getSenderId(), notificationDto.getProjectId())) {
                    throw new IllegalArgumentException("The user is already in the project");
                }
            } else if (Objects.equals(notificationDto.getType(), "400")) { // Invitation type
                if (userProjectBean.isUserInProject(notificationDto.getReceiverId(), notificationDto.getProjectId())) {
                    throw new IllegalArgumentException("The user is already in the project");
                }
            }

            // Create a new NotificationEntity
            NotificationEntity notification = new NotificationEntity();

            // Set description based on the notification type
            if (Objects.equals(notificationDto.getType(), "300")) {
                notification.setDescription(sender.getUsername() + " would like to join the project: " + project.getTitle());
                notification.setAction(NotificationManagingActions.INVITATION);
            } else if (Objects.equals(notificationDto.getType(), "400")) {
                notification.setDescription(sender.getUsername() + " has invited you to the project: " + project.getTitle());
                notification.setAction(NotificationManagingActions.INVITATION);
            }

            notification.setType(NotificationType.fromValue(Integer.parseInt(notificationDto.getType())));
            notification.setTimestamp(LocalDateTime.now());
            notification.setSender(sender);
            notification.setProject(project);

            // Persist the notification first to generate the ID
            notificationDao.persist(notification);

            // Handle user notifications based on the type
            if (Objects.equals(notificationDto.getType(), "300")) {
                // Notify project admins for managing type
                List<UserEntity> projectAdmins = userProjectDao.findAdminsByProjectIdActive(notificationDto.getProjectId());
                for (UserEntity admin : projectAdmins) {
                    UserNotificationEntity userNotification = new UserNotificationEntity();
                    userNotification.setUser(admin);
                    userNotification.setNotification(notification);
                    userNotification.setSeen(false);

                    // Persist the user notification
                    userNotificationDao.persist(userNotification);

                    // Send notifications to active tokens
                    List<TokenEntity> activeTokens = admin.getTokens().stream()
                            .filter(TokenEntity::isActiveToken)
                            .collect(Collectors.toList());

                    activeTokens.forEach(token -> ApplicationSocket.sendNotification(token.getTokenValue(), "notification"));
                }
            } else if (Objects.equals(notificationDto.getType(), "400")) {
                // Notify the specific user for invitation type
                UserEntity receiver = userDao.findUserById(notificationDto.getReceiverId());
                if (receiver != null) {
                    UserNotificationEntity userNotification = new UserNotificationEntity();
                    userNotification.setUser(receiver);
                    userNotification.setNotification(notification);
                    userNotification.setSeen(false);

                    // Persist the user notification
                    userNotificationDao.persist(userNotification);

                    // Send notifications to active tokens
                    List<TokenEntity> activeTokens = receiver.getTokens().stream()
                            .filter(TokenEntity::isActiveToken)
                            .collect(Collectors.toList());

                    activeTokens.forEach(token -> ApplicationSocket.sendNotification(token.getTokenValue(), "notification"));
                } else {
                    throw new IllegalArgumentException("Invalid receiver ID");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Approves or rejects a notification to participate in a project.
     *
     * @param notificationDto the notification DTO
     * @param sender the sender user entity
     * @param isInvitation whether the notification is an invitation
     */
    @Transactional
    public void approveOrRejectNotificationParticipateProject(NotificationDto notificationDto, UserEntity sender, boolean isInvitation) {
        NotificationEntity notification = notificationDao.find(notificationDto.getId());
        ProjectEntity project = projectDao.findProjectById(notificationDto.getProjectId());
        UserEntity receiver = userDao.findUserById(notificationDto.getReceiverId());
        boolean approval = notificationDto.isApproval();
        UserEntity correctUserToAdd;

        if (notification != null && project != null && receiver != null) {
            // Update the existing notification's approval status
            notification.setApproval(approval);
            notificationDao.merge(notification);

            if (approval) {
                // Check if the user is already in the project
                if (isInvitation) {
                    correctUserToAdd = sender;
                } else {
                    correctUserToAdd = receiver;
                }
                UserProjectEntity confirmIfUserIsOnProject = userProjectDao.findByUserAndProjectActive(correctUserToAdd.getId(), project.getId());
                if (confirmIfUserIsOnProject == null) {
                    // Add a new user to the project
                    UserProjectEntity userProjectEntity = new UserProjectEntity();
                    userProjectEntity.setUser(correctUserToAdd);
                    userProjectEntity.setIsAdmin(false);
                    userProjectEntity.setActive(true);
                    userProjectEntity.setProject(project);

                    // Persist the UserProjectEntity
                    userProjectDao.persist(userProjectEntity);

                    ProjectHistoryEntity log = new ProjectHistoryEntity();
                    log.setTitle(correctUserToAdd.getUsername() + " is a new member");
                    log.setTimestamp(LocalDateTime.now());
                    log.setType(HistoryType.ADD);
                    log.setProject(project);
                    projectHistoryDao.persist(log);
                } else {
                    throw new IllegalArgumentException("User is already on the project.");
                }
            }

            // Create response notification based on the type
            NotificationEntity responseNotification = new NotificationEntity();
            responseNotification.setTimestamp(LocalDateTime.now());
            responseNotification.setProject(project);
            responseNotification.setSender(sender);
            responseNotification.setType(NotificationType.MESSAGE);

            if (notificationDto.getType().equals("400")) {
                responseNotification.setDescription(approval
                        ? notification.getSender().getUsername() + " has accepted your invitation for project: " + project.getTitle()
                        : notification.getSender().getUsername() + " has refused your invitation for project: " + project.getTitle());
            } else {
                responseNotification.setDescription(approval
                        ? "Congratulations, your request was approved for project: " + project.getTitle()
                        : "We are sorry, but your request was refused for project: " + project.getTitle());
            }

            notificationDao.persist(responseNotification);

            // Create and persist the user notification entity for the receiver
            UserNotificationEntity userNotification = new UserNotificationEntity();
            userNotification.setUser(receiver);
            userNotification.setNotification(responseNotification);
            userNotification.setSeen(false);

            userNotificationDao.persist(userNotification);

            // Find the user notification entity for the receiver and mark it as seen
            List<Long> notificationIds = Collections.singletonList(notification.getId());
            notificationDao.updateSeenStatusByUserIdAndIds(sender.getId(), notificationIds, true);

            // Send notifications via sockets or other methods
            List<TokenEntity> activeTokens = receiver.getTokens().stream()
                    .filter(TokenEntity::isActiveToken)
                    .collect(Collectors.toList());

            activeTokens.forEach(token -> ApplicationSocket.sendNotification(token.getTokenValue(), "notification"));
        } else {
            throw new IllegalArgumentException("Notification, project, or receiver not found.");
        }
    }

    /**
     * Sends expiration notifications for a resource to all admins.
     *
     * @param resource the resource entity
     */
    public void sendExpirationNotifications(ResourceEntity resource) {
        List<UserEntity> admins = userDao.findAdmins();

        NotificationEntity notification = new NotificationEntity();
        notification.setTimestamp(LocalDateTime.now());
        notification.setSender(null); // Set the sender as the system or any specific user if needed
        notification.setType(NotificationType.MESSAGE);
        notification.setDescription("Resource " + resource.getName() + " is expiring in a week.");

        notificationDao.persist(notification);

        for (UserEntity admin : admins) {
            UserNotificationEntity userNotification = new UserNotificationEntity();
            userNotification.setUser(admin);
            userNotification.setNotification(notification);
            userNotification.setSeen(false);

            userNotificationDao.persist(userNotification);

            List<TokenEntity> activeTokens = admin.getTokens().stream()
                    .filter(TokenEntity::isActiveToken)
                    .collect(Collectors.toList());

            activeTokens.forEach(token -> ApplicationSocket.sendNotification(token.getTokenValue(), "notification"));
        }
    }

    /**
     * Sends a project approval notification to all admins.
     *
     * @param projectEntity the project entity
     */
    @Transactional
    public void sendProjectAproval(ProjectEntity projectEntity) {
        List<UserEntity> admins = userDao.findAdmins();

        NotificationEntity notification = new NotificationEntity();
        notification.setTimestamp(LocalDateTime.now());
        notification.setSender(projectEntity.getOwner());
        notification.setType(NotificationType.MANAGING);
        notification.setDescription("Project: " + projectEntity.getTitle() + " from " +
                projectEntity.getOwner().getUsername() + " is ready for approval");
        notification.setProject(projectEntity);
        notification.setAction(NotificationManagingActions.PROJECT);

        notificationDao.persist(notification);

        for (UserEntity admin : admins) {
            UserNotificationEntity userNotification = new UserNotificationEntity();
            userNotification.setUser(admin);
            userNotification.setNotification(notification);
            userNotification.setSeen(false);

            userNotificationDao.persist(userNotification);

            List<TokenEntity> activeTokens = admin.getTokens().stream()
                    .filter(TokenEntity::isActiveToken)
                    .collect(Collectors.toList());

            activeTokens.forEach(token -> ApplicationSocket.sendNotification(token.getTokenValue(), "notification"));
        }
    }

    /**
     * Approves or rejects a project ready notification.
     *
     * @param notificationDto the notification DTO
     * @param sender the sender user entity
     */
    @Transactional
    public void approveOrRejectNotificationReadyProject(NotificationDto notificationDto, UserEntity sender) {
        NotificationEntity notification = notificationDao.find(notificationDto.getId());
        ProjectEntity project = projectDao.findProjectById(notificationDto.getProjectId());
        boolean approval = notificationDto.isApproval();

        if (notification != null && project != null && sender != null) {
            // Update the existing notification's approval status
            notification.setApproval(approval);
            notificationDao.merge(notification);

            // Create response notification based on the type
            NotificationEntity responseNotification = new NotificationEntity();
            responseNotification.setTimestamp(LocalDateTime.now());
            responseNotification.setProject(project);
            responseNotification.setSender(sender);
            responseNotification.setType(NotificationType.PROJECT);

            if (approval) {
                responseNotification.setDescription("Congratulations, your project: " + project.getTitle() + " was approved");
                project.setStatus(ProjectStatus.READY);
                projectDao.merge(project);
            } else {
                responseNotification.setDescription("We are sorry, but your project: " + project.getTitle() + " was cancelled");
                project.setStatus(ProjectStatus.CANCELLED);
                projectDao.merge(project);
            }

            notificationDao.persist(responseNotification);

            // Create and persist the user notification entity for the receiver
            UserNotificationEntity userNotification = new UserNotificationEntity();
            userNotification.setUser(project.getOwner());
            userNotification.setNotification(responseNotification);
            userNotification.setSeen(false);

            userNotificationDao.persist(userNotification);

            // Find the user notification entity for the receiver and mark it as seen
            List<Long> notificationIds = Collections.singletonList(notification.getId());
            notificationDao.updateSeenStatusByUserIdAndIds(sender.getId(), notificationIds, true);

            // Send notifications via sockets or other methods
            List<TokenEntity> activeTokens = project.getOwner().getTokens().stream()
                    .filter(TokenEntity::isActiveToken)
                    .collect(Collectors.toList());

            activeTokens.forEach(token -> ApplicationSocket.sendNotification(token.getTokenValue(), "notification"));
        } else {
            throw new IllegalArgumentException("Notification, project, or receiver not found.");
        }
    }
}
