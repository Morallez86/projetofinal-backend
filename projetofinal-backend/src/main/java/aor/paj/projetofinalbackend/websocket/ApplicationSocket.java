package aor.paj.projetofinalbackend.websocket;

import aor.paj.projetofinalbackend.bean.TokenBean;
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

/**
 * WebSocket endpoint for handling application-specific communication.
 * This endpoint manages WebSocket connections based on a provided token.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Singleton
@ServerEndpoint("/websocket/application/{token}")
public class ApplicationSocket {

    @Inject
    private TokenBean tokenBean;

    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new JavaTimeModule());
    }

    /**
     * Handles WebSocket connection opening.
     *
     * @param session The WebSocket session object.
     * @param token   The token path parameter used for authentication.
     */
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

    /**
     * Handles WebSocket connection closure.
     *
     * @param session The WebSocket session object.
     * @param reason The reason for the connection closure.
     * @param token The token path parameter used for authentication.
     */
    @OnClose
    public void onClose(Session session, CloseReason reason, @PathParam("token") String token) {
        sessions.remove(token);
        System.out.println("WebSocket connection closed for token: " + token + " Reason: " + reason);
    }

    /**
     * Handles incoming WebSocket messages.
     *
     * @param session The WebSocket session object.
     * @param message The incoming message string.
     */
    @OnMessage
    public void onMessage(Session session, String message) {
        System.out.println("Received message: " + message);
        try {
            session.getBasicRemote().sendText("ack");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to send notifications to WebSocket clients.
     *
     * @param token The token identifying the WebSocket session.
     * @param notificationType The type of notification to send.
     */
    public static void sendNotification(String token, String notificationType) {
        try {
            // You can construct a simple JSON or text message indicating the event
            String notificationJson = "{\"type\": \"" + notificationType + "\"}";
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
}
