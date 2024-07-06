package aor.paj.projetofinalbackend.service;

import aor.paj.projetofinalbackend.bean.ComponentBean;
import aor.paj.projetofinalbackend.bean.ProjectHistoryBean;
import aor.paj.projetofinalbackend.dao.TokenDao;
import aor.paj.projetofinalbackend.dto.ComponentDto;
import aor.paj.projetofinalbackend.dto.ProjectHistoryDto;
import aor.paj.projetofinalbackend.entity.ProjectHistoryEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.utils.LoggerUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Path("/projectHistory")
public class ProjectHistoryService {

    @Inject
    ProjectHistoryBean projectHistoryBean;

    @Inject
    TokenDao tokenDao;

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
