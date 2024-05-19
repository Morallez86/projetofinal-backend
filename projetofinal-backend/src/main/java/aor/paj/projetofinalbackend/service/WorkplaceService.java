package aor.paj.projetofinalbackend.service;

import aor.paj.projetofinalbackend.bean.WorkplaceBean;
import aor.paj.projetofinalbackend.dto.WorkplaceDto;
import aor.paj.projetofinalbackend.entity.WorkplaceEntity;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/workplaces")
public class WorkplaceService {

    @EJB
    WorkplaceBean workplaceBean;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createWorkplace(WorkplaceDto workplaceDto) {
        try {
            WorkplaceEntity newWorkplace = workplaceBean.createWorkplace(workplaceDto.getName());
            if (newWorkplace != null) {
                return Response.status(Response.Status.CREATED).entity(newWorkplace).build();
            } else {
                return Response.status(Response.Status.CONFLICT)
                        .entity("A workplace with the same name already exists.")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllWorkplaces() {
        List<WorkplaceDto> workplaces = workplaceBean.getAllWorkplaces();
        return Response.ok().entity(workplaces).build();
    }
}
