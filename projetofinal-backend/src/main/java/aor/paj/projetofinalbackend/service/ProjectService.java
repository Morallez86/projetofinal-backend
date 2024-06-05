package aor.paj.projetofinalbackend.service;

import aor.paj.projetofinalbackend.bean.AuthBean;
import aor.paj.projetofinalbackend.bean.ProjectBean;
import aor.paj.projetofinalbackend.dto.ProfileDto;
import aor.paj.projetofinalbackend.dto.ProjectDto;
import aor.paj.projetofinalbackend.entity.ProjectEntity;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProject(@HeaderParam("Authorization") String authorizationHeader, ProjectDto projectDto) {
        try {
            String token = authorizationHeader.substring("Bearer".length()).trim();
            System.out.println(projectDto.getStatus());
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
            String token = authorizationHeader.substring("Bearer".length()).trim();
            Response validationResponse = authBean.validateUserToken(token);
            if (validationResponse.getStatus() != Response.Status.OK.getStatusCode()) {
                return validationResponse;
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
}
