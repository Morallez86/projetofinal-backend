package aor.paj.projetofinalbackend.websocket;

import aor.paj.projetofinalbackend.dao.ProjectDao;
import aor.paj.projetofinalbackend.dao.TokenDao;
import aor.paj.projetofinalbackend.dao.UserDao;
import aor.paj.projetofinalbackend.dto.ChatMessageDto;
import aor.paj.projetofinalbackend.entity.ProjectEntity;
import aor.paj.projetofinalbackend.entity.TokenEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.entity.UserProjectEntity;
import aor.paj.projetofinalbackend.utils.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket endpoint for managing chat messages within a specific project.
 * This endpoint handles WebSocket connections and message broadcasting among users of a project.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@ApplicationScoped
@ServerEndpoint("/websocket/projectChat/{projectId}/{token}")
public class ProjectChatSocket {

    @Inject
    private ProjectDao projectDao;

    @Inject
    private TokenDao tokenDao;

    @Inject
    private UserDao userDao;

    private static final Map<Long, Map<String, Session>> projectSessions = new ConcurrentHashMap<>();

    /**
     * Sends a chat message to all users connected to the specified project.
     *
     * @param messageDto The DTO containing message details.
     * @param message The message content to send.
     */
    public void send(ChatMessageDto messageDto, String message) {
        ProjectEntity project = projectDao.findProjectById(messageDto.getProjectId());
        UserEntity userSender = userDao.findUserById(messageDto.getSenderId());
        List<UserEntity> usersFromProject = new ArrayList<>();
        List<Session> userSessions = new ArrayList<>();

        for (UserProjectEntity userProject : project.getUserProjects()) {
            UserEntity user = userProject.getUser();
            if (user != null && userProject.isActive()) {
                usersFromProject.add(user);
            }
        }

        for (UserEntity user : usersFromProject) {
            List<TokenEntity> tokens = tokenDao.findTokensByUserId(user.getId());
            for (TokenEntity token : tokens) {
                if (token != null) {
                    Map<String, Session> sessions = projectSessions.get(messageDto.getProjectId());
                    if (sessions != null) {
                        Session userSession = sessions.get(token.getTokenValue());
                        if (userSession != null) {
                            userSessions.add(userSession);
                        }
                    }
                }
            }
        }

        for (Session session : userSessions) {
            try {
                session.getBasicRemote().sendObject(message);
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Handles WebSocket connection opening for the project chat.
     *
     * @param session The WebSocket session object.
     * @param projectId The ID of the project associated with the chat.
     * @param token The token used for authentication.
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("projectId") Long projectId, @PathParam("token") String token) {
        projectSessions.computeIfAbsent(projectId, k -> new HashMap<>()).put(token, session);
    }

    /**
     * Handles WebSocket connection closure for the project chat.
     *
     * @param session The WebSocket session object.
     * @param projectId The ID of the project associated with the chat.
     * @param token The token used for authentication.
     * @param reason The reason for the connection closure.
     */
    @OnClose
    public void onClose(Session session, @PathParam("projectId") Long projectId, @PathParam("token") String token, CloseReason reason) {
        Map<String, Session> sessions = projectSessions.get(projectId);
        if (sessions != null) {
            sessions.values().removeIf(s -> s.equals(session));
            if (sessions.isEmpty()) {
                projectSessions.remove(projectId);
            }
        }
    }

    /**
     * Handles incoming WebSocket messages for the project chat.
     *
     * @param message The incoming message string.
     */
    @OnMessage
    public void onMessage(String message) {
        System.out.println("Received message " + message);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        ChatMessageDto chatMessageDto = gson.fromJson(message, ChatMessageDto.class);
        send(chatMessageDto, message);
    }

}
