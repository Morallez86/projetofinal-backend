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
            } else if (searchTerm != null || skills != null || interests != null || status !=null ) {
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
        public Response updateProject (@HeaderParam("Authorization") String authorizationHeader,
                @PathParam("projectId") Long projectId,
                ProjectDto projectDto){
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
        public Response createChatMsg (@HeaderParam("Authorization") String authorizationHeader, ChatMessageDto chatMessageDto) {
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
            System.out.println(projectId);
            System.out.println(userId);
            System.out.println(userProjectDto.isAdmin());
            // Validate token
            String token = authorizationHeader.substring("Bearer".length()).trim();
            Response validationResponse = authBean.validateUserToken(token);
            if (validationResponse.getStatus() != Response.Status.OK.getStatusCode()) {
                return validationResponse;
            }

            Boolean newStatus = userProjectDto.isAdmin();

            // Change user status in the project
            projectBean.changeUserStatus(projectId, userId, newStatus);

            return Response.status(Response.Status.OK).entity("User status updated successfully").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}

