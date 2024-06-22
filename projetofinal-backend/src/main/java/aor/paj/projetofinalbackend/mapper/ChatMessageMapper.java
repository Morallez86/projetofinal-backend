package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.ChatMessageDto;
import aor.paj.projetofinalbackend.entity.ChatMessageEntity;

public class ChatMessageMapper {

    public static ChatMessageDto toDto(ChatMessageEntity entity) {
        ChatMessageDto dto = new ChatMessageDto();
        dto.setId(entity.getId());
        dto.setTimestamp(entity.getTimestamp());
        dto.setContent(entity.getContent());
        dto.setSenderId(entity.getSender().getId());
        dto.setSenderOnline(entity.getSender().getOnline());
        dto.setSenderUsername(entity.getSender().getUsername());
        dto.setProjectId(entity.getProject().getId());
        return dto;
    }

    public static ChatMessageEntity toEntity(ChatMessageDto dto) {
        ChatMessageEntity entity = new ChatMessageEntity();
        entity.setId(dto.getId());
        entity.setTimestamp(dto.getTimestamp());
        entity.setContent(dto.getContent());
        return entity;
    }
}
