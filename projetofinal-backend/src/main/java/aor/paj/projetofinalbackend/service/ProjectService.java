package aor.paj.projetofinalbackend.service;

import aor.paj.projetofinalbackend.bean.AuthBean;
import aor.paj.projetofinalbackend.bean.ChatMesssageBean;
import aor.paj.projetofinalbackend.bean.ProjectBean;
import aor.paj.projetofinalbackend.bean.ProjectHistoryBean;
import aor.paj.projetofinalbackend.dao.TokenDao;
import aor.paj.projetofinalbackend.dto.*;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.utils.LoggerUtil;
import aor.paj.projetofinalbackend.utils.ProjectStatus;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Service endpoints for managing projects.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Path("/projects")
public class ProjectService {

    @Inject
    ProjectBean projectBean;

    @Inject
    AuthBean authBean;

    @Inject
    ChatMesssageBean chatMessageBean;

    @Inject
    TokenDao tokenDao;

    /**
     * Endpoint to create a new project.
     *
     * @param authorizationHeader Authorization header containing bearer token.
     * @param projectDto ProjectDto object containing project details.
     * @return Response indicating success or failure of project creation.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProject(@HeaderParam("Authorization") String authorizationHeader, ProjectDto projectDto) {
        try {
            String token = authorizationHeader.substring("Bearer".length()).trim();
            UserEntity user = tokenDao.findUserByTokenValue(token);
            projectBean.addProject(projectDto, token);
            LoggerUtil.logInfo("PROJECT CREATED WITH THIS NAME" + projectDto.getTitle(), "at " + LocalDateTime.now(), user.getEmail(),token);
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

    /**
     * Endpoint for retrieving all projects, optionally filtered by various parameters.
     *
     * @param authorizationHeader The authorization header containing the bearer token.
     * @param page The page number for pagination (optional).
     * @param limit The limit of projects per page (optional).
     * @param searchTerm The search term to filter projects by (optional).
     * @param skills The skills to filter projects by (optional).
     * @param interests The interests to filter projects by (optional).
     * @param status The status to filter projects by (optional).
     * @return Response containing a list of projects and total number of pages.
     */
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
        String token = authorizationHeader.substring("Bearer".length()).trim();

