package aor.paj.projetofinalbackend.service;

import aor.paj.projetofinalbackend.bean.MessageBean;
import aor.paj.projetofinalbackend.dao.TokenDao;
import aor.paj.projetofinalbackend.dto.MessageDto;
import aor.paj.projetofinalbackend.dto.UpdateSeenStatusDto;
import aor.paj.projetofinalbackend.entity.MessageEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.mapper.MessageMapper;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/messages")
public class MessageService {

    @Inject
    MessageBean messageBean;

    @Inject
    TokenDao tokenDao;

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

                return Response.ok(response).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Messages not found").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }




    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMessage(MessageDto messageDto, @HeaderParam("Authorization") String authorizationHeader) {
        try {
            System.out.println(messageDto.getContent());
            // Extract the token from the header
            String token = authorizationHeader.substring("Bearer".length()).trim();
            UserEntity sender = tokenDao.findUserByTokenValue(token);

            // Set the sender in the message DTO
            messageDto.setSenderId(sender.getId());

            // Save the new message
            MessageEntity savedMessage = messageBean.addMessage(messageDto);

            // Convert the saved entity back to a DTO
            MessageDto savedMessageDto = MessageMapper.entityToDto(savedMessage);

            return Response.status(Response.Status.CREATED).entity(savedMessageDto).build();
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
            UserEntity sender = tokenDao.findUserByTokenValue(token);
            // Update the seen status for the messages
            messageBean.updateSeenStatus(updateSeenStatusDto.getMessageOrNotificationIds(), updateSeenStatusDto.isSeen(), sender);

            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

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
