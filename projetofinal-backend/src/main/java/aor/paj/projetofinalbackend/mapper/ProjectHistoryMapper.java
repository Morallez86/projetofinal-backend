package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.ProjectHistoryDto;
import aor.paj.projetofinalbackend.entity.ProjectHistoryEntity;
import aor.paj.projetofinalbackend.utils.HistoryType;

import java.util.stream.Collectors;

public class ProjectHistoryMapper {

    public static ProjectHistoryDto toDto(ProjectHistoryEntity entity) {
        ProjectHistoryDto dto = new ProjectHistoryDto();
        dto.setId(entity.getId());
        if (entity.getNewDescription()!=null) {
            dto.setNewDescription(entity.getNewDescription());
        }
        dto.setType(entity.getType().getValue());
        dto.setTimestamp(entity.getTimestamp());
        dto.setUserId(entity.getUser().getId());
        dto.setProjectId(entity.getProject().getId());
        if (entity.getTask()!=null) {
        dto.setTaskId(entity.getTask().getId());
        dto.setTaskName(entity.getTask().getTitle());}
        if (entity.getUser().getUsername() != null) {
            dto.setUserName(entity.getUser().getUsername());
        } else {
            dto.setUserName(entity.getUser().getEmail());
        }
        if (entity.getTitle()!=null) {
            dto.setTitle(entity.getTitle());
        }

        return dto;
    }

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
