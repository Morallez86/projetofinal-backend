package aor.paj.projetofinalbackend.service;

import aor.paj.projetofinalbackend.bean.ProjectBean;
import aor.paj.projetofinalbackend.dto.ProfileDto;
import aor.paj.projetofinalbackend.dto.ProjectDto;
import aor.paj.projetofinalbackend.entity.ProjectEntity;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/projects")
public class ProjectService {

    @Inject
    ProjectBean projectBean;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProject(@HeaderParam("Authorization") String authorizationHeader, ProjectDto projectDto) {
        try {
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
}
