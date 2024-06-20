package aor.paj.projetofinalbackend.websocket;

import aor.paj.projetofinalbackend.bean.MessageBean;
import aor.paj.projetofinalbackend.bean.NotificationBean;
import aor.paj.projetofinalbackend.bean.TokenBean;
import aor.paj.projetofinalbackend.dto.MessageDto;
import aor.paj.projetofinalbackend.dto.NotificationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
@ServerEndpoint("/websocket/application/{token}")
public class ApplicationSocket {

    @Inject
    private TokenBean tokenBean;

    @Inject
    private MessageBean messageBean;

    @Inject
    private NotificationBean notificationBean;

    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new JavaTimeModule());
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) {
        if (tokenBean.isTokenActive(token)) {
            sessions.put(token, session);
            System.out.println("WebSocket connection opened for token: " + token);
        } else {
            System.out.println("Invalid token. Connection rejected for token: " + token);
            try {
                session.close(new CloseReason(CloseReason.CloseCodes.CANNOT_ACCEPT, "Invalid token"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason reason, @PathParam("token") String token) {
        sessions.remove(token);
        System.out.println("WebSocket connection closed for token: " + token + " Reason: " + reason);
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        System.out.println("Received message: " + message);
        try {
            session.getBasicRemote().sendText("ack");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendNotification(String token, NotificationDto notification) {
        try {
            String notificationJson = mapper.writeValueAsString(notification);
            Session session = sessions.get(token);
            if (session != null && session.isOpen()) {
                session.getBasicRemote().sendText(notificationJson);
                System.out.println("Notification sent: " + notificationJson);
            } else {
                System.out.println("Session not found or closed for token: " + token);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessage(String token, MessageDto message) {
        try {
            String messageJson = mapper.writeValueAsString(message);
            Session session = sessions.get(token);
            if (session != null && session.isOpen()) {
                session.getBasicRemote().sendText(messageJson);
                System.out.println("Message sent: " + messageJson);
            } else {
                System.out.println("Session not found or closed for token: " + token);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
