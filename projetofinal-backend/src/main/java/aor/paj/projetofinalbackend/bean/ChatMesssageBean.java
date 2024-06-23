package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.ChatMessageDao;
import aor.paj.projetofinalbackend.dao.ProjectDao;
import aor.paj.projetofinalbackend.dao.UserDao;
import aor.paj.projetofinalbackend.dto.ChatMessageDto;
import aor.paj.projetofinalbackend.entity.ChatMessageEntity;
import aor.paj.projetofinalbackend.entity.ProjectEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.mapper.ChatMessageMapper;
import aor.paj.projetofinalbackend.utils.LocalDateTimeAdapter;
import aor.paj.projetofinalbackend.websocket.ProjectChatSocket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import javax.naming.NamingException;
import java.time.LocalDateTime;

@Stateless
public class ChatMesssageBean {

   @EJB
    ChatMessageDao chatMessageDao;

   @EJB
    UserDao userDao;

   @EJB
    ProjectDao projectDao;

   @Inject
    ProjectChatSocket projectChatSocket;

    public ChatMessageDto createChatMsg (ChatMessageDto chatMessageDto) throws NamingException {
        ChatMessageEntity chatMessageEntity = ChatMessageMapper.toEntity(chatMessageDto);
        UserEntity user = userDao.findUserById(chatMessageDto.getSenderId());
        chatMessageEntity.setSender(user);
        ProjectEntity project = projectDao.findProjectById(chatMessageDto.getProjectId());
        chatMessageEntity.setProject(project);
        chatMessageEntity.setTimestamp(LocalDateTime.now());
        chatMessageDao.persist(chatMessageEntity);
        ChatMessageEntity chatMessageSaved = chatMessageDao.findChatMessage(chatMessageEntity.getProject().getId(),chatMessageEntity.getTimestamp(),chatMessageEntity.getSender().getId());
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        String jsonMsg = gson.toJson(ChatMessageMapper.toDto(chatMessageSaved));
        projectChatSocket.toDoOnMessage(jsonMsg);
        return ChatMessageMapper.toDto(chatMessageSaved);
    }
}
