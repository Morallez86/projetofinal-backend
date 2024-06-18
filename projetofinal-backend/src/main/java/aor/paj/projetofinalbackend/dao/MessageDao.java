package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.MessageEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;

import java.util.List;

@Stateless
public class MessageDao extends AbstractDao<MessageEntity> {
    private static final long serialVersionUID = 1L;

    public MessageDao() {
        super(MessageEntity.class);
    }

    public List<MessageEntity> findReceivedMessagesByUserId(Long userId, int offset, int limit) {
        try {
            return em.createNamedQuery("Message.findReceivedMessagesByUserId", MessageEntity.class)
                    .setParameter("userId", userId)
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<MessageEntity> findSentMessagesByUserId(Long userId, int offset, int limit) {
        try {
            return em.createNamedQuery("Message.findSentMessagesByUserId", MessageEntity.class)
                    .setParameter("userId", userId)
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<MessageEntity> findUnreadMessagesByUserId(Long userId, int offset, int limit) {
        try {
            return em.createNamedQuery("Message.findUnreadMessagesByUserId", MessageEntity.class)
                    .setParameter("userId", userId)
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<MessageEntity> findReceivedMessagesByUserIdAndUsernameAndContent(Long userId, String username, String content, int offset, int limit) {
        try {
            return em.createNamedQuery("Message.findReceivedMessagesByUserIdAndUsernameAndContent", MessageEntity.class)
                    .setParameter("userId", userId)
                    .setParameter("username", username)
                    .setParameter("content", content)
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<MessageEntity> findSentMessagesByUserIdAndUsernameAndContent(Long userId, String username, String content, int offset, int limit) {
        try {
            return em.createNamedQuery("Message.findSentMessagesByUserIdAndUsernameAndContent", MessageEntity.class)
                    .setParameter("userId", userId)
                    .setParameter("username", username)
                    .setParameter("content", content)
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<MessageEntity> findUnreadMessagesByUserIdAndUsernameAndContent(Long userId, String username, String content, int offset, int limit) {
        try {
            return em.createNamedQuery("Message.findUnreadMessagesByUserIdAndUsernameAndContent", MessageEntity.class)
                    .setParameter("userId", userId)
                    .setParameter("username", username)
                    .setParameter("content", content)
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public int countReceivedMessagesByUserId(Long userId) {
        try {
            return ((Number) em.createNamedQuery("Message.countReceivedMessagesByUserId")
                    .setParameter("userId", userId)
                    .getSingleResult()).intValue();
        } catch (NoResultException e) {
            return 0;
        }
    }

    public int countSentMessagesByUserId(Long userId) {
        try {
            return ((Number) em.createNamedQuery("Message.countSentMessagesByUserId")
                    .setParameter("userId", userId)
                    .getSingleResult()).intValue();
        } catch (NoResultException e) {
            return 0;
        }
    }

    public int countUnreadMessagesByUserId(Long userId) {
        try {
            return ((Number) em.createNamedQuery("Message.countUnreadMessagesByUserId")
                    .setParameter("userId", userId)
                    .getSingleResult()).intValue();
        } catch (NoResultException e) {
            return 0;
        }
    }

    public int countReceivedMessagesByUserIdAndUsernameAndContent(Long userId, String username, String content) {
        try {
            return ((Number) em.createNamedQuery("Message.countReceivedMessagesByUserIdAndUsernameAndContent")
                    .setParameter("userId", userId)
                    .setParameter("username", username)
                    .setParameter("content", content)
                    .getSingleResult()).intValue();
        } catch (NoResultException e) {
            return 0;
        }
    }

    public int countSentMessagesByUserIdAndUsernameAndContent(Long userId, String username, String content) {
        try {
            return ((Number) em.createNamedQuery("Message.countSentMessagesByUserIdAndUsernameAndContent")
                    .setParameter("userId", userId)
                    .setParameter("username", username)
                    .setParameter("content", content)
                    .getSingleResult()).intValue();
        } catch (NoResultException e) {
            return 0;
        }
    }

    public int countUnreadMessagesByUserIdAndUsernameAndContent(Long userId, String username, String content) {
        try {
            return ((Number) em.createNamedQuery("Message.countUnreadMessagesByUserIdAndUsernameAndContent")
                    .setParameter("userId", userId)
                    .setParameter("username", username)
                    .setParameter("content", content)
                    .getSingleResult()).intValue();
        } catch (NoResultException e) {
            return 0;
        }
    }

    public void updateSeenStatus(List<Long> messageIds, boolean newStatus) {
        em.createNamedQuery("Message.updateSeenStatusByIds")
                .setParameter("seen", newStatus)
                .setParameter("ids", messageIds)
                .executeUpdate();
    }
}
