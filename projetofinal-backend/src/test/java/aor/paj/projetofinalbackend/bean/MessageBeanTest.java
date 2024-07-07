package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.bean.MessageBean;
import aor.paj.projetofinalbackend.dao.MessageDao;
import aor.paj.projetofinalbackend.dao.UserDao;
import aor.paj.projetofinalbackend.dto.MessageDto;
import aor.paj.projetofinalbackend.entity.MessageEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.websocket.ApplicationSocket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class MessageBeanTest {

    @InjectMocks
    private MessageBean messageBean;

    @Mock
    private MessageDao messageDao;

    @Mock
    private UserDao userDao;

    @Mock
    private ApplicationSocket applicationSocket;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddMessage() {
        // Mock data
        MessageDto messageDto = new MessageDto();
        messageDto.setSenderId(1L);
        messageDto.setReceiverId(2L);

        UserEntity sender = new UserEntity();
        sender.setId(1L);
        sender.setUsername("sender");

        UserEntity receiver = new UserEntity();
        receiver.setId(2L);
        receiver.setUsername("receiver");

        when(userDao.findUserById(1L)).thenReturn(sender);
        when(userDao.findUserById(2L)).thenReturn(receiver);

        // Mock para o método persist que não retorna valor
        doNothing().when(messageDao).persist(any(MessageEntity.class));

        // Test
        MessageEntity addedMessage = messageBean.addMessage(messageDto);

        // Verify
        assertEquals(sender, addedMessage.getSender());
        assertEquals(receiver, addedMessage.getReceiver());
        assertEquals(false, addedMessage.getSeen());

        // Verifica se o método persist foi chamado exatamente uma vez com qualquer MessageEntity
        verify(messageDao, times(1)).persist(any(MessageEntity.class));

        // Verifica se o método sendNotification foi chamado exatamente uma vez com qualquer string e "message"
        verify(applicationSocket, times(1)).sendNotification(anyString(), eq("message"));
    }
    
}

