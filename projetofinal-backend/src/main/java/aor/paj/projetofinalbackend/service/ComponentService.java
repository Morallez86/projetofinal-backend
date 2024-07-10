package aor.paj.projetofinalbackend.service;

import aor.paj.projetofinalbackend.bean.ComponentBean;
import aor.paj.projetofinalbackend.bean.UserBean;
import aor.paj.projetofinalbackend.dao.TokenDao;
import aor.paj.projetofinalbackend.dto.ComponentDto;
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
 * Service class that handles HTTP requests related to components.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Path("/components")
public class ComponentService {

    @Inject
    ComponentBean componentBean;

    @Inject
    UserBean userBean;

    @Inject
    TokenDao tokenDao;

    /**
     * Creates a new component.
     *
     * @param authorizationHeader Authorization header containing the JWT token.
     * @param componentDto ComponentDto object containing component data.
     * @return Response indicating success or failure of component creation.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createComponent(@HeaderParam("Authorization") String authorizationHeader, ComponentDto componentDto) {
        String token = authorizationHeader.substring("Bearer".length()).trim();
        UserEntity user = tokenDao.findUserByTokenValue(token);
        try {
            if (componentDto.getProjectId()== null) {
                componentBean.addComponentDefault(componentDto);
            } else {
                componentBean.addComponentInProject(componentDto, componentDto.getProjectId());
            }
            LoggerUtil.logInfo("COMPONENT CREATED WITH THIS NAME: " + componentDto.getName(), "at " + LocalDateTime.now(), user.getEmail(), token );

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

    /**
     * Updates an existing component.
     *
     * @param authorizationHeader Authorization header containing the JWT token.
     * @param componentDto ComponentDto object containing updated component data.
     * @return Response indicating success or failure of component update.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateComponent(@HeaderParam("Authorization") String authorizationHeader, ComponentDto componentDto) {
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
            componentBean.updateComponent(componentDto);
            LoggerUtil.logInfo("RESOURCE UPDATED THIS THIS ID: " + componentDto.getId(), "at " + LocalDateTime.now(), user.getEmail(), token );

            return Response.status(Response.Status.OK).entity("Component updated").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Retrieves all components with pagination support.
     *
     * @param authorizationHeader Authorization header containing the JWT token.
     * @param page Page number for pagination (default is 1).
     * @param limit Number of items per page (default is 10).
     * @param keyWord Keyword for searching components (default is empty).
     * @return Response with a list of components and pagination information.
     */
    @GET
    @Path(("/toTables"))
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllComponents(@HeaderParam("Authorization") String authorizationHeader, @QueryParam("page") @DefaultValue("1") int page,
                                     @QueryParam("limit") @DefaultValue("10") int limit, @QueryParam("filter") @DefaultValue("") String keyWord) {
        try {
            if (keyWord!=null) {
                List<ComponentDto> componentDtoListWithSearch = componentBean.allComponentsSearch(page, limit, keyWord);
                long totalComponentsSearch = componentBean.getTotalCountBySearch(keyWord);
                int totalPagesSearch = (int) Math.ceil((double)  totalComponentsSearch/limit);
                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("components", componentDtoListWithSearch);
                responseMap.put("totalPages", totalPagesSearch);
                return Response.status(Response.Status.OK).entity(responseMap).build();
            }
            else {
            List<ComponentDto> componentDtoList = componentBean.allComponents(page, limit);
            long totalComponents = componentBean.getTotalComponentsCount();
            int totalPages = (int) Math.ceil((double)  totalComponents/limit);

            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("components", componentDtoList);
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

    /**
     * Retrieves all components optionally filtered by workplace.
     *
     * @param authorizationHeader Authorization header containing the JWT token.
     * @param workplaceId Optional workplace ID to filter components.
     * @return Response with a list of components filtered by workplace.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response allComponents(@HeaderParam("Authorization") String authorizationHeader,
                                  @QueryParam("workplace") Long workplaceId) {
        try {
            List<ComponentDto> componentDtoList;
            if (workplaceId != null) {
                componentDtoList = componentBean.getAllByWorkplaceId(workplaceId);
            } else {
                componentDtoList = componentBean.getAllWithoutFilters();
            }
            return Response.status(Response.Status.OK).entity(componentDtoList).build();
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
     * Retrieves available component names grouped by name for a given workplace.
     *
     * @param authorizationHeader Authorization header containing the JWT token.
     * @param workplaceId Workplace ID for filtering components.
     * @return Response with a list of available component names grouped by name.
     */
    @GET
    @Path("/availableGroupedByName")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAvailableComponentsGroupedByName(@HeaderParam("Authorization") String authorizationHeader, @QueryParam("workplaceId") Long workplaceId) {
        try {
            if (workplaceId == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Workplace ID is required").build();
            }
            List<String> componentNames = componentBean.findAvailableComponentsGroupedByName(workplaceId);
            return Response.status(Response.Status.OK).entity(componentNames).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}