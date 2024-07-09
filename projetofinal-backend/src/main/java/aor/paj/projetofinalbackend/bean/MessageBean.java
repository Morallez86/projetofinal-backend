package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.MessageDao;
import aor.paj.projetofinalbackend.dao.UserDao;
import aor.paj.projetofinalbackend.dto.MessageDto;
import aor.paj.projetofinalbackend.entity.MessageEntity;
import aor.paj.projetofinalbackend.entity.TokenEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.mapper.MessageMapper;
import aor.paj.projetofinalbackend.websocket.ApplicationSocket;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Bean class that handles operations related to user messages.
 *
 * @see MessageDao
 * @see UserDao
 * @see MessageDto
 * @see MessageEntity
 * @see UserEntity
 * @see TokenEntity
 * @see MessageMapper
 * @see ApplicationSocket
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Stateless
public class MessageBean {

    @Inject
    private MessageDao messageDao;

    @Inject
    private UserDao userDao;

    /**
     * Retrieves the received messages for a user with optional filtering by sender username and content.
     *
     * @param userId the ID of the user
     * @param page the page number for pagination
     * @param limit the number of messages per page
     * @param username the username of the sender to filter by
     * @param content the content to filter by
     * @return a list of MessageDto representing the received messages
     */
    public List<MessageDto> getReceivedMessagesForUser(Long userId, int page, int limit, String username, String content) {
        int offset = (page - 1) * limit;
        List<MessageEntity> messages;

        if ((username != null && !username.isEmpty()) || (content != null && !content.isEmpty())) {
            messages = messageDao.findReceivedMessagesByUserIdAndUsernameAndContent(userId, username, content, offset, limit);
        } else {
            messages = messageDao.findReceivedMessagesByUserId(userId, offset, limit);
        }

        return MessageMapper.listToDto(messages);
    }

    /**
     * Retrieves the sent messages for a user with optional filtering by receiver username and content.
     *
     * @param userId the ID of the user
     * @param page the page number for pagination
     * @param limit the number of messages per page
     * @param username the username of the receiver to filter by
     * @param content the content to filter by
     * @return a list of MessageDto representing the sent messages
     */
    public List<MessageDto> getSentMessagesForUser(Long userId, int page, int limit, String username, String content) {
        int offset = (page - 1) * limit;
        List<MessageEntity> messages;

        if ((username != null && !username.isEmpty()) || (content != null && !content.isEmpty())) {
            messages = messageDao.findSentMessagesByUserIdAndUsernameAndContent(userId, username, content, offset, limit);
        } else {
            messages = messageDao.findSentMessagesByUserId(userId, offset, limit);
        }

        return MessageMapper.listToDto(messages);
    }

    /**
     * Retrieves the unread messages for a user with optional filtering by sender username and content.
     *
     * @param userId the ID of the user
     * @param page the page number for pagination
     * @param limit the number of messages per page
     * @param username the username of the sender to filter by
     * @param content the content to filter by
     * @return a list of MessageDto representing the unread messages
     */
    public List<MessageDto> getUnreadMessagesForUser(Long userId, int page, int limit, String username, String content) {
        int offset = (page - 1) * limit;
        List<MessageEntity> messages;

        if ((username != null && !username.isEmpty()) || (content != null && !content.isEmpty())) {
            messages = messageDao.findUnreadMessagesByUserIdAndUsernameAndContent(userId, username, content, offset, limit);
        } else {
            messages = messageDao.findUnreadMessagesByUserId(userId, offset, limit);
        }

        return MessageMapper.listToDto(messages);
    }

    /**
     * Gets the total count of received messages for a user with optional filtering by sender username and content.
     *
     * @param userId the ID of the user
     * @param username the username of the sender to filter by
     * @param content the content to filter by
     * @return the total count of received messages
     */
    public int getTotalReceivedMessagesForUser(Long userId, String username, String content) {
        if ((username != null && !username.isEmpty()) || (content != null && !content.isEmpty())) {
            return messageDao.countReceivedMessagesByUserIdAndUsernameAndContent(userId, username, content);
        } else {
            return messageDao.countReceivedMessagesByUserId(userId);
        }
    }

    /**
     * Gets the total count of sent messages for a user with optional filtering by receiver username and content.
     *
     * @param userId the ID of the user
     * @param username the username of the receiver to filter by
     * @param content the content to filter by
     * @return the total count of sent messages
     */
    public int getTotalSentMessagesForUser(Long userId, String username, String content) {
        if ((username != null && !username.isEmpty()) || (content != null && !content.isEmpty())) {
            return messageDao.countSentMessagesByUserIdAndUsernameAndContent(userId, username, content);
        } else {
            return messageDao.countSentMessagesByUserId(userId);
        }
    }

    /**
     * Gets the total count of unread messages for a user with optional filtering by sender username and content.
     *
     * @param userId the ID of the user
     * @param username the username of the sender to filter by
     * @param content the content to filter by
     * @return the total count of unread messages
     */
    public int getTotalUnreadMessagesForUser(Long userId, String username, String content) {
        if ((username != null && !username.isEmpty()) || (content != null && !content.isEmpty())) {
            return messageDao.countUnreadMessagesByUserIdAndUsernameAndContent(userId, username, content);
        } else {
            return messageDao.countUnreadMessagesByUserId(userId);
        }
    }

    /**
     * Adds a new message to the system.
     *
     * @param messageDto the MessageDto representing the message to add
     * @return the added MessageEntity
     */
    public MessageEntity addMessage(MessageDto messageDto) {
        try {
            // Fetch sender and recipient from database
            UserEntity sender = userDao.findUserById(messageDto.getSenderId());
            UserEntity receiver = userDao.findUserById(messageDto.getReceiverId());

            if (sender == null || receiver == null) {
                throw new IllegalArgumentException("Sender or receiver not found");
            }

            MessageEntity messageEntity = MessageMapper.dtoToEntity(messageDto);
            messageEntity.setSender(sender);
            messageEntity.setReceiver(receiver);
            messageEntity.setSeen(false);
            messageEntity.setTimestamp(LocalDateTime.now());

            messageDao.persist(messageEntity);

            List<TokenEntity> activeTokens = receiver.getTokens().stream()
                    .filter(TokenEntity::isActiveToken)
                    .collect(Collectors.toList());

            activeTokens.forEach(token -> ApplicationSocket.sendNotification(token.getTokenValue(), "message"));

            return messageEntity;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Updates the seen status of the specified messages and sends a notification to the sender.
     *
     * @param messageIds the list of message IDs to update
     * @param seen the new seen status
     * @param sender the UserEntity representing the sender
     */
    public void updateSeenStatus(List<Long> messageIds, boolean seen, UserEntity sender) {

        messageDao.updateSeenStatus(messageIds, seen);

        List<TokenEntity> activeTokens = sender.getTokens().stream()
                .filter(TokenEntity::isActiveToken)
                .collect(Collectors.toList());

        activeTokens.forEach(token -> ApplicationSocket.sendNotification(token.getTokenValue(), "refresh"));
    }
}
