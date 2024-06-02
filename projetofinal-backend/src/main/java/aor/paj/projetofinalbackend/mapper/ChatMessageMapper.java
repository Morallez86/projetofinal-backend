package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.ChatMessageDto;
import aor.paj.projetofinalbackend.entity.ChatMessageEntity;

public class ChatMessageMapper {

    public static ChatMessageEntity toEntity (ChatMessageDto dto) {
        ChatMessageEntity entity = new ChatMessageEntity();
        entity.setId(dto.getId());
        entity.setTimestamp(dto.getTimestamp());
        entity.setContent(dto.getContent());
        entity.setSender(UserMapper.toEntity(dto.getSender()));
        return entity;
    }
}
