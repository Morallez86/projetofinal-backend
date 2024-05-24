package aor.paj.projetofinalbackend.service;

import aor.paj.projetofinalbackend.bean.SkillBean;
import aor.paj.projetofinalbackend.dto.SkillDto;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
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
}
