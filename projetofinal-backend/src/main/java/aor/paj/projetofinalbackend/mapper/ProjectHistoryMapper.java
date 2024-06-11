package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.ProjectHistoryDto;
import aor.paj.projetofinalbackend.entity.ProjectHistoryEntity;
import aor.paj.projetofinalbackend.utils.HistoryType;

import java.util.stream.Collectors;

public class ProjectHistoryMapper {

    public static ProjectHistoryDto toDto(ProjectHistoryEntity entity) {
        ProjectHistoryDto dto = new ProjectHistoryDto();
        dto.setId(entity.getId());
        dto.setNewDescription(entity.getNewDescription());
        dto.setType(entity.getType().ordinal());
        dto.setTimestamp(entity.getTimestamp());
        dto.setUserId(entity.getUser().getId());
        dto.setProjectId(entity.getProject().getId());
        dto.setTaskId(entity.getTask().getId());
        return dto;
    }

    public static ProjectHistoryEntity toEntity(ProjectHistoryDto dto) {
        ProjectHistoryEntity entity = new ProjectHistoryEntity();
        entity.setId(dto.getId());
        entity.setNewDescription(dto.getNewDescription());
        if (dto.getType()==100 || dto.getType()==200 || dto.getType()==300 || dto.getType()==400 || dto.getType()==500) {
        entity.setType(HistoryType.fromValue(dto.getType()));
        }
        if (dto.getTimestamp() != null) {
        entity.setTimestamp(dto.getTimestamp());
        }
        return entity;
    }
}
