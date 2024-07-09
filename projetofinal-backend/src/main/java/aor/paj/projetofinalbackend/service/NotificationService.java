package aor.paj.projetofinalbackend.service;

import aor.paj.projetofinalbackend.bean.NotificationBean;
import aor.paj.projetofinalbackend.bean.TokenBean;
import aor.paj.projetofinalbackend.bean.UserProjectBean;
import aor.paj.projetofinalbackend.dto.NotificationDto;
import aor.paj.projetofinalbackend.dto.UpdateSeenStatusDto;
import aor.paj.projetofinalbackend.entity.NotificationEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.utils.LoggerUtil;
import aor.paj.projetofinalbackend.utils.NotificationManagingActions;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Service class for managing notifications.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Path("/notifications")
public class NotificationService {

    @Inject
    NotificationBean notificationBean;

    @Inject
    UserProjectBean userProjectBean;

    @Inject
    TokenBean tokenBean;

    /**
     * Retrieves notifications based on type and seen status for a specific user.
     *
     * @param authorizationHeader Authorization token in the request header.
     * @param type Type of notifications to retrieve (optional).
     * @param seen Seen status of notifications (true/false).
     * @param page Page number for pagination.
     * @param limit Limit of notifications per page.
     * @return Response with notifications and pagination info in JSON format, or an appropriate error response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNotifications(
            @HeaderParam("Authorization") String authorizationHeader,
            @QueryParam("type") String type,
            @QueryParam("seen") Boolean seen,
            @QueryParam("page") int page,
            @QueryParam("limit") int limit) {

        try {
            String token = authorizationHeader.substring("Bearer".length()).trim();
            UserEntity user = tokenBean.findUserByToken(token);

            if (user != null) {
                List<NotificationDto> notifications;
                int totalNotifications;

                if (seen != null) {
                    if (type != null) {
                        notifications = notificationBean.getNotificationsByUserIdAndTypeAndSeen(user.getId(), type, seen, page, limit);
                        totalNotifications = notificationBean.getTotalNotificationsByUserIdAndTypeAndSeen(user.getId(), type, seen);
                    } else {
                        notifications = notificationBean.getNotificationsByUserIdAndSeen(user.getId(), seen, page, limit);
                        totalNotifications = notificationBean.getTotalNotificationsByUserIdAndSeen(user.getId(), seen);
                    }

                    if (notifications != null) {
                        int totalPages = (int) Math.ceil((double) totalNotifications / limit);
                        Map<String, Object> response = new HashMap<>();
                        response.put("notifications", notifications);
                        response.put("totalPages", totalPages);
                        LoggerUtil.logInfo("OPEN NOTIFICATIONS" , "at" + LocalDateTime.now(), user.getEmail(), token);

                        return Response.ok(response).build();
                    } else {
                        return Response.status(Response.Status.NOT_FOUND).entity("Notifications not found").build();
                    }
                } else {
                    return Response.status(Response.Status.BAD_REQUEST).entity("Seen status is required").build();
                }
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity("User not authorized").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Adds a new notification.
     *
     * @param authorizationHeader Authorization token in the request header.
     * @param notificationDto NotificationDto object containing notification details.
     * @return Response with OK status upon successful addition, or an appropriate error response.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addNotification(@HeaderParam("Authorization") String authorizationHeader, NotificationDto notificationDto) {
        try {
            String token = authorizationHeader.substring("Bearer".length()).trim();
            UserEntity sender = tokenBean.findUserByToken(token);
            if (sender != null) {
                if (Objects.equals(notificationDto.getType(), "300") || Objects.equals(notificationDto.getType(), "400")) {
                    notificationBean.sendRequestInvitationProject(sender, notificationDto);
                    LoggerUtil.logInfo("NOTIFICATION SENT TO USER WHITH THIS ID :" + notificationDto.getReceiverId() , "at" + LocalDateTime.now(), sender.getEmail(), token);

                    return Response.status(Response.Status.OK).build();
                } else {
                    return Response.status(Response.Status.BAD_REQUEST).entity("Invalid notification type").build();
                }
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity("User not authorized").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Updates the seen status of notifications.
     *
     * @param updateSeenStatusDto UpdateSeenStatusDto object containing notification IDs and seen status.
     * @param authorizationHeader Authorization token in the request header.
     * @return Response with OK status upon successful update, or an appropriate error response.
     */
    @PUT
    @Path("/seen")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateSeenStatus(UpdateSeenStatusDto updateSeenStatusDto, @HeaderParam("Authorization") String authorizationHeader) {
        try {
            String token = authorizationHeader.substring("Bearer".length()).trim();
            UserEntity user = tokenBean.findUserByToken(token);

            if (user != null) {
                notificationBean.updateSeenStatus(user.getId(), updateSeenStatusDto.getMessageOrNotificationIds(), updateSeenStatusDto.isSeen(), user);
                LoggerUtil.logInfo("UPDATE NOTIFICATIONS: SEEN STATUS From this ids: " + updateSeenStatusDto.getMessageOrNotificationIds() + " to " + updateSeenStatusDto.isSeen() , "at" + LocalDateTime.now(), user.getEmail(), token);
                return Response.status(Response.Status.OK).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity("User not authorized").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Retrieves the count of unread notifications for the user.
     *
     * @param authorizationHeader Authorization token in the request header.
     * @return Response with the count of unread notifications in JSON format, or an appropriate error response.
     */
    @GET
    @Path("/unread/count")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUnreadNotificationsCount(@HeaderParam("Authorization") String authorizationHeader) {
        try {
            // Extract the token from the header
            String token = authorizationHeader.substring("Bearer".length()).trim();
            UserEntity user = tokenBean.findUserByToken(token);

            // Get the count of unread notifications for the user
            int unreadCount = notificationBean.getTotalNotificationsByUserIdAndTypeAndSeen(user.getId(), null, false);

            // Prepare response JSON
            Map<String, Integer> response = new HashMap<>();
            response.put("unreadCount", unreadCount);

            return Response.ok(response).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error fetching unread notifications count").build();
        }
    }

    /**
     * Approves or rejects a notification, based on the notification type.
     *
     * @param authorizationHeader Authorization token in the request header.
     * @param notificationDto    NotificationDto object containing notification details.
     * @return Response with OK status upon successful operation, or an appropriate error response.
     */
    @PUT
    @Path("/approval")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response approveOrRejectNotification(
            @HeaderParam("Authorization") String authorizationHeader, NotificationDto notificationDto) {
        try {
            String token = authorizationHeader.substring("Bearer".length()).trim();
            UserEntity user = tokenBean.findUserByToken(token);
            NotificationEntity notification = notificationBean.findNotificationById(notificationDto.getId());

            if (user == null) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("User not authorized").build();
            }
            if(notification.getAction() == NotificationManagingActions.INVITATION) {
                // Check if the notification type is INVITATION (type 400)
                boolean isInvitation = "400".equals(notificationDto.getType());

                // Use the senderId if it's an INVITATION, otherwise use the receiverId
                long userIdToCheck = isInvitation ? notificationDto.getSenderId() : notificationDto.getReceiverId();

                // Verify if the user is already in the project
                if (userProjectBean.isUserInProject(userIdToCheck, notificationDto.getProjectId())) {
                    return Response.status(Response.Status.CONFLICT).entity("User is already a member of the project").build();
                }
                // Verify if the project has an available slot
                if (userProjectBean.isProjectAtMaxUsers(notificationDto.getProjectId())) {
                    return Response.status(Response.Status.CONFLICT).entity("Project has reached maximum capacity").build();
                }

                notificationBean.approveOrRejectNotificationParticipateProject(notificationDto, user, isInvitation);
            }else{
                notificationBean.approveOrRejectNotificationReadyProject(notificationDto, user);
            }

            return Response.status(Response.Status.OK).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }


}
