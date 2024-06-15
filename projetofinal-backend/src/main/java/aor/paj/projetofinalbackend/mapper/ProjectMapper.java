package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.*;
import aor.paj.projetofinalbackend.entity.*;
import aor.paj.projetofinalbackend.utils.ProjectStatus;

import java.util.stream.Collectors;

public class ProjectMapper {
    public static ProjectDto toDto(ProjectEntity entity) {
        ProjectDto dto = new ProjectDto();

        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setMotivation(entity.getMotivation());
        dto.setStatus(entity.getStatus().getValue());
        dto.setMaxUsers(entity.getMaxUsers());
        dto.setOwner(entity.getOwner().getId());
        dto.setApproved(entity.getApproved());
        dto.setCreationDate(entity.getCreationDate());
        dto.setApprovedDate(entity.getApprovedDate());
        dto.setStartingDate(entity.getStartingDate());
        dto.setPlannedEndDate(entity.getPlannedEndDate());
        dto.setEndDate(entity.getEndDate());

        dto.setUserProjectDtos(entity.getUserProjects().stream().map(UserProjectMapper::toDto).collect(Collectors.toList()));
        dto.setComponents(entity.getComponents().stream().map(ComponentMapper::toDto).collect(Collectors.toList()));
        dto.setResources(entity.getResources().stream().map(ResourceMapper::toDto).collect(Collectors.toList()));
        dto.setTasks(entity.getTasks().stream().map(TaskMapper::toDto).collect(Collectors.toList()));
        dto.setInterests(entity.getInterests().stream().map(InterestMapper::toDto).collect(Collectors.toList()));
        dto.setSkills(entity.getSkills().stream().map(SkillMapper::toDto).collect(Collectors.toList()));
        dto.setHistoryrecords(entity.getHistoryRecords().stream().map(ProjectHistoryMapper::toDto).collect(Collectors.toList()));
        dto.setChatMessage(entity.getChatMessages().stream().map(ChatMessageMapper::toDto).collect(Collectors.toList()));
        dto.setWorkplace(WorkplaceMapper.toDto(entity.getWorkplace()));

        return dto;
    }

    public static ProjectEntity toEntity(ProjectDto dto) {
        ProjectEntity entity = new ProjectEntity();

        // Validate the dates
        if (dto.getStartingDate() != null && dto.getPlannedEndDate() != null) {
            if (!dto.getStartingDate().isBefore(dto.getPlannedEndDate())) {
                throw new IllegalArgumentException("Starting date must be before the planned end date");
            }
        }

        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setMotivation(dto.getMotivation());
        entity.setMaxUsers(dto.getMaxUsers());
        entity.setApproved(dto.isApproved());
        entity.setCreationDate(dto.getCreationDate());
        entity.setApprovedDate(dto.getApprovedDate());
        entity.setStartingDate(dto.getStartingDate());
        entity.setPlannedEndDate(dto.getPlannedEndDate());
        entity.setEndDate(dto.getEndDate());

        if (dto.getComponents() != null) {
            entity.setComponents(dto.getComponents().stream().map(ComponentMapper::toEntity).collect(Collectors.toSet()));
        }
        if (dto.getResources() != null) {
            entity.setResources(dto.getResources().stream().map(ResourceMapper::toEntity).collect(Collectors.toSet()));
        }
        if (dto.getInterests() != null) {
            entity.setInterests(dto.getInterests().stream().map(InterestMapper::toEntity).collect(Collectors.toSet()));
        }
        if (dto.getSkills() != null) {
            entity.setSkills(dto.getSkills().stream().map(SkillMapper::toEntity).collect(Collectors.toSet()));
        }
        if (dto.getTasks() != null) {
            entity.setTasks(dto.getTasks().stream().map(TaskMapper::toEntity).collect(Collectors.toList()));
        }
        if (dto.getHistoryrecords() != null) {
            entity.setHistoryRecords(dto.getHistoryrecords().stream().map(ProjectHistoryMapper::toEntity).collect(Collectors.toSet()));
        }
        if (dto.getChatMessage() != null) {
            entity.setChatMessages(dto.getChatMessage().stream().map(ChatMessageMapper::toEntity).collect(Collectors.toSet()));
        }
        if (dto.getWorkplace() != null) {
            entity.setWorkplace(WorkplaceMapper.toEntity(dto.getWorkplace()));
        }

        return entity;
    }
}
