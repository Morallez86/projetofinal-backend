package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.TaskDto;
import aor.paj.projetofinalbackend.entity.TaskEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.utils.ListConverter;
import aor.paj.projetofinalbackend.utils.ProjectStatus;
import aor.paj.projetofinalbackend.utils.TaskPriority;
import aor.paj.projetofinalbackend.utils.TaskStatus;
import org.modelmapper.ModelMapper;

public class TaskMapper {

    public static TaskEntity toEntity (TaskDto dto) {
        TaskEntity entity = new TaskEntity();
        for (TaskStatus status : TaskStatus.values()) {
            if (status.getValue() == dto.getStatus()) {
                entity.setStatus(status);
                break;
            }
        }
        for (TaskPriority priority : TaskPriority.values()) {
            if (priority.getValue() == dto.getPriority()) {
                entity.setPriority(priority);
            }
        }
        entity.setUser(UserMapper.toEntity(dto.getUser()));
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setPlannedStartingDate(dto.getPlannedStartingDate());
        entity.setStartingDate(dto.getStartingDate());
        entity.setPlannedEndingDate(dto.getPlannedEndingDate());
        entity.setContributors(dto.getContributors());
        entity.setUser(UserMapper.toEntity(dto.getUser()));
        entity.setDependencies(ListConverter.convertListToSet(dto.getDependencies(), TaskMapper::toEntity));
        entity.setDependentTasks(ListConverter.convertListToSet(dto.getDependentTasks(), TaskMapper::toEntity));
        return entity;
    }
}
