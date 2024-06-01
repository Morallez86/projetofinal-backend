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

    private static ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper.typeMap(TaskDto.class, TaskEntity.class).addMappings(mapper -> {
            mapper.skip(TaskEntity::setStatus);
            mapper.skip(TaskEntity::setPriority);
            mapper.skip(TaskEntity::setUser);
            mapper.skip(TaskEntity::setDependencies);
            mapper.skip(TaskEntity::setDependentTasks);
            mapper.skip(TaskEntity::setProject);
        });
    }

    public static TaskEntity toEntity (TaskDto dto) {
        TaskEntity entity = modelMapper.map(dto,TaskEntity.class);
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
        entity.setDependencies(ListConverter.convertListToSet(dto.getDependencies(), TaskMapper::toEntity));
        entity.setDependentTasks(ListConverter.convertListToSet(dto.getDependentTasks(), TaskMapper::toEntity));
        entity.setProject(ProjectMapper.toEntity(dto.getProject()));
        return entity;
    }
}
