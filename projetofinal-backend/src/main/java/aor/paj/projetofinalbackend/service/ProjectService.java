package aor.paj.projetofinalbackend.service;

import aor.paj.projetofinalbackend.bean.AuthBean;
import aor.paj.projetofinalbackend.bean.ProjectBean;
import aor.paj.projetofinalbackend.bean.ProjectHistoryBean;
import aor.paj.projetofinalbackend.dao.TaskDao;
import aor.paj.projetofinalbackend.dto.*;
import aor.paj.projetofinalbackend.entity.ProjectEntity;
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
                                   @QueryParam("page") @DefaultValue("1") int page,
                                   @QueryParam("limit") @DefaultValue("10") int limit) {
        try {
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring("Bearer".length()).trim();
                Response validationResponse = authBean.validateUserToken(token);
                if (validationResponse.getStatus() != Response.Status.OK.getStatusCode()) {
                    return validationResponse;
                }
            }

            Set<ProjectDto> projectDtos = projectBean.getAllProjects(page, limit);
            long totalProjects = projectBean.getTotalProjectCount();
            int totalPages = (int) Math.ceil((double) totalProjects / limit);

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

    }

