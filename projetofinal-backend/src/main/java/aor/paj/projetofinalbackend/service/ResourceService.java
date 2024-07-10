package aor.paj.projetofinalbackend.service;

import aor.paj.projetofinalbackend.bean.ResourceBean;
import aor.paj.projetofinalbackend.bean.UserBean;
import aor.paj.projetofinalbackend.dao.TokenDao;
import aor.paj.projetofinalbackend.dto.ResourceDto;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.security.JwtUtil;
import aor.paj.projetofinalbackend.utils.LoggerUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service endpoints for managing resources.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Path("/resources")
public class ResourceService {

    @Inject
    ResourceBean resourceBean;

    @Inject
    UserBean userBean;

    @Inject
    TokenDao tokenDao;

    /**
     * Retrieves all resources with optional pagination and filtering.
     *
     * @param authorizationHeader The Authorization header containing the bearer token.
     * @param page The page number for pagination (default is 1).
     * @param limit The maximum number of resources per page (default is 10).
     * @param keyWord The keyword to filter resources by name.
     * @return Response with status OK and a map containing resources list and pagination details.
     */
    @GET
    @Path(("/toTables"))
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response allResources(@HeaderParam("Authorization") String authorizationHeader, @QueryParam("page") @DefaultValue("1") int page,
                                 @QueryParam("limit") @DefaultValue("10") int limit, @QueryParam("filter") @DefaultValue("") String keyWord) {
        String token = authorizationHeader.substring("Bearer".length()).trim();
        UserEntity user = tokenDao.findUserByTokenValue(token);
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
                LoggerUtil.logInfo("CHECK RESOURCES WITH FILTERS", "at " + LocalDateTime.now(), user.getEmail(), token );
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

    /**
     * Creates a new resource.
     *
     * @param authorizationHeader The Authorization header containing the bearer token.
     * @param resourceDto The ResourceDto object containing resource details.
     * @return Response with status CREATED if successful, otherwise INTERNAL_SERVER_ERROR.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createResource(@HeaderParam("Authorization") String authorizationHeader, ResourceDto resourceDto) {
        String token = authorizationHeader.substring("Bearer".length()).trim();
        UserEntity user = tokenDao.findUserByTokenValue(token);
        try {
            if (resourceDto.getProjectIds()== null) {
                resourceBean.addResourceDefault(resourceDto);
            } else {
                resourceBean.addResourceInProject(resourceDto, resourceDto.getProjectIds());
            }
            LoggerUtil.logInfo("RESOURCE CREATED WITH THIS NAME: " + resourceDto.getName(), "at " + LocalDateTime.now(), user.getEmail(), token );

            return Response.status(Response.Status.CREATED).entity("resource created").build();
        } catch (ExceptionInInitializerError e) {
            Throwable cause = e.getCause();
            cause.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(cause.getMessage()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Updates an existing resource.
     *
     * @param authorizationHeader The Authorization header containing the bearer token.
     * @param resourceDto The ResourceDto object containing updated resource details.
     * @return Response with status OK if successful, otherwise INTERNAL_SERVER_ERROR.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateResource(@HeaderParam("Authorization") String authorizationHeader, ResourceDto resourceDto) {
        String token = authorizationHeader.substring("Bearer".length()).trim();
        UserEntity user = tokenDao.findUserByTokenValue(token);

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
            LoggerUtil.logInfo("RESOURCE UPDATED THIS THIS ID: " + resourceDto.getId(), "at " + LocalDateTime.now(), user.getEmail(), token );

            return Response.status(Response.Status.OK).entity("resource updated").build();
        } catch (ExceptionInInitializerError e) {
            Throwable cause = e.getCause();
            cause.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(cause.getMessage()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Retrieves all resources without any filters.
     *
     * @param authorizationHeader The Authorization header containing the bearer token.
     * @return Response with status OK and a list of all resources.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response allResourcesWithoutFilters(@HeaderParam("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring("Bearer".length()).trim();
        UserEntity user = tokenDao.findUserByTokenValue(token);
        try {
            List <ResourceDto> resourceDtoList = resourceBean.getAllWithoutFilters();
            LoggerUtil.logInfo("CHECK RESOURCES WITHOUT FILTERS", "at " + LocalDateTime.now(), user.getEmail(), token );

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
