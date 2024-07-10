package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.ProjectHistoryDto;
import aor.paj.projetofinalbackend.entity.ProjectHistoryEntity;
import aor.paj.projetofinalbackend.utils.HistoryType;

/**
 * Mapper class for converting between ProjectHistoryEntity and ProjectHistoryDto.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public class ProjectHistoryMapper {

    /**
     * Converts a ProjectHistoryEntity to a ProjectHistoryDto.
     *
     * @param entity The ProjectHistoryEntity to convert.
     * @return The corresponding ProjectHistoryDto.
     */
    public static ProjectHistoryDto toDto(ProjectHistoryEntity entity) {
        ProjectHistoryDto dto = new ProjectHistoryDto();
        dto.setId(entity.getId());
        if (entity.getNewDescription()!=null) {
            dto.setNewDescription(entity.getNewDescription());
        }
        dto.setType(entity.getType().getValue());
        dto.setTimestamp(entity.getTimestamp());
        if (entity.getUser()!=null) {
            dto.setUserId(entity.getUser().getId());
        }
        dto.setProjectId(entity.getProject().getId());
        if (entity.getTask()!=null) {
        dto.setTaskId(entity.getTask().getId());
        dto.setTaskName(entity.getTask().getTitle());}
        if (entity.getUser()!=null) {
            if (entity.getUser().getUsername() != null) {
                dto.setUserName(entity.getUser().getUsername());
            } else {
                dto.setUserName(entity.getUser().getEmail());
            }
        }
        if (entity.getTitle()!=null) {
            dto.setTitle(entity.getTitle());
        }

        return dto;
    }

    /**
     * Converts a ProjectHistoryDto to a ProjectHistoryEntity.
     *
     * @param dto The ProjectHistoryDto to convert.
     * @return The corresponding ProjectHistoryEntity.
     */
    public static ProjectHistoryEntity toEntity(ProjectHistoryDto dto) {
        ProjectHistoryEntity entity = new ProjectHistoryEntity();
        entity.setId(dto.getId());
        if (dto.getNewDescription()!=null) {
        entity.setNewDescription(dto.getNewDescription());}
        if (dto.getType()==100 || dto.getType()==200 || dto.getType()==300 || dto.getType()==400 || dto.getType()==500) {
        entity.setType(HistoryType.fromValue(dto.getType()));
        }
        if (dto.getTimestamp() != null) {
        entity.setTimestamp(dto.getTimestamp());
        }
        if (dto.getTitle() !=null) {
            entity.setTitle(dto.getTitle());
        }
        return entity;
    }
}
