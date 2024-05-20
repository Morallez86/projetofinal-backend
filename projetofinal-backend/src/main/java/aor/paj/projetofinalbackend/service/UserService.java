package aor.paj.projetofinalbackend.service;

import aor.paj.projetofinalbackend.bean.ImageBean;
import aor.paj.projetofinalbackend.bean.TokenBean;
import aor.paj.projetofinalbackend.bean.UserBean;
import aor.paj.projetofinalbackend.dto.TokenResponse;
import aor.paj.projetofinalbackend.dto.UserCredentials;
import aor.paj.projetofinalbackend.dto.UserDto;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.utils.EmailSender;
import aor.paj.projetofinalbackend.utils.EncryptHelper;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.io.InputStream;

@Path("/users")
public class UserService {

    @Inject
    UserBean userBean;

    @Inject
    TokenBean tokenBean;

    @Inject
    ImageBean imageBean;

    @Inject
    EmailSender emailSender;

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

    @POST
    @Path("/emailRecoveryPassword")
    @Consumes (MediaType.APPLICATION_JSON)
    public Response emailRecoveryPassword (String email) {
        UserEntity user = userBean.findUserByEmail(email);
        if (user != null) {
            emailSender.sendRecoveryPassword("testeAor@hotmail.com",user.getEmailToken());
            return Response.status(200).entity("Email sended").build();
        }
        else {
            return Response.status(400).entity("User not found").build();
        }
    }

    @POST
    @Path("/image")
    @Consumes("image/*")
    public Response uploadImage(InputStream imageData, @HeaderParam("filename") String originalFileName, @HeaderParam("email") String email) {
        try {
            imageBean.saveUserProfileImage(email, imageData, originalFileName);
            } catch (IOException ex) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok().build();
    }
}
