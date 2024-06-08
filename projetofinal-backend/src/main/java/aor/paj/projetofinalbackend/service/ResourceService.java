package aor.paj.projetofinalbackend.service;

import aor.paj.projetofinalbackend.bean.ComponentBean;
import aor.paj.projetofinalbackend.bean.ResourceBean;
import aor.paj.projetofinalbackend.dto.ComponentDto;
import aor.paj.projetofinalbackend.dto.ResourceDto;
import jakarta.annotation.Resource;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/resources")
public class ResourceService {

    @Inject
    ResourceBean resourceBean;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response allResources(@HeaderParam("Authorization") String authorizationHeader, @QueryParam("page") @DefaultValue("1") int page,
                                 @QueryParam("limit") @DefaultValue("10") int limit, @QueryParam("filter") @DefaultValue("") String keyWord) {
        try {
            if (keyWord!=null) {
                List<ResourceDto> resourceDtoListWithSearch = resourceBean.allResourcesSearch(page, limit, keyWord);
                long totalResourcesSearch = resourceBean.getTotalCountBySearch(keyWord);
                int totalPagesSearch = (int) Math.ceil((double)  totalResourcesSearch/limit);
                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("resources", resourceDtoListWithSearch);
                responseMap.put("totalPages", totalPagesSearch);
                return Response.status(Response.Status.OK).entity(responseMap).build();
            }
            else {
                List<ResourceDto> resourceDtoList = resourceBean.allResources(page, limit);
                long totalResources = resourceBean.getTotalResourcesCount();
                int totalPages = (int) Math.ceil((double)  totalResources/limit);

                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("resources", resourceDtoList);
                responseMap.put("totalPages", totalPages);
                return Response.status(Response.Status.OK).entity(responseMap).build();
            }
        } catch (ExceptionInInitializerError e) {
            Throwable cause = e.getCause();
            cause.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(cause.getMessage()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateComponent(@HeaderParam("Authorization") String authorizationHeader, ResourceDto resourceDto) {
        try {
            resourceBean.updateResource(resourceDto);
            return Response.status(Response.Status.OK).entity("component updated").build();
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
