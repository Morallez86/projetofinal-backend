/*package aor.paj.projetofinalbackend.service;

import aor.paj.projetofinalbackend.dto.ComponentDto;
import aor.paj.projetofinalbackend.dto.ResourceDto;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/resources")
public class ResourceService {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createComponent(@HeaderParam("Authorization") String authorizationHeader, ResourceDto resourceDto) {
        try {
            if (componentDto.getProjectId()== null) {
                componentBean.addComponentDefault(componentDto);
            } else {
                componentBean.addComponentInProject(componentDto, componentDto.getProjectId());
            }
            return Response.status(Response.Status.CREATED).entity("component created").build();
        } catch (ExceptionInInitializerError e) {
            Throwable cause = e.getCause();
            cause.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(cause.getMessage()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }


}*/
