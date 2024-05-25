package aor.paj.projetofinalbackend.service;

import aor.paj.projetofinalbackend.bean.SkillBean;
import aor.paj.projetofinalbackend.dto.SkillDto;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/skills")
public class SkillService {

    @Inject
    SkillBean skillBean;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSkills(@HeaderParam("Authorization") String authorizationHeader) {
        List<SkillDto> skills = skillBean.getAllSkills();
        if (skills != null) {
            return Response.ok(skills).build();
        } else return Response.status(Response.Status.NOT_FOUND).entity("Skills not found").build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addSkills(List<SkillDto> skillDtos, @HeaderParam("Authorization") String authorizationHeader) {
        try {
            // Extract the token from the header
            String token = authorizationHeader.substring("Bearer".length()).trim();

            // Add the skills
            skillBean.addSkills(skillDtos, token);

            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
