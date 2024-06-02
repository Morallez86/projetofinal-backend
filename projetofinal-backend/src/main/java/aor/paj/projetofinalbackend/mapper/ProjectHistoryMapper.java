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
        dto.setUser(UserMapper.toDto(entity.getUser()));
        dto.setProject(ProjectMapper.toDto(entity.getProject()));
        return dto;
    }

    public static ProjectHistoryEntity toEntity(ProjectHistoryDto dto) {
        ProjectHistoryEntity entity = new ProjectHistoryEntity();
        entity.setNewDescription(dto.getNewDescription());
        entity.setType(HistoryType.values()[dto.getType()]);
        entity.setTimestamp(dto.getTimestamp());
        entity.setUser(UserMapper.toEntity(dto.getUser()));
        entity.setProject(ProjectMapper.toEntity(dto.getProject()));
        return entity;
    }
}
