package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.TaskDto;
import aor.paj.projetofinalbackend.entity.TaskEntity;
import aor.paj.projetofinalbackend.utils.TaskPriority;
import aor.paj.projetofinalbackend.utils.TaskStatus;

import java.util.stream.Collectors;

public class TaskMapper {

    public static TaskDto toDto(TaskEntity entity) {
        TaskDto dto = new TaskDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setPlannedStartingDate(entity.getPlannedStartingDate());
        dto.setStartingDate(entity.getStartingDate());
        dto.setPlannedEndingDate(entity.getPlannedEndingDate());
        dto.setEndingDate(entity.getEndingDate());
        dto.setStatus(entity.getStatus().ordinal());
        dto.setPriority(entity.getPriority().getValue());
        dto.setContributors(entity.getContributors());
        dto.setUser(UserMapper.toDto(entity.getUser()));
        dto.setDependencies(entity.getDependencies().stream().map(TaskMapper::toDto).collect(Collectors.toList()));
        dto.setDependentTasks(entity.getDependentTasks().stream().map(TaskMapper::toDto).collect(Collectors.toList()));
        dto.setProject(ProjectMapper.toDto(entity.getProject()));
        return dto;
    }

    public static TaskEntity toEntity(TaskDto dto) {
        TaskEntity entity = new TaskEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setPlannedStartingDate(dto.getPlannedStartingDate());
        entity.setStartingDate(dto.getStartingDate());
        entity.setPlannedEndingDate(dto.getPlannedEndingDate());
        entity.setEndingDate(dto.getEndingDate());
        entity.setStatus(TaskStatus.values()[dto.getStatus()]);
        entity.setPriority(TaskPriority.fromValue(dto.getPriority()));
        entity.setContributors(dto.getContributors());
        entity.setUser(UserMapper.toEntity(dto.getUser()));
        entity.setDependencies(dto.getDependencies().stream().map(TaskMapper::toEntity).collect(Collectors.toList()));
        entity.setDependentTasks(dto.getDependentTasks().stream().map(TaskMapper::toEntity).collect(Collectors.toList()));
        entity.setProject(ProjectMapper.toEntity(dto.getProject()));
        return entity;
    }
}
