package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.ChatMessageDto;
import aor.paj.projetofinalbackend.entity.ChatMessageEntity;

/**
 * Mapper class for converting between ChatMessageEntity and ChatMessageDto.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public class ChatMessageMapper {

    /**
     * Converts a ChatMessageEntity to a ChatMessageDto.
     *
     * @param entity The ChatMessageEntity to convert.
     * @return The corresponding ChatMessageDto.
     */
    public static ChatMessageDto toDto(ChatMessageEntity entity) {
        ChatMessageDto dto = new ChatMessageDto();
        dto.setId(entity.getId());
        if (entity.getTimestamp()!=null) {
        dto.setTimestamp(entity.getTimestamp());}
        dto.setContent(entity.getContent());
        dto.setSenderId(entity.getSender().getId());
        dto.setSenderOnline(entity.getSender().getOnline());
        dto.setSenderUsername(entity.getSender().getUsername());
        dto.setProjectId(entity.getProject().getId());
        return dto;
    }

    /**
     * Converts a ChatMessageDto to a ChatMessageEntity.
     *
     * @param dto The ChatMessageDto to convert.
     * @return The corresponding ChatMessageEntity.
     */
    public static ChatMessageEntity toEntity(ChatMessageDto dto) {
        ChatMessageEntity entity = new ChatMessageEntity();
        entity.setId(dto.getId());
        if (dto.getTimestamp()!=null) {
        entity.setTimestamp(dto.getTimestamp()); }
        entity.setContent(dto.getContent());
        return entity;
    }
}
