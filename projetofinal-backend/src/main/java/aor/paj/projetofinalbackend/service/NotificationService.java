package aor.paj.projetofinalbackend.service;

import aor.paj.projetofinalbackend.bean.NotificationBean;
import aor.paj.projetofinalbackend.dao.TokenDao;
import aor.paj.projetofinalbackend.dto.NotificationDto;
import aor.paj.projetofinalbackend.dto.UpdateSeenStatusDto;
import aor.paj.projetofinalbackend.entity.NotificationEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.mapper.NotificationMapper;
import aor.paj.projetofinalbackend.utils.NotificationType;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/notifications")
public class NotificationService {

    @Inject
    NotificationBean notificationBean;

    @Inject
    TokenDao tokenDao;

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
            UserEntity user = tokenDao.findUserByTokenValue(token);

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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addNotification(NotificationDto notificationDto, @HeaderParam("Authorization") String authorizationHeader) {
        try {
            String token = authorizationHeader.substring("Bearer".length()).trim();
            UserEntity sender = tokenDao.findUserByTokenValue(token);

            if (sender != null) {
                notificationDto.setSenderId(sender.getId());
                NotificationEntity newNotification = notificationBean.addNotification(notificationDto);
                return Response.status(Response.Status.CREATED).entity(NotificationMapper.entityToDto(newNotification)).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity("User not authorized").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/seen")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateSeenStatus(UpdateSeenStatusDto updateSeenStatusDto, @HeaderParam("Authorization") String authorizationHeader) {
        try {
            String token = authorizationHeader.substring("Bearer".length()).trim();
            UserEntity user = tokenDao.findUserByTokenValue(token);

            if (user != null) {
                notificationBean.updateSeenStatus(user.getId(), updateSeenStatusDto.getMessageOrNotificationIds(), updateSeenStatusDto.isSeen());
                return Response.status(Response.Status.OK).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity("User not authorized").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/unread/count")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUnreadNotificationsCount(@HeaderParam("Authorization") String authorizationHeader) {
        try {
            // Extract the token from the header
            String token = authorizationHeader.substring("Bearer".length()).trim();
            UserEntity user = tokenDao.findUserByTokenValue(token);

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
}
