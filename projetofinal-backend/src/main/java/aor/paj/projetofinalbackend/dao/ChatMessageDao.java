package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.ChatMessageEntity;
import aor.paj.projetofinalbackend.entity.MessageEntity;
import jakarta.ejb.Stateless;

@Stateless
public class ChatMessageDao extends AbstractDao<ChatMessageEntity> {

    private static final long serialVersionUID = 1L;

    public ChatMessageDao() {
        super(ChatMessageEntity.class);
    }
}
