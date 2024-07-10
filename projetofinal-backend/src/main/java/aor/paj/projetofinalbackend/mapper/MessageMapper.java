package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.MessageDto;
import aor.paj.projetofinalbackend.entity.MessageEntity;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper class for converting between MessageEntity and MessageDto.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public class MessageMapper {

    /**
     * Converts a MessageEntity to a MessageDto.
     *
     * @param entity The MessageEntity to convert.
     * @return The corresponding MessageDto.
     */
    public static MessageDto entityToDto(MessageEntity entity) {
        MessageDto dto = new MessageDto();
        dto.setId(entity.getId());
        dto.setTimestamp(entity.getTimestamp());
        dto.setContent(entity.getContent());
        dto.setSenderId(entity.getSender().getId());
        dto.setSenderUsername(entity.getSender().getUsername());
        dto.setReceiverId(entity.getReceiver().getId());
        dto.setReceiverUsername(entity.getReceiver().getUsername());
        dto.setSeen(entity.getSeen());
        return dto;
    }

    /**
     * Converts a list of MessageEntity objects to a list of MessageDto objects.
     *
     * @param entities The list of MessageEntity objects to convert.
     * @return A list of MessageDto objects corresponding to the input list of MessageEntity objects.
     */
    public static List<MessageDto> listToDto(List<MessageEntity> entities) {
        return entities.stream().map(MessageMapper::entityToDto).collect(Collectors.toList());
    }

    /**
     * Converts a MessageDto to a MessageEntity.
     *
     * @param dto The MessageDto to convert.
     * @return The corresponding MessageEntity.
     */
    public static MessageEntity dtoToEntity(MessageDto dto) {
        MessageEntity entity = new MessageEntity();
        entity.setId(dto.getId());
        entity.setTimestamp(dto.getTimestamp());
        entity.setContent(dto.getContent());

        return entity;
    }

    /**
     * Converts a list of MessageDto objects to a list of MessageEntity objects.
     *
     * @param dtos The list of MessageDto objects to convert.
     * @return A list of MessageEntity objects corresponding to the input list of MessageDto objects.
     */
    public static List<MessageEntity> listToEntity(List<MessageDto> dtos) {
        return dtos.stream().map(MessageMapper::dtoToEntity).collect(Collectors.toList());
    }
}
