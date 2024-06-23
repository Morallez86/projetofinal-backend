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
import com.fasterxml.jackson.core.io.CharTypes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
@ServerEndpoint("/websocket/projectChat/{token}")
public class ProjectChatSocket {

    @Inject
    ProjectDao projectDao;

    @Inject
    TokenDao tokenDao;

    @Inject
    UserDao userDao;

    private static final Map<String, Session> sessions = new HashMap<>();

    public void send(ChatMessageDto messageDto, String message) {
        ProjectEntity project = projectDao.findProjectById(messageDto.getProjectId());
        UserEntity userSender = userDao.findUserById(messageDto.getSenderId());
        List<UserEntity> usersFromProject = new ArrayList<>();
        List<Session> userSessions = new ArrayList<>();

        for (UserProjectEntity user : project.getUserProjects()) {
            if (user != null && user.getUser().getId()!=userSender.getId()) {
                usersFromProject.add(user.getUser());
            }
        }

        for (UserEntity user : usersFromProject) {
            List<TokenEntity> tokens = tokenDao.findTokensByUserId(user.getId());
            for (TokenEntity token : tokens) {
                if (token != null && token.isActiveToken()) {
                    Session userSession = sessions.get(token.getTokenValue());
                    if (userSession != null) {
                        userSessions.add(userSession);
                    }
                }
            }
        }

        for (Session session : userSessions) {
            try {
                session.getBasicRemote().sendObject(message);
            } catch (IOException e) {
                System.out.println("Something went wrong!");
            } catch (EncodeException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @OnOpen
    public void toDoOnOpen(Session session, @PathParam("token") String token) {
        System.out.println("A new WebSocketNotification session is opened for client with token: " + token);
        sessions.put(token, session);
    }

    @OnClose
    public void toDoOnClose(Session session, CloseReason reason) {
        sessions.values().removeIf(s -> s.equals(session));
        System.out.println("WebSocketNotification session closed: " + reason.getReasonPhrase());
    }

    @OnMessage
    public void toDoOnMessage(String message) throws NamingException {
        System.out.println("Received message " + message);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        ChatMessageDto chatMessageDto = gson.fromJson(message, ChatMessageDto.class);
        send(chatMessageDto,message);
    }

    @OnError
    public void toDoOnError(Session session, Throwable throwable) {
        System.err.println("Error in WebSocket session: " + throwable.getMessage());
        throwable.printStackTrace();
    }
}