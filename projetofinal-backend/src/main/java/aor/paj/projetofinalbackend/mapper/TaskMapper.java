package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.TaskDto;
import aor.paj.projetofinalbackend.entity.TaskEntity;
import aor.paj.projetofinalbackend.utils.TaskPriority;
import aor.paj.projetofinalbackend.utils.TaskStatus;

import java.util.List;
import java.util.stream.Collectors;

public class TaskMapper {

    private static final int MAX_RECURSION_DEPTH = 2;

    public static TaskDto toDto(TaskEntity entity) {
        return toDto(entity, 0);
    }

    private static TaskDto toDto(TaskEntity entity, int depth) {
        if (depth > MAX_RECURSION_DEPTH) {
            return null;
        }

        TaskDto dto = new TaskDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setPlannedStartingDate(entity.getPlannedStartingDate());
        dto.setStartingDate(entity.getStartingDate());
        dto.setPlannedEndingDate(entity.getPlannedEndingDate());
        dto.setEndingDate(entity.getEndingDate());
        dto.setStatus(entity.getStatus().getValue());
        dto.setPriority(entity.getPriority().getValue());
        dto.setContributors(entity.getContributors());
        dto.setUserId(entity.getUser().getId());
        dto.setDependencies(mapDependencies(entity.getDependencies(), depth));
        dto.setDependentTasks(mapDependencies(entity.getDependentTasks(), depth));
        dto.setProjectId(entity.getProject().getId());
        dto.setUserName(entity.getUser().getUsername());
        return dto;
    }

    private static List<TaskDto> mapDependencies(List<TaskEntity> dependencies, int depth) {
        return dependencies.stream().map(dep -> toDto(dep, depth + 1)).collect(Collectors.toList());
    }

    public static TaskEntity toEntity(TaskDto dto) {
        return toEntity(dto, 0);
    }

    private static TaskEntity toEntity(TaskDto dto, int depth) {
        if (depth > MAX_RECURSION_DEPTH) {
            return null;
        }

        TaskEntity entity = new TaskEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setPlannedStartingDate(dto.getPlannedStartingDate());
        entity.setStartingDate(dto.getStartingDate());
        entity.setPlannedEndingDate(dto.getPlannedEndingDate());
        entity.setEndingDate(dto.getEndingDate());
        entity.setStatus(TaskStatus.fromValue(dto.getStatus()));
        entity.setPriority(TaskPriority.fromValue(dto.getPriority()));
        entity.setContributors(dto.getContributors());
        entity.setDependencies(mapEntities(dto.getDependencies(), depth));
        entity.setDependentTasks(mapEntities(dto.getDependentTasks(), depth));
        return entity;
    }

    private static List<TaskEntity> mapEntities(List<TaskDto> dependencies, int depth) {
        return dependencies.stream().map(dep -> toEntity(dep, depth + 1)).collect(Collectors.toList());
    }
}
