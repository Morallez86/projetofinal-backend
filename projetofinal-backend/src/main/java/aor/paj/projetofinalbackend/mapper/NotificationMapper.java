package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.NotificationDto;
import aor.paj.projetofinalbackend.entity.NotificationEntity;
import aor.paj.projetofinalbackend.entity.UserNotificationEntity;
import aor.paj.projetofinalbackend.utils.NotificationType;

import java.util.List;
import java.util.stream.Collectors;

public class NotificationMapper {

    public static NotificationDto entityToDto(NotificationEntity entity) {
        NotificationDto dto = new NotificationDto();
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setType(entity.getType().name());
        dto.setTimestamp(entity.getTimestamp());
        dto.setReceiverId(entity.getSender().getId());
        dto.setSenderId(entity.getSender().getId());
        dto.setSenderUsername(entity.getSender().getUsername());
        if(entity.getProject() != null){
            dto.setProjectId(entity.getProject().getId());
            dto.setProjectTitle(entity.getProject().getTitle());
        }
        dto.setSeen(entity.getUserNotifications().stream()
                .filter(un -> un.getNotification().getId().equals(entity.getId()))
                .findFirst()
                .map(UserNotificationEntity::isSeen)
                .orElse(false));
        return dto;
    }

    public static List<NotificationDto> listToDto(List<NotificationEntity> entities) {
        return entities.stream().map(NotificationMapper::entityToDto).collect(Collectors.toList());
    }

    public static NotificationEntity dtoToEntity(NotificationDto dto) {
        NotificationEntity entity = new NotificationEntity();
        entity.setDescription(dto.getDescription());
        entity.setType(NotificationType.valueOf(dto.getType()));
        entity.setTimestamp(dto.getTimestamp());
        return entity;
    }

    public static List<NotificationEntity> listToEntity(List<NotificationDto> dtos) {
        return dtos.stream().map(NotificationMapper::dtoToEntity).collect(Collectors.toList());
    }
}
