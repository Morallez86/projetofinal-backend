package aor.paj.projetofinalbackend.service;


import aor.paj.projetofinalbackend.bean.InterestBean;
import aor.paj.projetofinalbackend.dto.InterestDto;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/interests")
public class InterestService {

    @Inject
    InterestBean interestBean;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllInterests(@HeaderParam("Authorization") String authorizationHeader) {
        List<InterestDto> interests = interestBean.getAllAttributes();
        if (interests != null) {
            return Response.ok(interests).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Interests not found").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addInterests(List<InterestDto> interestDtos, @HeaderParam("Authorization") String authorizationHeader) {
        try {
            // Extract the token from the header
            String token = authorizationHeader.substring("Bearer".length()).trim();

            // Add the interests
            interestBean.addAttributes(interestDtos, token);

            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
