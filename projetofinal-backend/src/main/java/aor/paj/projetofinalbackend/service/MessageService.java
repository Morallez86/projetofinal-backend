package aor.paj.projetofinalbackend.service;

import aor.paj.projetofinalbackend.bean.MessageBean;
import aor.paj.projetofinalbackend.dao.TokenDao;
import aor.paj.projetofinalbackend.dto.MessageDto;
import aor.paj.projetofinalbackend.dto.UpdateSeenStatusDto;
import aor.paj.projetofinalbackend.entity.MessageEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.mapper.MessageMapper;
import aor.paj.projetofinalbackend.utils.LoggerUtil;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class for managing messages.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Path("/messages")
public class MessageService {

    @Inject
    MessageBean messageBean;

    @Inject
    TokenDao tokenDao;

    /**
     * Retrieves messages based on type (received, sent, unread).
     *
     * @param authorizationHeader Authorization token in the request header.
     * @param type Type of messages to retrieve (received, sent, unread).
     * @param page Page number for pagination.
     * @param limit Limit of messages per page.
     * @param username Username filter for message sender or receiver.
     * @param content Content filter for message content.
     * @return Response with messages and pagination info in JSON format, or a BAD_REQUEST response if the type is invalid.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMessages(
            @HeaderParam("Authorization") String authorizationHeader,
            @QueryParam("type") String type,
            @QueryParam("page") int page,
            @QueryParam("limit") int limit,
            @QueryParam("username") String username,
            @QueryParam("content") String content) {

        try {
            // Extract the token from the header
            String token = authorizationHeader.substring("Bearer".length()).trim();
            UserEntity user = tokenDao.findUserByTokenValue(token);

            List<MessageDto> messages;
            int totalMessages;

            if ("received".equalsIgnoreCase(type)) {
                // Get received messages for the user
                messages = messageBean.getReceivedMessagesForUser(user.getId(), page, limit, username, content);
                totalMessages = messageBean.getTotalReceivedMessagesForUser(user.getId(), username, content);
            } else if ("sent".equalsIgnoreCase(type)) {
                // Get sent messages for the user
                messages = messageBean.getSentMessagesForUser(user.getId(), page, limit, username, content);
                totalMessages = messageBean.getTotalSentMessagesForUser(user.getId(), username, content);
            } else if ("unread".equalsIgnoreCase(type)) {
                // Get unread messages for the user
                messages = messageBean.getUnreadMessagesForUser(user.getId(), page, limit, username, content);
                totalMessages = messageBean.getTotalUnreadMessagesForUser(user.getId(), username, content);
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Invalid message type").build();
            }

            if (messages != null) {
                // Calculate total pages
                int totalPages = (int) Math.ceil((double) totalMessages / limit);

                // Create response with messages and pagination info
                Map<String, Object> response = new HashMap<>();
                response.put("messages", messages);
                response.put("totalPages", totalPages);

                LoggerUtil.logInfo("OPEN MESSAGES" , "at" + LocalDateTime.now(), user.getEmail(), token);
                return Response.ok(response).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Messages not found").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Adds a new message.
     *
     * @param messageDto MessageDto object containing message details.
     * @param authorizationHeader Authorization token in the request header.
     * @return Response with the newly added message in JSON format, or an INTERNAL_SERVER_ERROR response if an error occurs.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMessage(MessageDto messageDto, @HeaderParam("Authorization") String authorizationHeader) {
        try {
            // Extract the token from the header
            String token = authorizationHeader.substring("Bearer".length()).trim();
            UserEntity sender = tokenDao.findUserByTokenValue(token);

            // Set the sender in the message DTO
            messageDto.setSenderId(sender.getId());

            // Save the new message
            MessageEntity savedMessage = messageBean.addMessage(messageDto);

            // Convert the saved entity back to a DTO
            MessageDto savedMessageDto = MessageMapper.entityToDto(savedMessage);

            LoggerUtil.logInfo("SEND MESSAGE TO " + messageDto.getReceiverUsername() , "at" + LocalDateTime.now(), sender.getEmail(), token);

            return Response.status(Response.Status.CREATED).entity(savedMessageDto).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Updates the seen status of messages.
     *
     * @param updateSeenStatusDto UpdateSeenStatusDto object containing message IDs and seen status.
     * @param authorizationHeader Authorization token in the request header.
     * @return Response with OK status upon successful update, or an INTERNAL_SERVER_ERROR response if an error occurs.
     */
    @PUT
    @Path("/seen")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateSeenStatus(UpdateSeenStatusDto updateSeenStatusDto, @HeaderParam("Authorization") String authorizationHeader) {
        try {
            String token = authorizationHeader.substring("Bearer".length()).trim();
            UserEntity sender = tokenDao.findUserByTokenValue(token);
            // Update the seen status for the messages
            messageBean.updateSeenStatus(updateSeenStatusDto.getMessageOrNotificationIds(), updateSeenStatusDto.isSeen(), sender);
            LoggerUtil.logInfo("UPDATE MESSAGES: SEEN STATUS From this ids: " + updateSeenStatusDto.getMessageOrNotificationIds() + " to " + updateSeenStatusDto.isSeen() , "at" + LocalDateTime.now(), sender.getEmail(), token);

            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Retrieves the count of unread messages for the user.
     *
     * @param authorizationHeader Authorization token in the request header.
     * @return Response with the count of unread messages in JSON format, or an INTERNAL_SERVER_ERROR response if an error occurs.
     */
    @GET
    @Path("/unread/count")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUnreadMessagesCount(@HeaderParam("Authorization") String authorizationHeader) {
        try {
            // Extract the token from the header
            String token = authorizationHeader.substring("Bearer".length()).trim();
            UserEntity user = tokenDao.findUserByTokenValue(token);

            // Get the count of unread messages for the user
            int unreadCount = messageBean.getTotalUnreadMessagesForUser(user.getId(), null, null);

            // Prepare response JSON
            Map<String, Integer> response = new HashMap<>();
            response.put("unreadCount", unreadCount);

            return Response.ok(response).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error fetching unread messages count").build();
        }
    }
}