        try {
            Set<ProjectDto> projectDtos;
            long totalProjects;
            int totalPages;

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

            LoggerUtil.logInfo("SEE ALL PROJECTS", "at " + LocalDateTime.now(), "not user",token);

            return Response.ok(responseMap).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Endpoint for retrieving details of a specific project.
     *
     * @param authorizationHeader The authorization header containing the bearer token.
     * @param projectId The ID of the project to retrieve.
     * @return Response containing the project details.
     */
    @GET
    @Path("/{projectId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProjectDetails(@HeaderParam("Authorization") String authorizationHeader,
                                      @PathParam("projectId") Long projectId) {
        try {
            String token = authorizationHeader.substring("Bearer".length()).trim();
            UserEntity user = tokenDao.findUserByTokenValue(token);
            Response validationResponse = authBean.validateUserToken(token);
            if (validationResponse.getStatus() != Response.Status.OK.getStatusCode()) {
                return validationResponse;
            }

            ProjectDto projectDto = projectBean.getProjectById(projectId);
            if (projectDto == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Project not found").build();
            }
            LoggerUtil.logInfo("CHECK PROJECT WITH THIS ID: " + projectId, "at " + LocalDateTime.now(), user.getEmail(),token);

            return Response.ok(projectDto).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Retrieves tasks associated with a project identified by projectId.
     *
     * @param authorizationHeader The Authorization header containing the bearer token.
     * @param projectId The ID of the project to fetch tasks for.
     * @return Response with status OK and a list of TaskDto objects if successful, otherwise INTERNAL_SERVER_ERROR.
     */
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

    /**
     * Retrieves users associated with a project identified by projectId.
     *
     * @param authorizationHeader The Authorization header containing the bearer token.
     * @param projectId The ID of the project to fetch users for.
     * @return Response with status OK and a list of UserProjectDto objects if successful, otherwise INTERNAL_SERVER_ERROR.
     */
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

    /**
     * Retrieves tasks dependent on a project identified by projectId and planned starting date.
     *
     * @param authorizationHeader The Authorization header containing the bearer token.
     * @param projectId The ID of the project to fetch dependent tasks for.
     * @param plannedStartingDate The planned starting date in format "yyyy-MM-dd HH:mm:ss".
     * @return Response with status OK and a list of TaskDto objects if successful, otherwise INTERNAL_SERVER_ERROR.
     */
    @GET
    @Path("/{projectId}/possibleDependentTasks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksDependentByProjectId(
            @HeaderParam("Authorization") String authorizationHeader,
            @PathParam("projectId") Long projectId,
            @QueryParam("plannedStartingDate") String plannedStartingDate) {
        try {
            // Verificação se plannedStartingDate é nulo ou vazio
            if (plannedStartingDate == null || plannedStartingDate.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("plannedStartingDate query parameter is required")
                        .build();
            }

            // Formatação da data
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime plannedStartingDateLocal;

            try {
                plannedStartingDateLocal = LocalDateTime.parse(plannedStartingDate, formatter);
            } catch (DateTimeParseException e) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Invalid date format for plannedStartingDate. Expected format: yyyy-MM-dd HH:mm:ss")
                        .build();
            }

            // Criação do DTO e obtenção das tarefas
            TaskEndDateDto plannedStartingDateDto = new TaskEndDateDto(plannedStartingDateLocal);
            List<TaskDto> taskDtos = projectBean.getPossibleDependentTasks(projectId, plannedStartingDateDto);

            return Response.status(Response.Status.OK).entity(taskDtos).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Updates details of a project identified by projectId.
     *
     * @param authorizationHeader The Authorization header containing the bearer token.
     * @param projectId The ID of the project to update.
     * @param projectDto The ProjectDto object containing updated project details.
     * @return Response with status OK if successful, otherwise BAD_REQUEST or INTERNAL_SERVER_ERROR.
     */
    @PUT
    @Path("/{projectId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProject(@HeaderParam("Authorization") String authorizationHeader,
                                  @PathParam("projectId") Long projectId,
                                  ProjectDto projectDto) {
        try {
            String token = authorizationHeader.substring("Bearer".length()).trim();
            UserEntity user = tokenDao.findUserByTokenValue(token);
            Response validationResponse = authBean.validateUserToken(token);
            if (validationResponse.getStatus() != Response.Status.OK.getStatusCode()) {
                return validationResponse;
            }
            projectBean.updateProject(projectId, projectDto, token);
            LoggerUtil.logInfo("UPDATE PROJECT WITH THIS ID: " + projectDto, "at " + LocalDateTime.now(), user.getEmail(), token);
            return Response.status(Response.Status.OK).entity("Project updated successfully").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Creates a chat message associated with a project identified by projectId.
     *
     * @param authorizationHeader The Authorization header containing the bearer token.
     * @param chatMessageDto The ChatMessageDto object containing the chat message details.
     * @return Response with status CREATED and the created ChatMessageDto if successful, otherwise INTERNAL_SERVER_ERROR.
     */
    @POST
    @Path("/createChatMsg")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createChatMsg(@HeaderParam("Authorization") String authorizationHeader, ChatMessageDto chatMessageDto) {
        String token = authorizationHeader.substring("Bearer".length()).trim();
        UserEntity user = tokenDao.findUserByTokenValue(token);
        try {
            ChatMessageDto chatMessageDtoNew = chatMessageBean.createChatMsg(chatMessageDto);
            LoggerUtil.logInfo("MSG SENT TO PROJECT CHAT: " + chatMessageDto.getContent(), "at " + LocalDateTime.now(), user.getEmail(),token);

            return Response.status(Response.Status.CREATED).entity(chatMessageDtoNew).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Changes the status of a user in a project identified by projectId and userId.
     *
     * @param authorizationHeader The Authorization header containing the bearer token.
     * @param projectId The ID of the project.
     * @param userId The ID of the user to update status.
     * @param userProjectDto The UserProjectDto object containing updated user status.
     * @return Response with status OK if successful, otherwise INTERNAL_SERVER_ERROR.
     */
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
            UserEntity user = tokenDao.findUserByTokenValue(token);
            Response validationResponse = authBean.validateUserToken(token);
            if (validationResponse.getStatus() != Response.Status.OK.getStatusCode()) {
                return validationResponse;
            }

            Boolean newStatus = userProjectDto.isAdmin();

            // Change user status in the project
            projectBean.changeUserStatus(projectId, userId, newStatus, token);
            LoggerUtil.logInfo("USER STATUS CHANGED TO ADMIN? " + newStatus , "at " + LocalDateTime.now(), user.getEmail(),token);

            return Response.status(Response.Status.OK).entity("User status updated successfully").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Changes the status of a user to inactive in a project identified by projectId and userId.
     *
     * @param authorizationHeader The Authorization header containing the bearer token.
     * @param projectId The ID of the project.
     * @param userId The ID of the user to set inactive.
     * @param userProjectDto The UserProjectDto object containing updated user status.
     * @return Response with status OK if successful, otherwise INTERNAL_SERVER_ERROR.
     */
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
            UserEntity user = tokenDao.findUserByTokenValue(token);
            Response validationResponse = authBean.validateUserToken(token);
            if (validationResponse.getStatus() != Response.Status.OK.getStatusCode()) {
                return validationResponse;
            }

            Boolean newStatus = userProjectDto.isAdmin();

            // Change user status in the project
            projectBean.changeUserToInactive(projectId, userId, token);

            LoggerUtil.logInfo("USER STATUS CHANGED TO INATIVE" , "at " + LocalDateTime.now(), user.getEmail(),token);


            return Response.status(Response.Status.OK).entity("User status updated successfully").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Adds a skill to a project identified by projectId.
     *
     * @param authorizationHeader The Authorization header containing the bearer token.
     * @param projectId The ID of the project.
     * @param addSkillToProjectDto  The AddSkillToProjectDto object containing the skill to add.
     * @return Response with status OK if successful, otherwise INTERNAL_SERVER_ERROR.
     */
    @POST
    @Path("/{projectId}/addSkill")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSkillToProject(@HeaderParam("Authorization") String authorizationHeader, @PathParam("projectId") Long projectId, AddSkillToProjectDto addSkillToProjectDto) {
        try {
            // Validate token
            String token = authorizationHeader.substring("Bearer".length()).trim();
            UserEntity user = tokenDao.findUserByTokenValue(token);
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
            LoggerUtil.logInfo("SKILLS ADDED TO PROJECT. PROJECT ID: " + projectId + " Skills Id's: " + addSkillToProjectDto.getSkill().getId() , "at " + LocalDateTime.now(), user.getEmail(),token);

            return Response.status(Response.Status.OK).entity("Skill added to project successfully").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Adds an interest to a project identified by projectId.
     *
     * @param authorizationHeader The Authorization header containing the bearer token.
     * @param projectId The ID of the project.
     * @param addInterestToProjectDto  The AddInterestToProjectDto object containing the interest to add.
     * @return Response with status OK if successful, otherwise INTERNAL_SERVER_ERROR.
     */
    @POST
    @Path("/{projectId}/addInterest")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addInterestToProject(@HeaderParam("Authorization") String authorizationHeader, @PathParam("projectId") Long projectId, AddInterestToProjectDto addInterestToProjectDto) {
        try {
            // Validate token
            String token = authorizationHeader.substring("Bearer".length()).trim();
            UserEntity user = tokenDao.findUserByTokenValue(token);
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
            LoggerUtil.logInfo("INTEREST ADDED TO PROJECT. PROJECT ID: " + projectId + " Interests Id's: " + addInterestToProjectDto.getInterest().getId() , "at " + LocalDateTime.now(), user.getEmail(),token);

            return Response.status(Response.Status.OK).entity("Interest added to project successfully").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Adds a component to a project identified by projectId.
     *
     * @param authorizationHeader The Authorization header containing the bearer token.
     * @param projectId The ID of the project.
     * @param addComponentToProjectDto  The AddComponentToProjectDto object containing the component to add.
     * @return Response with status OK if successful, otherwise INTERNAL_SERVER_ERROR.
     */
    @POST
    @Path("/{projectId}/addComponent")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addComponentToProject(@HeaderParam("Authorization") String authorizationHeader, @PathParam("projectId") Long projectId, AddComponentToProjectDto addComponentToProjectDto) {
        try {
            // Validate token
            String token = authorizationHeader.substring("Bearer".length()).trim();
            UserEntity user = tokenDao.findUserByTokenValue(token);

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
            LoggerUtil.logInfo("COMPONENT ADDED TO PROJECT. PROJECT ID: " + projectId + " Components Id's: " + addComponentToProjectDto.getComponent().getId() , "at " + LocalDateTime.now(), user.getEmail(),token);

            return Response.status(Response.Status.OK).entity("Component added to project successfully").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Adds a resource to a project identified by projectId.
     *
     * @param authorizationHeader The Authorization header containing the bearer token.
     * @param projectId The ID of the project.
     * @param addResourceToProjectDto The AddResourceToProjectDto object containing the resource to add.
     * @return Response with status OK if successful, otherwise INTERNAL_SERVER_ERROR.
     */
    @POST
    @Path("/{projectId}/addResource")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addResourceToProject(@HeaderParam("Authorization") String authorizationHeader, @PathParam("projectId") Long projectId, AddResourceToProjectDto addResourceToProjectDto) {
        try {
            // Validate token
            String token = authorizationHeader.substring("Bearer".length()).trim();
            UserEntity user = tokenDao.findUserByTokenValue(token);

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
            LoggerUtil.logInfo("RESOURCE ADDED TO PROJECT. PROJECT ID: " + projectId + " Components Id's: " + addResourceToProjectDto.getResource().getId() , "at " + LocalDateTime.now(), user.getEmail(),token);

            return Response.status(Response.Status.OK).entity("Resource added to project successfully").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Removes skills from a project identified by projectId.
     *
     * @param authorizationHeader The Authorization header containing the bearer token.
     * @param projectId The ID of the project.
     * @param skillsToRemove List of IDs of skills to remove from the project.
     * @return Response with status OK if successful, otherwise INTERNAL_SERVER_ERROR.
     */
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
            UserEntity user = tokenDao.findUserByTokenValue(token);
            Response validationResponse = authBean.validateUserToken(token);
            if (validationResponse.getStatus() != Response.Status.OK.getStatusCode()) {
                return validationResponse;
            }

            projectBean.removeSKillsProject( skillsToRemove, projectId);
            LoggerUtil.logInfo("SKILLS REMOVED FROM PROJECT. PROJECT ID: " + projectId + " Skills Id's: " + skillsToRemove , "at " + LocalDateTime.now(), user.getEmail(),token);

            return Response.status(Response.Status.OK).entity("Skills removed from project successfully").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Removes interests from a project identified by projectId.
     *
     * @param authorizationHeader  The Authorization header containing the bearer token.
     * @param projectId The ID of the project.
     * @param interestsToRemove List of IDs of interests to remove from the project.
     * @return Response with status OK if successful, otherwise INTERNAL_SERVER_ERROR.
     */
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
            UserEntity user = tokenDao.findUserByTokenValue(token);

            Response validationResponse = authBean.validateUserToken(token);
            if (validationResponse.getStatus() != Response.Status.OK.getStatusCode()) {
                return validationResponse;
            }

            projectBean.removeInterestsProject(interestsToRemove, projectId);
            LoggerUtil.logInfo("INTERESTS REMOVED FROM PROJECT. PROJECT ID: " + projectId + " Interests Id's: " + interestsToRemove , "at " + LocalDateTime.now(), user.getEmail(),token);

            return Response.status(Response.Status.OK).entity("Interests removed from project successfully").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Removes components from a project identified by projectId.
     *
     * @param authorizationHeader The Authorization header containing the bearer token.
     * @param projectId The ID of the project.
     * @param componentsToRemove List of IDs of components to remove from the project.
     * @return Response with status OK if successful, otherwise INTERNAL_SERVER_ERROR.
     */
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
            UserEntity user = tokenDao.findUserByTokenValue(token);

            Response validationResponse = authBean.validateUserToken(token);
            if (validationResponse.getStatus() != Response.Status.OK.getStatusCode()) {
                return validationResponse;
            }

            projectBean.removeComponentsFromProject(componentsToRemove, projectId);
            LoggerUtil.logInfo("COMPONENTS REMOVED FROM PROJECT. PROJECT ID: " + projectId + " Componentes Id's: " + componentsToRemove , "at " + LocalDateTime.now(), user.getEmail(),token);

            return Response.status(Response.Status.OK).entity("Components removed from project successfully").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Removes resources from a project identified by projectId.
     *
     * @param authorizationHeader  The Authorization header containing the bearer token.
     * @param projectId The ID of the project.
     * @param resourcesToRemove List of IDs of resources to remove from the project.
     * @return Response with status OK if successful, otherwise INTERNAL_SERVER_ERROR.
     */
    @DELETE
    @Path("/{projectId}/removeResources")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeResourcesFromProject(
            @HeaderParam("Authorization") String authorizationHeader,
            @PathParam("projectId") Long projectId,
            List<Long> resourcesToRemove) {
        try {
            // Validate token
            String token = authorizationHeader.substring("Bearer".length()).trim();
            UserEntity user = tokenDao.findUserByTokenValue(token);
            Response validationResponse = authBean.validateUserToken(token);
            if (validationResponse.getStatus() != Response.Status.OK.getStatusCode()) {
                return validationResponse;
            }

            projectBean.removeResourcesFromProject(resourcesToRemove, projectId);
            LoggerUtil.logInfo("RESOURCES REMOVED FROM PROJECT. PROJECT ID: " + projectId + " RESOURCES Id's: " + resourcesToRemove , "at " + LocalDateTime.now(), user.getEmail(),token);

            return Response.status(Response.Status.OK).entity("Resources removed from project successfully").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}

