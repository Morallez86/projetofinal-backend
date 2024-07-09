package aor.paj.projetofinalbackend.service;

import aor.paj.projetofinalbackend.bean.WorkplaceBean;
import aor.paj.projetofinalbackend.dto.WorkplaceDto;
import aor.paj.projetofinalbackend.entity.WorkplaceEntity;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

/**
 * Service endpoints for managing workplaces.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Path("/workplaces")
public class WorkplaceService {

    @EJB
    WorkplaceBean workplaceBean;


    /**
     * Endpoint to create a new workplace.
     *
     * @param workplaceDto The data transfer object containing the details of the new workplace to be created.
     * @return Response with status CREATED and the created WorkplaceEntity if successful, otherwise appropriate error response.
     */
    @POST
    @Path("/createWorkplace")
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

    /**
     * Endpoint to retrieve all workplaces.
     *
     * @return Response with status OK and a list of WorkplaceDto objects representing all workplaces, otherwise appropriate error response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllWorkplaces() {
        List<WorkplaceDto> workplaces = workplaceBean.getAllWorkplaces();
        if (workplaces != null) {
        return Response.ok().entity(workplaces).build();
    } else {
            return Response.status(404).entity("Workplaces not found").build();
        }
    }
}
