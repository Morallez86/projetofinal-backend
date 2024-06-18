package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.MessageDao;
import aor.paj.projetofinalbackend.dao.UserDao;
import aor.paj.projetofinalbackend.dto.MessageDto;
import aor.paj.projetofinalbackend.entity.MessageEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.mapper.MessageMapper;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class MessageBean {

    @Inject
    MessageDao messageDao;

    @Inject
    UserDao userDao;

    public List<MessageDto> getReceivedMessagesForUser(Long userId, int page, int limit, String username) {
        int offset = (page - 1) * limit;
        List<MessageEntity> messages;

        if (username != null && !username.isEmpty()) {
            messages = messageDao.findReceivedMessagesByUserIdAndUsername(userId, username, offset, limit);
        } else {
            messages = messageDao.findReceivedMessagesByUserId(userId, offset, limit);
        }

        return MessageMapper.listToDto(messages);
    }

    public List<MessageDto> getSentMessagesForUser(Long userId, int page, int limit, String username) {
        int offset = (page - 1) * limit;
        List<MessageEntity> messages;

        if (username != null && !username.isEmpty()) {
            messages = messageDao.findSentMessagesByUserIdAndUsername(userId, username, offset, limit);
        } else {
            messages = messageDao.findSentMessagesByUserId(userId, offset, limit);
        }

        return MessageMapper.listToDto(messages);
    }

    public List<MessageDto> getUnreadMessagesForUser(Long userId, int page, int limit, String username) {
        int offset = (page - 1) * limit;
        List<MessageEntity> messages;

        if (username != null && !username.isEmpty()) {
            messages = messageDao.findUnreadMessagesByUserIdAndUsername(userId, username, offset, limit);
        } else {
            messages = messageDao.findUnreadMessagesByUserId(userId, offset, limit);
        }

        return MessageMapper.listToDto(messages);
    }

    public int getTotalReceivedMessagesForUser(Long userId) {
        return messageDao.countReceivedMessagesByUserId(userId);
    }

    public int getTotalSentMessagesForUser(Long userId) {
        return messageDao.countSentMessagesByUserId(userId);
    }

    public int getTotalUnreadMessagesForUser(Long userId) {
        return messageDao.countUnreadMessagesByUserId(userId);
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
