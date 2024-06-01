package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.ProjectDto;
import aor.paj.projetofinalbackend.dto.SkillDto;
import aor.paj.projetofinalbackend.entity.InterestEntity;
import aor.paj.projetofinalbackend.entity.ProjectEntity;
import aor.paj.projetofinalbackend.entity.SkillEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.utils.ListConverter;
import aor.paj.projetofinalbackend.utils.ProjectStatus;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProjectMapper {

    public static ProjectEntity toEntity(ProjectDto dto, UserEntity userOwner) {
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
        project.setOwner(userOwner);
        project.setApproved(dto.isApproved());
        project.setCreationDate(dto.getCreationDate());
        project.setApprovedDate(dto.getApprovedDate());
        project.setStartingDate(dto.getStartingDate());
        project.setPlannedEndDate(dto.getPlannedEndDate());

        Set<InterestEntity> listInterestSet = ListConverter.convertListToSet(dto.getInterests(),InterestMapper::toEntity);
        project.setInterests(listInterestSet);
        Set<SkillEntity> listSkillsSet = ListConverter.convertListToSet(dto.getSkills(), SkillMapper::toEntity);
        project.setSkills(listSkillsSet);





    }
}
