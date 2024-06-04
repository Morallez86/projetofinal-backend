package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.ProjectDto;
import aor.paj.projetofinalbackend.dto.SkillDto;
import aor.paj.projetofinalbackend.entity.*;
import aor.paj.projetofinalbackend.utils.ListConverter;
import aor.paj.projetofinalbackend.utils.ProjectStatus;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProjectMapper {
    public static ProjectDto toDto(ProjectEntity entity) {
        ProjectDto dto = new ProjectDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setMotivation(entity.getMotivation());
        dto.setStatus(entity.getStatus().ordinal());
        dto.setMaxUsers(entity.getMaxUsers());
        dto.setOwner(entity.getOwner().getId());
        dto.setApproved(entity.getApproved());
        dto.setCreationDate(entity.getCreationDate());
        dto.setApprovedDate(entity.getApprovedDate());
        dto.setStartingDate(entity.getStartingDate());
        dto.setPlannedEndDate(entity.getPlannedEndDate());
        dto.setEndDate(entity.getEndDate());
       /* dto.setUserProjectDtos(entity.getUserProjects().stream().map(UserProjectMapper::toDto).collect(Collectors.toList()));
        dto.setComponents(entity.getComponents().stream().map(ComponentMapper::toDto).collect(Collectors.toList()));
        dto.setResources(entity.getResources().stream().map(ResourceMapper::toDto).collect(Collectors.toList()));
        dto.setTasks(entity.getTasks().stream().map(TaskMapper::toDto).collect(Collectors.toList()));
        dto.setInterests(entity.getInterests().stream().map(InterestMapper::toDto).collect(Collectors.toList()));
        dto.setSkills(entity.getSkills().stream().map(SkillMapper::toDto).collect(Collectors.toList()));
        dto.setHistoryrecords(entity.getHistoryRecords().stream().map(ProjectHistoryMapper::toDto).collect(Collectors.toList()));
        dto.setChatMessage(entity.getChatMessages().stream().map(ChatMessageMapper::toDto).collect(Collectors.toList()));*/
        return dto;
    }

    public static ProjectEntity toEntity(ProjectDto dto) {
        ProjectEntity entity = new ProjectEntity();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setMotivation(dto.getMotivation());
        entity.setStatus(ProjectStatus.fromValue(dto.getStatus()));
        entity.setMaxUsers(dto.getMaxUsers());
        entity.setApproved(dto.isApproved());
        entity.setCreationDate(dto.getCreationDate());
        entity.setApprovedDate(dto.getApprovedDate());
        entity.setStartingDate(dto.getStartingDate());
        entity.setPlannedEndDate(dto.getPlannedEndDate());
        entity.setEndDate(dto.getEndDate());
        entity.setComponents(dto.getComponents().stream().map(ComponentMapper::toEntity).collect(Collectors.toSet()));
        entity.setResources(dto.getResources().stream().map(ResourceMapper::toEntity).collect(Collectors.toSet()));
        entity.setInterests(dto.getInterests().stream().map(InterestMapper::toEntity).collect(Collectors.toSet()));
        entity.setSkills(dto.getSkills().stream().map(SkillMapper::toEntity).collect(Collectors.toSet()));
        entity.setUserProjects(dto.getUserProjectDtos().stream().map(UserProjectMapper::toEntity).collect(Collectors.toSet()));
        if (dto.getTasks()!=null) {
        entity.setTasks(dto.getTasks().stream().map(TaskMapper::toEntity).collect(Collectors.toSet())); }
        if (dto.getHistoryrecords()!=null) {
            entity.setHistoryRecords(dto.getHistoryrecords().stream().map(ProjectHistoryMapper::toEntity).collect(Collectors.toSet()));
        }
        if (dto.getChatMessage()!=null) {
            entity.setChatMessages(dto.getChatMessage().stream().map(ChatMessageMapper::toEntity).collect(Collectors.toSet()));

        }
        return entity;
    }
}

