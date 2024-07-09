package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.ChatMessageDao;
import aor.paj.projetofinalbackend.dao.ProjectDao;
import aor.paj.projetofinalbackend.dao.UserDao;
import aor.paj.projetofinalbackend.dto.ChatMessageDto;
import aor.paj.projetofinalbackend.entity.ChatMessageEntity;
import aor.paj.projetofinalbackend.entity.ProjectEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.mapper.ChatMessageMapper;
import aor.paj.projetofinalbackend.utils.LocalDateTimeAdapter;
import aor.paj.projetofinalbackend.websocket.ProjectChatSocket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import javax.naming.NamingException;
import java.time.LocalDateTime;

/**
 * Stateless bean responsible for managing chat messages.
 * It handles the creation of chat messages.
 *
 * @see ChatMessageDao
 * @see UserDao
 * @see ProjectDao
 * @see ChatMessageDto
 * @see ChatMessageEntity
 * @see ProjectEntity
 * @see UserEntity
 * @see ChatMessageMapper
 * @see LocalDateTimeAdapter
 * @see ProjectChatSocket
 * @see Gson
 * @see GsonBuilder
 * @see LocalDateTime
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Stateless
public class ChatMesssageBean {

    @EJB
    ChatMessageDao chatMessageDao;

    @EJB
    UserDao userDao;

    @EJB
    ProjectDao projectDao;

    @Inject
    private ProjectChatSocket projectChatSocket;

    /**
     * Creates a new chat message.
     * This method converts the provided DTO to an entity, sets the sender and project,
     * persists the message, and sends the message through the WebSocket.
     *
     * @param chatMessageDto the DTO containing chat message details
     * @return the saved chat message as a DTO
     * @throws NamingException if there is an error with JNDI naming
     */
    public ChatMessageDto createChatMsg(ChatMessageDto chatMessageDto) throws NamingException {
        // Map DTO to entity
        ChatMessageEntity chatMessageEntity = ChatMessageMapper.toEntity(chatMessageDto);

        // Find and set the sender
        UserEntity user = userDao.findUserById(chatMessageDto.getSenderId());
        chatMessageEntity.setSender(user);

        // Find and set the project
        ProjectEntity project = projectDao.findProjectById(chatMessageDto.getProjectId());
        chatMessageEntity.setProject(project);

        // Set the current timestamp
        chatMessageEntity.setTimestamp(LocalDateTime.now());

        // Persist the chat message entity
        chatMessageDao.persist(chatMessageEntity);

        // Retrieve the saved chat message entity
        ChatMessageEntity chatMessageSaved = chatMessageDao.findChatMessage(
                chatMessageEntity.getProject().getId(),
                chatMessageEntity.getTimestamp(),
                chatMessageEntity.getSender().getId()
        );

        // Convert the saved entity to JSON
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        String jsonMsg = gson.toJson(ChatMessageMapper.toDto(chatMessageSaved));

        // Send the message through the WebSocket
        projectChatSocket.onMessage(jsonMsg);

        // Return the saved chat message as a DTO
        return ChatMessageMapper.toDto(chatMessageSaved);
    }
}
