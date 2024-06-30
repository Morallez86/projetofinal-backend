package aor.paj.projetofinalbackend.service;

import aor.paj.projetofinalbackend.bean.ComponentBean;
import aor.paj.projetofinalbackend.bean.ResourceBean;
import aor.paj.projetofinalbackend.bean.UserBean;
import aor.paj.projetofinalbackend.dto.ComponentDto;
import aor.paj.projetofinalbackend.dto.ResourceDto;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.security.JwtUtil;
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

    @Inject
    UserBean userBean;

    @GET
    @Path(("/toTables"))
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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createResource(@HeaderParam("Authorization") String authorizationHeader, ResourceDto resourceDto) {
        try {
            if (resourceDto.getProjectIds()== null) {
                resourceBean.addResourceDefault(resourceDto);
            } else {
                resourceBean.addResourceInProject(resourceDto, resourceDto.getProjectIds());
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

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateResource(@HeaderParam("Authorization") String authorizationHeader, ResourceDto resourceDto) {
        String token = authorizationHeader.substring("Bearer".length()).trim();

        Long tokenUserId;
        try {
            tokenUserId = JwtUtil.extractUserIdFromToken(token);
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid token").build();
        }

        // Check if the current user is an admin
        UserEntity currentUser = userBean.findUserById(tokenUserId);
        if (currentUser == null || currentUser.getRole().getValue() != 200) {
            return Response.status(Response.Status.FORBIDDEN).entity("Only admins can update roles").build();
        }
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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response allResourcesWithoutFilters(@HeaderParam("Authorization") String authorizationHeader) {
        try {
            List <ResourceDto> resourceDtoList = resourceBean.getAllWithoutFilters();
            return Response.status(Response.Status.OK).entity(resourceDtoList).build();
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
