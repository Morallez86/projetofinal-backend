package aor.paj.projetofinalbackend.websocket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import java.util.HashMap;
import java.util.Map;

@Singleton
@ServerEndpoint("/websocket/projectChat/{token}")
public class ProjectChatSocket {

    private static final Map<String, Session> sessions = new HashMap<>();

    public void send(String token, String message) {
        Session session = sessions.get(token);
        if (session != null && session.isOpen()) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
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
    public void toDoOnMessage(String message, @PathParam("token") String token) throws NamingException {
        System.out.println("Received message from token " + token + ": " + message);
        // Adicione lógica para processar a mensagem recebida, se necessário
    }

    @OnError
    public void toDoOnError(Session session, Throwable throwable) {
        System.err.println("Error in WebSocket session: " + throwable.getMessage());
        throwable.printStackTrace();
    }
}