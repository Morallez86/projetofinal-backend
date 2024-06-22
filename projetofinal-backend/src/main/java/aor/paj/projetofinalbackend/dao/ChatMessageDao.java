package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.ChatMessageEntity;
import aor.paj.projetofinalbackend.entity.MessageEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class ChatMessageDao extends AbstractDao<ChatMessageEntity> {

    private static final long serialVersionUID = 1L;

    public ChatMessageDao() {
        super(ChatMessageEntity.class);
    }

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
