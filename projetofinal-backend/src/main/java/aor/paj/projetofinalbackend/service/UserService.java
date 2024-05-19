package aor.paj.projetofinalbackend.service;

import aor.paj.projetofinalbackend.bean.TokenBean;
import aor.paj.projetofinalbackend.bean.UserBean;
import aor.paj.projetofinalbackend.dto.TokenResponse;
import aor.paj.projetofinalbackend.dto.UserCredentials;
import aor.paj.projetofinalbackend.dto.UserDto;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.utils.EncryptHelper;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
public class UserService {

    @Inject
    UserBean userBean;

    @Inject
    TokenBean tokenBean;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(UserCredentials credentials) {

        UserEntity user = userBean.findUserByEmail(credentials.getEmail());
        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("User not found").build();
        }

        if (!EncryptHelper.checkPassword(credentials.getPassword(), user.getPassword())) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid password").build();
        }

        String tokenValue = userBean.createAndSaveToken(user);
        return Response.ok(new TokenResponse(tokenValue)).build();
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(UserDto userDto) {
        try {
            userBean.registerUser(userDto);
            return Response.status(Response.Status.CREATED).entity("User registered successfully").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("User registration failed: " + e.getMessage()).build();
        }
    }
}
