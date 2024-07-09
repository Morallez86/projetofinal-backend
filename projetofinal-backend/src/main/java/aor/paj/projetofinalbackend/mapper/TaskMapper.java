package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.TaskDto;
import aor.paj.projetofinalbackend.entity.TaskEntity;
import aor.paj.projetofinalbackend.utils.TaskPriority;
import aor.paj.projetofinalbackend.utils.TaskStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class for converting between TaskEntity and TaskDto.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public class TaskMapper {

    /**
     * Maximum recursion depth for mapping dependencies and dependent tasks.
     */
    private static final int MAX_RECURSION_DEPTH = 2;

    /**
     * Converts a TaskEntity to a TaskDto.
     *
     * @param entity The TaskEntity to convert.
     * @return The corresponding TaskDto.
     */
    public static TaskDto toDto(TaskEntity entity) {
        return toDto(entity, 0);
    }

    /**
     * Converts a TaskEntity to a TaskDto with recursion handling.
     *
     * @param entity The TaskEntity to convert.
     * @param depth  The current recursion depth.
     * @return The corresponding TaskDto.
     */
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
        if (entity.getDependencies()!=null) {
       List<Long> longList = new ArrayList<>();
       for (TaskEntity entity1 : entity.getDependencies()) {
           System.out.println(entity1.getId());
           longList.add(entity1.getId());
       }
       dto.setDependencies(longList);
        }
        if (entity.getDependentTasks()!=null) {
            List<Long> longList2 = new ArrayList<>();
            for (TaskEntity entity1 : entity.getDependentTasks()) {
                longList2.add(entity1.getId());
            }
            dto.setDependentTasks(longList2);
        }
        dto.setProjectId(entity.getProject().getId());
        dto.setUserName(entity.getUser().getUsername());
        return dto;
    }

    /**
     * Converts a TaskDto to a TaskEntity.
     *
     * @param dto The TaskDto to convert.
     * @return The corresponding TaskEntity.
     */
    public static TaskEntity toEntity(TaskDto dto) {
        return toEntity(dto, 0);
    }

    /**
     * Converts a TaskDto to a TaskEntity with recursion handling.
     *
     * @param dto   The TaskDto to convert.
     * @param depth The current recursion depth.
     * @return The corresponding TaskEntity.
     */
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
        return entity;
    }
}
