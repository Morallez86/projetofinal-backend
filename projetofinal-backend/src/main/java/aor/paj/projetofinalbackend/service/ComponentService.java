package aor.paj.projetofinalbackend.service;

import aor.paj.projetofinalbackend.bean.AuthBean;
import aor.paj.projetofinalbackend.bean.ComponentBean;
import aor.paj.projetofinalbackend.dao.ComponentDao;
import aor.paj.projetofinalbackend.dto.ComponentDto;
import aor.paj.projetofinalbackend.dto.ProjectDto;
import aor.paj.projetofinalbackend.utils.Role;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/components")
public class ComponentService {

    @Inject
    ComponentBean componentBean;

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
        if (!authBean.isUserInRole(Role.ADMIN)) {
            return Response.status(Response.Status.FORBIDDEN).entity("You are not authorized to perform this action").build();
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllComponents(@HeaderParam("Authorization") String authorizationHeader) {
        try {
            List<ComponentDto> componentDtoList = componentBean.allComponents();
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


