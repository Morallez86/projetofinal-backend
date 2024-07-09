package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.NotificationDto;
import aor.paj.projetofinalbackend.entity.NotificationEntity;
import aor.paj.projetofinalbackend.entity.UserNotificationEntity;
import aor.paj.projetofinalbackend.utils.NotificationType;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper class for converting between NotificationEntity and NotificationDto.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public class NotificationMapper {

    /**
     * Converts a NotificationEntity to a NotificationDto.
     *
     * @param entity The NotificationEntity to convert.
     * @return The corresponding NotificationDto.
     */
    public static NotificationDto entityToDto(NotificationEntity entity) {
        NotificationDto dto = new NotificationDto();
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setType(entity.getType().name());
        dto.setTimestamp(entity.getTimestamp());
        dto.setReceiverId(entity.getSender().getId());
        dto.setSenderId(entity.getSender().getId());
        dto.setSenderUsername(entity.getSender().getUsername());
        if(entity.getAction() !=null){
            dto.setAction(entity.getAction().name());
        }
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

    /**
     * Converts a list of NotificationEntity objects to a list of NotificationDto objects.
     *
     * @param entities The list of NotificationEntity objects to convert.
     * @return A list of NotificationDto objects corresponding to the input list of NotificationEntity objects.
     */
    public static List<NotificationDto> listToDto(List<NotificationEntity> entities) {
        return entities.stream().map(NotificationMapper::entityToDto).collect(Collectors.toList());
    }

    /**
     * Converts a NotificationDto to a NotificationEntity.
     *
     * @param dto The NotificationDto to convert.
     * @return The corresponding NotificationEntity.
     */
    public static NotificationEntity dtoToEntity(NotificationDto dto) {
        NotificationEntity entity = new NotificationEntity();
        entity.setDescription(dto.getDescription());
        entity.setType(NotificationType.valueOf(dto.getType()));
        entity.setTimestamp(dto.getTimestamp());
        return entity;
    }

    /**
     * Converts a list of NotificationDto objects to a list of NotificationEntity objects.
     *
     * @param dtos The list of NotificationDto objects to convert.
     * @return A list of NotificationEntity objects corresponding to the input list of NotificationDto objects.
     */
    public static List<NotificationEntity> listToEntity(List<NotificationDto> dtos) {
        return dtos.stream().map(NotificationMapper::dtoToEntity).collect(Collectors.toList());
    }
}
