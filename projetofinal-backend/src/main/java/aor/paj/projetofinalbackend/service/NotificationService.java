package aor.paj.projetofinalbackend.service;

import aor.paj.projetofinalbackend.bean.NotificationBean;
import aor.paj.projetofinalbackend.dao.TokenDao;
import aor.paj.projetofinalbackend.dto.NotificationDto;
import aor.paj.projetofinalbackend.dto.UpdateSeenStatusDto;
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
            // Extract the token from the header
            String token = authorizationHeader.substring("Bearer".length()).trim();
            UserEntity user = tokenDao.findUserByTokenValue(token);

            List<NotificationDto> notifications;
            int totalNotifications;

            if (type != null && seen != null) {
                // Get notifications by type and seen status
                notifications = notificationBean.getNotificationsByTypeAndSeen(NotificationType.valueOf(type), seen, page, limit);
                totalNotifications = notificationBean.getTotalNotificationsByTypeAndSeen(NotificationType.valueOf(type), seen);
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Invalid type or seen status").build();
            }

            if (notifications != null) {
                // Calculate total pages
                int totalPages = (int) Math.ceil((double) totalNotifications / limit);

                // Create response with notifications and pagination info
                Map<String, Object> response = new HashMap<>();
                response.put("notifications", notifications);
                response.put("totalPages", totalPages);

                return Response.ok(response).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Notifications not found").build();
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
            // Extract the token from the header
            String token = authorizationHeader.substring("Bearer".length()).trim();
            UserEntity sender = tokenDao.findUserByTokenValue(token);

            // Set the sender in the notification DTO
            notificationDto.setSenderId(sender.getId());

            // Save the new notification
            notificationBean.addNotification(notificationDto);

            return Response.status(Response.Status.CREATED).entity(notificationDto).build();
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
            // Update the seen status for the notifications
            notificationBean.updateSeenStatus(updateSeenStatusDto.getMessageOrNotificationIds(), updateSeenStatusDto.isSeen());

            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
