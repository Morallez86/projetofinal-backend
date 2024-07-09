package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.MessageEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;

import java.util.List;

/**
 * Data Access Object (DAO) for managing MessageEntity entities.
 * Provides methods to perform CRUD operations and retrieve messages from the database.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Stateless
public class MessageDao extends AbstractDao<MessageEntity> {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs the MessageDao initializing with MessageEntity class.
     */
    public MessageDao() {
        super(MessageEntity.class);
    }

    /**
     * Retrieves received messages by user ID.
     *
     * @param userId The ID of the user whose received messages are to be retrieved.
     * @param offset The offset for pagination.
     * @param limit The maximum number of messages to retrieve.
     * @return A list of received MessageEntity objects for the specified user ID.
     */
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

    /**
     * Retrieves sent messages by user ID.
     *
     * @param userId The ID of the user whose sent messages are to be retrieved.
     * @param offset The offset for pagination.
     * @param limit The maximum number of messages to retrieve.
     * @return A list of sent MessageEntity objects for the specified user ID.
     */
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

    /**
     * Retrieves unread messages by user ID.
     *
     * @param userId The ID of the user whose unread messages are to be retrieved.
     * @param offset The offset for pagination.
     * @param limit The maximum number of messages to retrieve.
     * @return A list of unread MessageEntity objects for the specified user ID.
     */
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

    /**
     * Retrieves received messages by user ID, username, and message content.
     *
     * @param userId The ID of the user whose received messages are to be retrieved.
     * @param username The username to filter by.
     * @param content The message content to filter by.
     * @param offset The offset for pagination.
     * @param limit The maximum number of messages to retrieve.
     * @return A list of received MessageEntity objects that match the criteria.
     */
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

    /**
     * Retrieves sent messages by user ID, username, and message content.
     *
     * @param userId The ID of the user whose sent messages are to be retrieved.
     * @param username The username to filter by.
     * @param content The message content to filter by.
     * @param offset The offset for pagination.
     * @param limit The maximum number of messages to retrieve.
     * @return A list of sent MessageEntity objects that match the criteria.
     */
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

    /**
     * Retrieves unread messages by user ID, username, and message content.
     *
     * @param userId The ID of the user whose unread messages are to be retrieved.
     * @param username The username to filter by.
     * @param content The message content to filter by.
     * @param offset The offset for pagination.
     * @param limit The maximum number of messages to retrieve.
     * @return A list of unread MessageEntity objects that match the criteria.
     */
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

    /**
     * Counts received messages by user ID.
     *
     * @param userId The ID of the user whose received messages are to be counted.
     * @return The number of received messages for the specified user ID.
     */
    public int countReceivedMessagesByUserId(Long userId) {
        try {
            return ((Number) em.createNamedQuery("Message.countReceivedMessagesByUserId")
                    .setParameter("userId", userId)
                    .getSingleResult()).intValue();
        } catch (NoResultException e) {
            return 0;
        }
    }

    /**
     * Counts sent messages by user ID.
     *
     * @param userId The ID of the user whose sent messages are to be counted.
     * @return The number of sent messages for the specified user ID.
     */
    public int countSentMessagesByUserId(Long userId) {
        try {
            return ((Number) em.createNamedQuery("Message.countSentMessagesByUserId")
                    .setParameter("userId", userId)
                    .getSingleResult()).intValue();
        } catch (NoResultException e) {
            return 0;
        }
    }

    /**
     * Counts unread messages by user ID.
     *
     * @param userId The ID of the user whose unread messages are to be counted.
     * @return The number of unread messages for the specified user ID.
     */
    public int countUnreadMessagesByUserId(Long userId) {
        try {
            return ((Number) em.createNamedQuery("Message.countUnreadMessagesByUserId")
                    .setParameter("userId", userId)
                    .getSingleResult()).intValue();
        } catch (NoResultException e) {
            return 0;
        }
    }

    /**
     * Counts received messages by user ID, username, and message content.
     *
     * @param userId   The ID of the user whose received messages are to be counted.
     * @param username The username to filter by.
     * @param content  The message content to filter by.
     * @return The number of received messages that match the criteria.
     */
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

    /**
     * Counts sent messages by user ID, username, and message content.
     *
     * @param userId   The ID of the user whose sent messages are to be counted.
     * @param username The username to filter by.
     * @param content  The message content to filter by.
     * @return The number of sent messages that match the criteria.
     */
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

    /**
     * Counts unread messages by user ID, username, and message content.
     *
     * @param userId   The ID of the user whose unread messages are to be counted.
     * @param username The username to filter by.
     * @param content  The message content to filter by.
     * @return The number of unread messages that match the criteria.
     */
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

    /**
     * Updates the 'seen' status of messages by their IDs.
     *
     * @param messageIds The list of message IDs to update.
     * @param newStatus  The new 'seen' status to set for the messages.
     */
    public void updateSeenStatus(List<Long> messageIds, boolean newStatus) {
        em.createNamedQuery("Message.updateSeenStatusByIds")
                .setParameter("seen", newStatus)
                .setParameter("ids", messageIds)
                .executeUpdate();
    }
}
