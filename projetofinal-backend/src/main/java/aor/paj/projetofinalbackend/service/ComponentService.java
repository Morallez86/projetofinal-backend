package aor.paj.projetofinalbackend.service;

import aor.paj.projetofinalbackend.bean.AuthBean;
import aor.paj.projetofinalbackend.bean.ComponentBean;
import aor.paj.projetofinalbackend.bean.UserBean;
import aor.paj.projetofinalbackend.dao.ComponentDao;
import aor.paj.projetofinalbackend.dto.ComponentDto;
import aor.paj.projetofinalbackend.dto.ProjectDto;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.security.JwtUtil;
import aor.paj.projetofinalbackend.utils.Role;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/components")
public class ComponentService {

    @Inject
    ComponentBean componentBean;

    @Inject
    UserBean userBean;

    @Inject
    AuthBean authBean;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createComponent(@HeaderParam("Authorization") String authorizationHeader, ComponentDto componentDto) {
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

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateComponent(@HeaderParam("Authorization") String authorizationHeader, ComponentDto componentDto) {
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
            componentBean.updateComponent(componentDto);
            return Response.status(Response.Status.OK).entity("Component updated").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }


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
}


