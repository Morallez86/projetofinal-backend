package aor.paj.projetofinalbackend.dto;

import aor.paj.projetofinalbackend.entity.ChatMessageEntity;
import aor.paj.projetofinalbackend.entity.ComponentEntity;
import aor.paj.projetofinalbackend.entity.ProjectHistoryEntity;
import aor.paj.projetofinalbackend.entity.ResourceEntity;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.time.LocalDateTime;
import java.util.List;

@XmlRootElement
public class ProjectDto {

    private Long id;

    private String title;
    private String description;
    private String motivation;

    private int status;

    private int maxUsers;

    private Long ownerId;

    private boolean approved;

    private LocalDateTime creationDate;

    private LocalDateTime approvedDate;

    private LocalDateTime startingDate;

    private LocalDateTime plannedEndDate;

    private LocalDateTime endDate;

    private List<UserProjectDto> userProjectDtos;

    private List<ComponentDto> components;

    private List<ResourceDto> resources;

    private List<TaskDto> tasks;

    private List<InterestDto> interests;

    private List<ProjectHistoryDto> historyrecords;

    private List<ChatMessageDto> chatMessage;

}
