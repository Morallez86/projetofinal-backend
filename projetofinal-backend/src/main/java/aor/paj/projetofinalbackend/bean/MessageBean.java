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

@Stateless
public class MessageBean {

    @Inject
    MessageDao messageDao;

    @Inject
    UserDao userDao;


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

    public int getTotalReceivedMessagesForUser(Long userId, String username, String content) {
        if ((username != null && !username.isEmpty()) || (content != null && !content.isEmpty())) {
            return messageDao.countReceivedMessagesByUserIdAndUsernameAndContent(userId, username, content);
        } else {
            return messageDao.countReceivedMessagesByUserId(userId);
        }
    }

    public int getTotalSentMessagesForUser(Long userId, String username, String content) {
        if ((username != null && !username.isEmpty()) || (content != null && !content.isEmpty())) {
            return messageDao.countSentMessagesByUserIdAndUsernameAndContent(userId, username, content);
        } else {
            return messageDao.countSentMessagesByUserId(userId);
        }
    }

    public int getTotalUnreadMessagesForUser(Long userId, String username, String content) {
        if ((username != null && !username.isEmpty()) || (content != null && !content.isEmpty())) {
            return messageDao.countUnreadMessagesByUserIdAndUsernameAndContent(userId, username, content);
        } else {
            return messageDao.countUnreadMessagesByUserId(userId);
        }
    }

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

    public void updateSeenStatus(List<Long> messageIds, boolean seen) {
        messageDao.updateSeenStatus(messageIds, seen);
    }
}
