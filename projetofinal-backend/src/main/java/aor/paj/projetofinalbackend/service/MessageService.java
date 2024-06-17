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
            @QueryParam("limit") int limit) {

        try {
            // Extract the token from the header
            String token = authorizationHeader.substring("Bearer".length()).trim();
            UserEntity user = tokenDao.findUserByTokenValue(token);

            List<MessageDto> messages;
            int totalMessages;

            if ("received".equalsIgnoreCase(type)) {
                // Get received messages for the user
                messages = messageBean.getReceivedMessagesForUser(user.getId(), page, limit);
                totalMessages = messageBean.getTotalReceivedMessagesForUser(user.getId());
            } else if ("sent".equalsIgnoreCase(type)) {
                // Get sent messages for the user
                messages = messageBean.getSentMessagesForUser(user.getId(), page, limit);
                totalMessages = messageBean.getTotalSentMessagesForUser(user.getId());
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
            // Update the seen status for the messages
            messageBean.updateSeenStatus(updateSeenStatusDto.getMessageIds(), updateSeenStatusDto.isSeen());

            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
