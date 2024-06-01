package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.ProjectDto;
import aor.paj.projetofinalbackend.dto.SkillDto;
import aor.paj.projetofinalbackend.entity.*;
import aor.paj.projetofinalbackend.utils.ListConverter;
import aor.paj.projetofinalbackend.utils.ProjectStatus;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProjectMapper {

    public static ProjectEntity toEntity(ProjectDto dto) {
        ProjectEntity project = new ProjectEntity();
        project.setId(dto.getId());
        project.setTitle(dto.getTitle());
        project.setDescription(dto.getDescription());
        project.setMotivation(dto.getMotivation());
        for (ProjectStatus status : ProjectStatus.values()) {
            if (status.getValue() == dto.getStatus()) {
                project.setStatus(status);
                break;
            }
        }
        project.setMaxUsers(dto.getMaxUsers());
        project.setOwner(UserMapper.toEntity(dto.getOwner()));
        project.setApproved(dto.isApproved());
        project.setCreationDate(dto.getCreationDate());
        project.setApprovedDate(dto.getApprovedDate());
        project.setStartingDate(dto.getStartingDate());
        project.setPlannedEndDate(dto.getPlannedEndDate());
        project.setEndDate(dto.getEndDate());

        Set < UserProjectEntity> listUserProjectSet = ListConverter.convertListToSet(dto.getUserProjectDtos(),UserProjectMapper::toEntity);
        project.setUserProjects(listUserProjectSet);

        Set<ComponentEntity> listComponentSet = ListConverter.convertListToSet(dto.getComponents(), ComponentMapper::toEntity);
        project.setComponents(listComponentSet);

        Set<ResourceEntity> listResourceSet =  ListConverter.convertListToSet(dto.getResources(),ResourceMapper::toEntity);
        project.setResources(listResourceSet);

        Set<TaskEntity> listTaskSet = ListConverter.convertListToSet(dto.getTasks(),TaskMapper::toEntity);
        project.setTasks(listTaskSet);

        Set<ProjectHistoryEntity> listProjectHistorySet = ListConverter.convertListToSet(dto.getHistoryrecords(),ProjectHistoryMapper::toEntity);
        project.setHistoryRecords(listProjectHistorySet);

        Set<ChatMessageEntity> listChatMessageSet = ListConverter.convertListToSet(dto.getChatMessage(),ChatMessageMapper::toEntity);
        project.setChatMessages(listChatMessageSet);

        Set<InterestEntity> listInterestSet = ListConverter.convertListToSet(dto.getInterests(),InterestMapper::toEntity);
        project.setInterests(listInterestSet);
        Set<SkillEntity> listSkillsSet = ListConverter.convertListToSet(dto.getSkills(), SkillMapper::toEntity);
        project.setSkills(listSkillsSet);
return project;
    }
}
