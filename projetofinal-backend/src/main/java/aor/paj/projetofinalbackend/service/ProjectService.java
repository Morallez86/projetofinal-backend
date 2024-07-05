package aor.paj.projetofinalbackend.service;

import aor.paj.projetofinalbackend.bean.AuthBean;
import aor.paj.projetofinalbackend.bean.ChatMesssageBean;
import aor.paj.projetofinalbackend.bean.ProjectBean;
import aor.paj.projetofinalbackend.bean.ProjectHistoryBean;
import aor.paj.projetofinalbackend.dao.TaskDao;
import aor.paj.projetofinalbackend.dto.*;
import aor.paj.projetofinalbackend.entity.ProjectEntity;
import aor.paj.projetofinalbackend.utils.ProjectStatus;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Path("/projects")
public class ProjectService {

    @Inject
    ProjectBean projectBean;

    @Inject
    AuthBean authBean;

    @Inject
    ProjectHistoryBean projectHistoryBean;

    @Inject
    ChatMesssageBean chatMessageBean;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProject(@HeaderParam("Authorization") String authorizationHeader, ProjectDto projectDto) {
        try {
            System.out.println(projectDto.getUserProjectDtos().get(0).getId());
            String token = authorizationHeader.substring("Bearer".length()).trim();
            projectBean.addProject(projectDto, token);
            return Response.status(Response.Status.CREATED).entity("project created").build();
        } catch (ExceptionInInitializerError e) {
            Throwable cause = e.getCause();
            cause.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(cause.getMessage()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProjects(@HeaderParam("Authorization") String authorizationHeader,
                                   @QueryParam("page") Integer page,
                                   @QueryParam("limit") Integer limit,
                                   @QueryParam("searchTerm") String searchTerm,
                                   @QueryParam("skills") String skills,
                                   @QueryParam("interests") String interests,
                                   @QueryParam("status") String status
    ) {
        try {
            Set<ProjectDto> projectDtos;
            long totalProjects;
            int totalPages;
            System.out.println(status);

            ProjectStatus projectStatus = null;
            if (status != null) {
                try {
                    projectStatus = ProjectStatus.fromValue(Integer.parseInt(status));
                } catch (IllegalArgumentException e) {
                    return Response.status(Response.Status.BAD_REQUEST).entity("Invalid status value").build();
                }
            }

            if ((page == null || limit == null) && (searchTerm == null && skills == null && interests == null)) {
                // No pagination or search parameters provided, return all projects
                projectDtos = projectBean.getAllProjectsNoQueries();
                totalPages = 1; // Since we're returning all projects in a single response
            } else if (searchTerm != null || skills != null || interests != null || status != null) {
                // Search parameters provided, return filtered projects
                projectDtos = projectBean.searchProjects(searchTerm, skills, interests, projectStatus);
                System.out.println(searchTerm);
                System.out.println(skills);
                System.out.println(interests);
                System.out.println(status);
                for (ProjectDto project : projectDtos) {
                    System.out.println(project.getTitle());
                }

                totalProjects = projectDtos.size(); // Total projects based on search criteria
                totalPages = (int) Math.ceil((double) totalProjects / (limit != null ? limit : totalProjects));
            } else {
                // Pagination parameters provided, return paginated projects
                projectDtos = projectBean.getAllProjects(page, limit);
                totalProjects = projectBean.getTotalProjectCount();
                totalPages = (int) Math.ceil((double) totalProjects / limit);
            }

            // Include total number of pages in the response
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("projects", projectDtos);
            responseMap.put("totalPages", totalPages);

            return Response.ok(responseMap).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{projectId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProjectDetails(@HeaderParam("Authorization") String authorizationHeader,
                                      @PathParam("projectId") Long projectId) {
        try {
            String token = authorizationHeader.substring("Bearer".length()).trim();
            Response validationResponse = authBean.validateUserToken(token);
            if (validationResponse.getStatus() != Response.Status.OK.getStatusCode()) {
                return validationResponse;
            }

            ProjectDto projectDto = projectBean.getProjectById(projectId);
            if (projectDto == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Project not found").build();
            }

            return Response.ok(projectDto).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}/tasks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksByProjectId(@HeaderParam("Authorization") String authorizationHeader, @PathParam("id") Long projectId) {
        try {
            List<TaskDto> taskDtos = projectBean.getTasksByProjectId(projectId);
            return Response.status(Response.Status.OK).entity(taskDtos).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{projectId}/users")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersByProjectId(@HeaderParam("Authorization") String authorizationHeader, @PathParam("projectId") Long projectId) {
        try {
            List<UserProjectDto> userProjectDtos = projectBean.getUsersByProject(projectId);
            return Response.status(Response.Status.OK).entity(userProjectDtos).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();

        }
    }

    @GET
    @Path("/{projectId}/possibleDependentTasks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksDependentByProjectId(@HeaderParam("Authorization") String authorizationHeader, @PathParam("projectId") Long projectId, @QueryParam("plannedStartingDate") String plannedStartingDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            LocalDateTime plannedStartingDateLocal = LocalDateTime.parse(plannedStartingDate, formatter);
            TaskEndDateDto plannedStartingDateDto = new TaskEndDateDto(plannedStartingDateLocal);
            List<TaskDto> taskDtos = projectBean.getPossibleDependentTasks(projectId, plannedStartingDateDto);
            return Response.status(Response.Status.OK).entity(taskDtos).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();

        }
    }


    @PUT
    @Path("/{projectId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProject(@HeaderParam("Authorization") String authorizationHeader,
                                  @PathParam("projectId") Long projectId,
                                  ProjectDto projectDto) {
        try {
            String token = authorizationHeader.substring("Bearer".length()).trim();
            Response validationResponse = authBean.validateUserToken(token);
            if (validationResponse.getStatus() != Response.Status.OK.getStatusCode()) {
                return validationResponse;
            }

            projectBean.updateProject(projectId, projectDto, token);
            return Response.status(Response.Status.OK).entity("Project updated successfully").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/createChatMsg")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createChatMsg(@HeaderParam("Authorization") String authorizationHeader, ChatMessageDto chatMessageDto) {
        try {
            ChatMessageDto chatMessageDtoNew = chatMessageBean.createChatMsg(chatMessageDto);
            return Response.status(Response.Status.CREATED).entity(chatMessageDtoNew).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{projectId}/users/{userId}/status")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeUserStatus(
            @HeaderParam("Authorization") String authorizationHeader,
            @PathParam("projectId") Long projectId,
            @PathParam("userId") Long userId,
            UserProjectDto userProjectDto) {

        try {
            // Validate token
            String token = authorizationHeader.substring("Bearer".length()).trim();
            Response validationResponse = authBean.validateUserToken(token);
            if (validationResponse.getStatus() != Response.Status.OK.getStatusCode()) {
                return validationResponse;
            }

            Boolean newStatus = userProjectDto.isAdmin();

            // Change user status in the project
            projectBean.changeUserStatus(projectId, userId, newStatus, token);

            return Response.status(Response.Status.OK).entity("User status updated successfully").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{projectId}/users/{userId}/inactive")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeUserInactive(
            @HeaderParam("Authorization") String authorizationHeader,
            @PathParam("projectId") Long projectId,
            @PathParam("userId") Long userId,
            UserProjectDto userProjectDto) {

        try {
            // Validate token
            String token = authorizationHeader.substring("Bearer".length()).trim();
            Response validationResponse = authBean.validateUserToken(token);
            if (validationResponse.getStatus() != Response.Status.OK.getStatusCode()) {
                return validationResponse;
            }

            Boolean newStatus = userProjectDto.isAdmin();

            // Change user status in the project
            projectBean.changeUserToInactive(projectId, userId, token);

            return Response.status(Response.Status.OK).entity("User status updated successfully").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/{projectId}/addSkill")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSkillToProject(@HeaderParam("Authorization") String authorizationHeader, @PathParam("projectId") Long projectId, AddSkillToProjectDto addSkillToProjectDto) {
        try {
            // Validate token
            String token = authorizationHeader.substring("Bearer".length()).trim();
            Response validationResponse = authBean.validateUserToken(token);
            if (validationResponse.getStatus() != Response.Status.OK.getStatusCode()) {
                return validationResponse;
            }

            // Get project by ID
            ProjectDto projectDto = projectBean.getProjectById(projectId);
            if (projectDto == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Project not found").build();
            }

            // Check if the skill already exists in the project
            List<SkillDto> existingSkills = projectDto.getSkills();
            for (SkillDto skill : existingSkills) {
                if (skill.getName().equals(addSkillToProjectDto.getSkill().getName())) {
                    return Response.status(Response.Status.CONFLICT).entity("Skill already exists in the project").build();
                }
            }

            // Add skill to the project
            projectBean.addSkillToProject(projectId, addSkillToProjectDto.getSkill(), token);

            return Response.status(Response.Status.OK).entity("Skill added to project successfully").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/{projectId}/addInterest")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addInterestToProject(@HeaderParam("Authorization") String authorizationHeader, @PathParam("projectId") Long projectId, AddInterestToProjectDto addInterestToProjectDto) {
        System.out.println("ocdnsvvnsdo");
        try {
            // Validate token
            String token = authorizationHeader.substring("Bearer".length()).trim();
            Response validationResponse = authBean.validateUserToken(token);
            if (validationResponse.getStatus() != Response.Status.OK.getStatusCode()) {
                return validationResponse;
            }

            // Get project by ID
            ProjectDto projectDto = projectBean.getProjectById(projectId);
            if (projectDto == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Project not found").build();
            }

            // Check if the skill already exists in the project
            List<InterestDto> existingInterests = projectDto.getInterests();
            for (InterestDto interest : existingInterests) {
                if (interest.getName().equals(addInterestToProjectDto.getInterest().getName())) {
                    return Response.status(Response.Status.CONFLICT).entity("Interest already exists in the project").build();
                }
            }

            // Add skill to the project
            projectBean.addInterestToProject(projectId, addInterestToProjectDto.getInterest(), token);

            return Response.status(Response.Status.OK).entity("Skill added to project successfully").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/{projectId}/addComponent")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addComponentToProject(@HeaderParam("Authorization") String authorizationHeader, @PathParam("projectId") Long projectId, AddComponentToProjectDto addComponentToProjectDto) {
        try {
            // Validate token
            String token = authorizationHeader.substring("Bearer".length()).trim();
            Response validationResponse = authBean.validateUserToken(token);
            if (validationResponse.getStatus() != Response.Status.OK.getStatusCode()) {
                return validationResponse;
            }

            // Get project by ID
            ProjectDto projectDto = projectBean.getProjectById(projectId);
            if (projectDto == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Project not found").build();
            }

            // Add component to the project
            projectBean.addComponentToProject(projectId, addComponentToProjectDto.getComponent(), token);

            return Response.status(Response.Status.OK).entity("Component added to project successfully").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/{projectId}/addResource")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addResourceToProject(@HeaderParam("Authorization") String authorizationHeader, @PathParam("projectId") Long projectId, AddResourceToProjectDto addResourceToProjectDto) {
        try {
            // Validate token
            String token = authorizationHeader.substring("Bearer".length()).trim();
            Response validationResponse = authBean.validateUserToken(token);
            if (validationResponse.getStatus() != Response.Status.OK.getStatusCode()) {
                return validationResponse;
            }

            // Get project by ID
            ProjectDto projectDto = projectBean.getProjectById(projectId);
            if (projectDto == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Project not found").build();
            }

            // Check if the resource already exists in the project
            List<ResourceDto> existingResources = projectDto.getResources();
            for (ResourceDto resource : existingResources) {
                if (resource.getName().equals(addResourceToProjectDto.getResource().getName())) {
                    return Response.status(Response.Status.CONFLICT).entity("Resource already exists in the project").build();
                }
            }

            // Add resource to the project
            projectBean.addResourceToProject(projectId, addResourceToProjectDto.getResource(), token);

            return Response.status(Response.Status.OK).entity("Resource added to project successfully").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }



    @DELETE
    @Path("/{projectId}/removeSkills")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeSkillsFromProject(
            @HeaderParam("Authorization") String authorizationHeader,
            @PathParam("projectId") Long projectId,
            List<Long> skillsToRemove) {
        try {
            // Validate token
            String token = authorizationHeader.substring("Bearer".length()).trim();
            Response validationResponse = authBean.validateUserToken(token);
            if (validationResponse.getStatus() != Response.Status.OK.getStatusCode()) {
                return validationResponse;
            }

            projectBean.removeSKillsProject( skillsToRemove, projectId);

            return Response.status(Response.Status.OK).entity("Skills removed from project successfully").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{projectId}/removeInterests")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeInterestsFromProject(
            @HeaderParam("Authorization") String authorizationHeader,
            @PathParam("projectId") Long projectId,
            List<Long> interestsToRemove) {
        try {
            // Validate token
            String token = authorizationHeader.substring("Bearer".length()).trim();
            Response validationResponse = authBean.validateUserToken(token);
            if (validationResponse.getStatus() != Response.Status.OK.getStatusCode()) {
                return validationResponse;
            }

            projectBean.removeInterestsProject(interestsToRemove, projectId);

            return Response.status(Response.Status.OK).entity("Interests removed from project successfully").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{projectId}/removeComponents")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeComponentsFromProject(
            @HeaderParam("Authorization") String authorizationHeader,
            @PathParam("projectId") Long projectId,
            List<Long> componentsToRemove) {
        try {
            // Validate token
            String token = authorizationHeader.substring("Bearer".length()).trim();
            Response validationResponse = authBean.validateUserToken(token);
            if (validationResponse.getStatus() != Response.Status.OK.getStatusCode()) {
                return validationResponse;
            }

            projectBean.removeComponentsFromProject(componentsToRemove, projectId);

            return Response.status(Response.Status.OK).entity("Components removed from project successfully").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }


}

