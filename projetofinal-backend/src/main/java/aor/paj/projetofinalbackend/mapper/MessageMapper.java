package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.MessageDto;
import aor.paj.projetofinalbackend.entity.MessageEntity;

import java.util.List;
import java.util.stream.Collectors;

public class MessageMapper {

    public static MessageDto entityToDto(MessageEntity entity) {
        MessageDto dto = new MessageDto();
        dto.setId(entity.getId());
        dto.setTimestamp(entity.getTimestamp());
        dto.setContent(entity.getContent());
        dto.setSenderId(entity.getSender().getId());
        dto.setReceiverId(entity.getReceiver().getId());
        dto.setSeen(entity.getSeen());
        return dto;
    }

    public static List<MessageDto> listToDto(List<MessageEntity> entities) {
        return entities.stream().map(MessageMapper::entityToDto).collect(Collectors.toList());
    }

    public static MessageEntity dtoToEntity(MessageDto dto) {
        MessageEntity entity = new MessageEntity();
        entity.setId(dto.getId());
        entity.setTimestamp(dto.getTimestamp());
        entity.setContent(dto.getContent());
        // Sender and receiver should be set separately in the service/bean
        return entity;
    }

    public static List<MessageEntity> listToEntity(List<MessageDto> dtos) {
        return dtos.stream().map(MessageMapper::dtoToEntity).collect(Collectors.toList());
    }
}
