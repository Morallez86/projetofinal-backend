package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.NotificationDto;
import aor.paj.projetofinalbackend.entity.NotificationEntity;
import aor.paj.projetofinalbackend.utils.NotificationType;

import java.util.List;
import java.util.stream.Collectors;

public class NotificationMapper {

    public static NotificationDto entityToDto(NotificationEntity entity) {
        NotificationDto dto = new NotificationDto();
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setType(entity.getType().name());
        dto.setSeen(entity.isSeen());
        dto.setTimestamp(entity.getTimestamp());
        dto.setReceiverId(entity.getSender().getId());
        dto.setSenderId(entity.getSender().getId());
        dto.setSenderUsername(entity.getSender().getUsername());
        dto.setProjectId(entity.getProject().getId());
        dto.setProjectTitle(entity.getProject().getTitle());
        return dto;
    }

    public static List<NotificationDto> listToDto(List<NotificationEntity> entities) {
        return entities.stream().map(NotificationMapper::entityToDto).collect(Collectors.toList());
    }

    public static NotificationEntity dtoToEntity(NotificationDto dto) {
        NotificationEntity entity = new NotificationEntity();
        entity.setDescription(dto.getDescription());
        entity.setType(NotificationType.valueOf(dto.getType()));
        entity.setSeen(dto.isSeen());
        entity.setTimestamp(dto.getTimestamp());
        return entity;
    }

    public static List<NotificationEntity> listToEntity(List<NotificationDto> dtos) {
        return dtos.stream().map(NotificationMapper::dtoToEntity).collect(Collectors.toList());
    }
}
