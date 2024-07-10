package aor.paj.projetofinalbackend.service;

import aor.paj.projetofinalbackend.bean.TaskBean;
import aor.paj.projetofinalbackend.dao.TokenDao;
import aor.paj.projetofinalbackend.dto.EditTaskResult;
import aor.paj.projetofinalbackend.dto.TaskDto;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.utils.LoggerUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service endpoints for managing tasks.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Path("/tasks")
public class TaskService {

    @Inject
    TaskBean taskBean;

    @Inject
    TokenDao tokenDao;

    /**
     * Retrieves all tasks.
     *
     * @param authorizationHeader The Authorization header containing the bearer token.
     * @return Response with status OK and a list of all tasks if successful, otherwise INTERNAL_SERVER_ERROR.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTasks(@HeaderParam("Authorization") String authorizationHeader) {
        try {
            List<TaskDto> tasks = taskBean.getAllTasks();
            return Response.ok(tasks).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Edits an existing task.
     *
     * @param authorizationHeader The Authorization header containing the bearer token.
     * @param taskDto The TaskDto object containing updated task information.
     * @return Response with status OK and the result of the task edit if successful, otherwise INTERNAL_SERVER_ERROR.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editTask(@HeaderParam("Authorization") String authorizationHeader, TaskDto taskDto) {
        String token = authorizationHeader.substring("Bearer".length()).trim();
        UserEntity user = tokenDao.findUserByTokenValue(token);
        try {
            EditTaskResult result = taskBean.editTask(taskDto);
            LoggerUtil.logInfo("TASK UPDATED: " + taskDto.getId(),"at " + LocalDateTime.now(),user.getEmail(), token);
            return Response.ok(result).build();
        }catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Creates a new task.
     *
     * @param authorizationHeader The Authorization header containing the bearer token.
     * @param taskDto The TaskDto object containing task information to be created.
     * @return Response with status CREATED if the task is created successfully, otherwise INTERNAL_SERVER_ERROR.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTask (@HeaderParam("Authorization") String authorizationHeader, TaskDto taskDto) {
        String token = authorizationHeader.substring("Bearer".length()).trim();
        UserEntity user = tokenDao.findUserByTokenValue(token);
        try {
            taskBean.createTask(taskDto);
            LoggerUtil.logInfo("TASK CREATED WITH THIS NAME: " + taskDto.getTitle(),"at " + LocalDateTime.now(),user.getEmail(), token);

            return Response.status(Response.Status.CREATED).entity("Task created").build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
