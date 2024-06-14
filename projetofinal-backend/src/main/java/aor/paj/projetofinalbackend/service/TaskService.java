package aor.paj.projetofinalbackend.service;

import aor.paj.projetofinalbackend.bean.TaskBean;
import aor.paj.projetofinalbackend.dto.TaskDto;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/tasks")
public class TaskService {

    @Inject
    TaskBean taskBean;

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

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editTask(@HeaderParam("Authorization") String authorizationHeader, TaskDto taskDto) {
        try {
            taskBean.editTask(taskDto);
            return Response.ok("task updated").build();
        }catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }}
