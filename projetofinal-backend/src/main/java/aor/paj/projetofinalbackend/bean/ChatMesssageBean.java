package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.ChatMessageDao;
import aor.paj.projetofinalbackend.dao.ProjectDao;
import aor.paj.projetofinalbackend.dao.UserDao;
import aor.paj.projetofinalbackend.dto.ChatMessageDto;
import aor.paj.projetofinalbackend.entity.ChatMessageEntity;
import aor.paj.projetofinalbackend.entity.ProjectEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.mapper.ChatMessageMapper;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.time.LocalDateTime;

@Stateless
public class ChatMesssageBean {

   @EJB
    ChatMessageDao chatMessageDao;

   @EJB
    UserDao userDao;

   @EJB
    ProjectDao projectDao;

    public void createChatMsg (ChatMessageDto chatMessageDto) {
        ChatMessageEntity chatMessageEntity = ChatMessageMapper.toEntity(chatMessageDto);
        UserEntity user = userDao.findUserById(chatMessageDto.getSenderId());
        chatMessageEntity.setSender(user);
        ProjectEntity project = projectDao.findProjectById(chatMessageDto.getProjectId());
        chatMessageEntity.setProject(project);
        chatMessageEntity.setTimestamp(LocalDateTime.now());
        chatMessageDao.persist(chatMessageEntity);
    }
}
