package aor.paj.projetofinalbackend.service;

import aor.paj.projetofinalbackend.bean.ProjectHistoryBean;
import aor.paj.projetofinalbackend.dao.TokenDao;
import aor.paj.projetofinalbackend.dto.ProjectHistoryDto;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.utils.LoggerUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service endpoint for managing project history logs.
 * This service allows authenticated users to create logs for specific projects.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Path("/projectHistory")
public class ProjectHistoryService {

    @Inject
    ProjectHistoryBean projectHistoryBean;

    @Inject
    TokenDao tokenDao;

    /**
     * Creates a new project history log entry for the specified project.
     *
     * @param authorizationHeader the Authorization header containing the bearer token
     * @param projectHistoryDto the ProjectHistoryDto object containing log details
     * @param projectId the ID of the project to which the log belongs
     * @return a Response object containing the created project history log
     */
    @POST
    @Path("/{projectId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createLog(@HeaderParam("Authorization") String authorizationHeader, ProjectHistoryDto projectHistoryDto,  @PathParam("projectId") Long projectId) {
        try {
            String token = authorizationHeader.substring("Bearer".length()).trim();
            UserEntity user = tokenDao.findUserByTokenValue(token);
            List<ProjectHistoryDto> projectHistoryDtoList = projectHistoryBean.addLog(projectHistoryDto, projectId, token);
            
            Long finalId = null;

            for (ProjectHistoryDto log : projectHistoryDtoList) {
                finalId = log.getId();
                break;
            }
            LoggerUtil.logInfo("LOG CREATED. PROJECT ID: " + projectId + "LOGS ID's: " + finalId , "at " + LocalDateTime.now(), user.getEmail(),token);
            return Response.status(Response.Status.CREATED).entity(projectHistoryDtoList).build();
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
