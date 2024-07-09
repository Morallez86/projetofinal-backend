package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.ChatMessageEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;

/**
 * Data Access Object (DAO) for managing ChatMessageEntity entities.
 * Extends AbstractDao providing basic CRUD operations and additional methods specific to ChatMessageEntity.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Stateless
public class ChatMessageDao extends AbstractDao<ChatMessageEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs the ChatMessageDao with the entity class ChatMessageEntity.
     */
    public ChatMessageDao() {
        super(ChatMessageEntity.class);
    }

    /**
     * Retrieves a ChatMessageEntity by projectId, timestamp, and senderId.
     *
     * @param projectId The ID of the project associated with the chat message.
     * @param timestamp The timestamp of the chat message.
     * @param senderId  The ID of the user who sent the chat message.
     * @return The ChatMessageEntity matching the specified projectId, timestamp, and senderId, or null if not found.
     */
    public ChatMessageEntity findChatMessage(Long projectId, LocalDateTime timestamp, Long senderId) {
        TypedQuery<ChatMessageEntity> query = em.createNamedQuery(
                "ChatMessageEntity.findByProjectIdTimestampSenderId", ChatMessageEntity.class);
        query.setParameter("projectId", projectId);
        query.setParameter("timestamp", timestamp);
        query.setParameter("senderId", senderId);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
