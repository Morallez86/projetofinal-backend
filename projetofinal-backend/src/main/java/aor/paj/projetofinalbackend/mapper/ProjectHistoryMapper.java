package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.ProjectHistoryDto;
import aor.paj.projetofinalbackend.entity.ProjectHistoryEntity;
import aor.paj.projetofinalbackend.utils.HistoryType;

public class ProjectHistoryMapper {

    public static ProjectHistoryEntity toEntity (ProjectHistoryDto dto) {
        ProjectHistoryEntity entity = new ProjectHistoryEntity();
        entity.setId(dto.getId());
        entity.setNewDescription(dto.getNewDescription());
        for (HistoryType history : HistoryType.values()) {
            if (history.getCode() == dto.getType()) {
                entity.setType(history);
                break;
            }
        }
        entity.setTimestamp(dto.getTimestamp());
        entity.setProject(ProjectMapper.toEntity(dto.getProject()));
        entity.setUser(UserMapper.toEntity(dto.getUser()));
        return entity;
    }
}
