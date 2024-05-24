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
        return Response.ok(skills).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addSkill(SkillDto skillDto, @HeaderParam("Authorization") String authorizationHeader) {
        try {

            String token = authorizationHeader.substring("Bearer".length()).trim();

            skillBean.addSkill(skillDto, token);

            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
