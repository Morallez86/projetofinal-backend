package aor.paj.projetofinalbackend.service;

import aor.paj.projetofinalbackend.bean.ProjectBean;
import aor.paj.projetofinalbackend.dto.ProfileDto;
import aor.paj.projetofinalbackend.dto.ProjectDto;
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

    String token = authorizationHeader.substring("Bearer".length()).trim();

        projectBean.addProject(projectDto, token);

}

}
